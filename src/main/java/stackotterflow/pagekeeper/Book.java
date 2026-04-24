/**
 * @author Anthony Torres
 * A class for representing book metadata sch as title, author, ISBN, total pages, and summary.
 * created: 4/13/2026
 * @since 0.1.0
 */
package stackotterflow.pagekeeper;

public class Book {
  private int bookId;
  private String title;
  private String author;
  private String isbn;
  private Integer totalPages;
  private String summary;
  private Integer year;

  // constructor for existing record
  public Book(int bookId, String title, String author, String isbn, Integer totalPages, String summary, Integer year) {
    this.bookId = bookId;
    this.title = title;
    this.author = author;
    this.isbn = isbn;
    this.totalPages = totalPages;
    this.summary = summary;
    this.year = year;
  }

  //constructor for new record
  public Book(String title, String author, String isbn, Integer totalPages, String summary, Integer year) {
    this.title = title;
    this.author = author;
    this.isbn = isbn;
    this.totalPages = totalPages;
    this.summary = summary;
    this.year = year;
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

  public Integer getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(Integer totalPages) {
    this.totalPages = totalPages;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }
  public Integer getYear() {
    return year;
  }
  public void setYear(Integer year) {
    this.year = year;
  }
}
