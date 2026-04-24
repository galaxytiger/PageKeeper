/**
 * @author Anthony Torres
 * <p>
 * created: 4/23/2026
 * @since 0.1.0
 */
package stackotterflow.pagekeeper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class OpenLibraryClient {
  private static final String SEARCH_URL = "https://openlibrary.org/search.json";
  private static final String USER_AGENT = "PageKeeper/1.0";

  private final HttpClient httpClient;
  private final ObjectMapper objectMapper;

  public OpenLibraryClient() {
    this.httpClient = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(10))
        .build();
    this.objectMapper = new ObjectMapper();
  }

  public List<BookSearchResult> searchBooks(String query) throws IOException, InterruptedException {
    String searchQuery = query == null ? "" : query.trim();
    if (searchQuery.isEmpty()) {
      return List.of();
    }

    String encodedQuery = URLEncoder.encode(searchQuery, StandardCharsets.UTF_8);
    String uri = SEARCH_URL + "?q=" + encodedQuery + "&limit=20";

    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(uri))
        .timeout(Duration.ofSeconds(10))
        .header("Accept", "application/json")
        .header("User-Agent", USER_AGENT)
        .GET()
        .build();

    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() != 200) {
      throw new IOException("Search request failed with status " + response.statusCode());
    }

    JsonNode root = objectMapper.readTree(response.body());
    JsonNode docs = root.path("docs");

    List<BookSearchResult> results = new ArrayList<>();
    if (!docs.isArray()) {
      return results;
    }

    for (JsonNode doc : docs) {
      String title = getText(doc, "title");
      String author = getFirstArrayText(doc, "author_name");
      String isbn = getFirstArrayText(doc, "isbn");
      int year = getIntOrDefault(doc);
      Integer coverId = getNullableInt(doc, "cover_i");
      String openLibKey = getText(doc, "key");
      Integer totalPages = getNullableInt(doc, "number_of_pages_median");

      results.add(new BookSearchResult(
          title,
          author,
          isbn,
          year,
          coverId,
          openLibKey,
          totalPages,
          null
      ));
    }

    return results;
  }

  public String fetchSummary(String openLibKey) throws IOException, InterruptedException {
    if (openLibKey == null || openLibKey.isBlank()) {
      return null;
    }

    String normalizedKey = openLibKey.startsWith("/works/") ? openLibKey : "/works/" + openLibKey;
    String uri = "https://openlibrary.org" + normalizedKey + ".json";

    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(uri))
        .timeout(Duration.ofSeconds(10))
        .header("Accept", "application/json")
        .header("User-Agent", USER_AGENT)
        .GET()
        .build();

    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() != 200) {
      return null;
    }

    JsonNode root = objectMapper.readTree(response.body());
    JsonNode descriptionNode = root.get("description");

    if (descriptionNode == null || descriptionNode.isNull()) {
      return null;
    }

    if (descriptionNode.isTextual()) {
      return descriptionNode.asText().trim();
    }

    JsonNode valueNode = descriptionNode.get("value");
    if (valueNode != null && valueNode.isTextual()) {
      return valueNode.asText().trim();
    }

    return null;
  }

  public String buildCoverUrl(Integer coverId) {
    if (coverId == null) {
      return null;
    }
    return "https://covers.openlibrary.org/b/id/" + coverId + "-M.jpg";
  }


  private String getText(JsonNode node, String fieldName) {
    JsonNode child = node.get(fieldName);
    if (child == null || child.isNull() || !child.isTextual()) {
      return null;
    }
    return child.asText().trim();
  }

  private String getFirstArrayText(JsonNode node, String fieldName) {
    JsonNode arrayNode = node.get(fieldName);
    if (arrayNode == null || !arrayNode.isArray() || arrayNode.isEmpty()) {
      return null;
    }

    JsonNode first = arrayNode.get(0);
    if (first == null || first.isNull() || !first.isTextual()) {
      return null;
    }

    return first.asText().trim();
  }

  private Integer getNullableInt(JsonNode node, String fieldName) {
    JsonNode child = node.get(fieldName);
    if (child == null || child.isNull() || !child.isNumber()) {
      return null;
    }
    return child.asInt();
  }

  private int getIntOrDefault(JsonNode node) {
    JsonNode child = node.get("first_publish_year");
    if (child == null || child.isNull() || !child.isNumber()) {
      return 0;
    }
    return child.asInt();
  }

}
