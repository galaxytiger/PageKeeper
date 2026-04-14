/**
 * @author Anthony Torres
 * <p>
 * created: 4/13/2026
 * @since 0.1.0
 */
package stackotterflow.pagekeeper;

public class Book {
  private int bookId;
  private String title;
  private String author;
  private String isbn;
  private int totalPages;
  private String summary;

  public Book(int bookId, String title, String author, String isbn, int totalPages, String summary) {
    this.bookId = bookId;
    this.title = title;
    this.author = author;
    this.isbn = isbn;
    this.totalPages = totalPages;
    this.summary = summary;
  }

  public Book(String title, String author, String isbn, int totalPages, String summary) {
    this.title = title;
    this.author = author;
    this.isbn = isbn;
    this.totalPages = totalPages;
    this.summary = summary;
  }

  public int getBookId() {
    return bookId;
  }

  public void setBookId(int bookId) {
    this.bookId = bookId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }
}
