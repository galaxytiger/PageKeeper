/**
 * @author Anthony Torres
 * A class to store book metadata returned from OpenLibraryClient search results.
 * created: 4/23/2026
 * @since 0.1.0
 */
package stackotterflow.pagekeeper;

public class BookSearchResult {
  private final String title;
  private final String author;
  private final String isbn;
  private final int year;
  private final Integer coverID;
  private final String openLibkey;
  private final Integer totalPages;
  private final String summary;

  public BookSearchResult(String title, String author, String isbn, int year, Integer coverID, String openLibkey, Integer totalPages, String summary) {
    this.title = title;
    this.author = author;
    this.isbn = isbn;
    this.year = year;
    this.coverID = coverID;
    this.openLibkey = openLibkey;
    this.totalPages = totalPages;
    this.summary = summary;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public String getIsbn() {
    return isbn;
  }

  public int getYear() {
    return year;
  }

  public Integer getCoverID() {
    return coverID;
  }

  public String getOpenLibkey() {
    return openLibkey;
  }

  public Integer getTotalPages() {
    return totalPages;
  }

  public String getSummary() {
    return summary;
  }
}
