/**
 * @author Anthony Torres
 * <p>
 * created: 4/13/2026
 * @since 0.1.0
 */
package stackotterflow.pagekeeper;

public class BookDetail {
  private int detailId;
  private int userId;
  private int bookId;
  private String status;
  private int currentPage;
  private boolean borrowed;
  private String returnDate;
  private Integer rating;
  private String review;

  //constructor for existing record
  public BookDetail(int detailId, int userId, int bookId, String status,
      int currentPage, boolean borrowed, String returnDate,
      Integer rating, String review) {
    this.detailId = detailId;
    this.userId = userId;
    this.bookId = bookId;
    this.status = status;
    this.currentPage = currentPage;
    this.borrowed = borrowed;
    this.returnDate = returnDate;
    this.rating = rating;
    this.review = review;
  }

  //constructor for new record
  public BookDetail(int userId, int bookId, String status,
      int currentPage, boolean borrowed, String returnDate,
      Integer rating, String review) {
    this.userId = userId;
    this.bookId = bookId;
    this.status = status;
    this.currentPage = currentPage;
    this.borrowed = borrowed;
    this.returnDate = returnDate;
    this.rating = rating;
    this.review = review;
  }

  public int getDetailId() {
    return detailId;
  }

  public void setDetailId(int detailId) {
    this.detailId = detailId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getBookId() {
    return bookId;
  }

  public void setBookId(int bookId) {
    this.bookId = bookId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  public boolean isBorrowed() {
    return borrowed;
  }

  public void setBorrowed(boolean borrowed) {
    this.borrowed = borrowed;
  }

  public String getReturnDate() {
    return returnDate;
  }

  public void setReturnDate(String returnDate) {
    this.returnDate = returnDate;
  }

  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }

  public String getReview() {
    return review;
  }

  public void setReview(String review) {
    this.review = review;
  }
}
