/**
 * @author Anthony Torres
 * Implements database CRUD operations for Book Detail records INSERT, READ, UPDATE, DELETE
 * created: 4/13/2026
 * @since 0.1.0
 */
package stackotterflow.pagekeeper;
import java.sql.*;

public class BookDetailCrud implements BookDetailDao {
  private final DatabaseManager databaseManager;

  public BookDetailCrud(DatabaseManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public boolean insert(BookDetail bookDetail) {
    String sql = """
        INSERT INTO book_details (user_id, book_id, status, current_page, borrowed, return_date,
        rating, review)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

    try {
      Connection connection = databaseManager.getConnection();
      if (connection == null) {
        return false;
      }

      try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        stmt.setInt(1, bookDetail.getUserId());
        stmt.setInt(2, bookDetail.getBookId());
        stmt.setString(3, bookDetail.getStatus());
        stmt.setInt(4, bookDetail.getCurrentPage());
        stmt.setBoolean(5, bookDetail.isBorrowed());
        stmt.setString(6, bookDetail.getReturnDate());

        if (bookDetail.getRating() == null) {
          stmt.setNull(7, Types.INTEGER);
        } else {
          stmt.setInt(7, bookDetail.getRating());
        }

        stmt.setString(8, bookDetail.getReview());

        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected != 1) {
          return false;
        }

        try (ResultSet genKeys = stmt.getGeneratedKeys()) {
          if (genKeys.next()) {
            bookDetail.setDetailId(genKeys.getInt(1));
          }
        }
        return true;
      }
    } catch (SQLException e) {
      System.out.println("insert failed: " + e.getMessage());
      return false;
    }
  }
  @Override
  public BookDetail queryById(int detailId) {
    String sql = """
        SELECT detail_id, user_id, book_id, status, current_page, borrowed, return_date, rating, review
        FROM book_details
        WHERE detail_id = ?
        """;

    try {
      Connection connection = databaseManager.getConnection();
      if (connection == null) {
        return null;
      }

      try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, detailId);

        try (ResultSet rs = stmt.executeQuery()) {
          if (rs.next()) {
            int ratingValue = rs.getInt("rating");
            Integer rating = rs.wasNull() ? null : ratingValue;
            return new BookDetail(
                rs.getInt("detail_id"),
                rs.getInt("user_id"),
                rs.getInt("book_id"),
                rs.getString("status"),
                rs.getInt("current_page"),
                rs.getBoolean("borrowed"),
                rs.getString("return_date"),
                rating,
                rs.getString("review")
            );
          }
        }
      }
    } catch (SQLException e) {
      System.out.println("queryById failed: " + e.getMessage());
    }

    return null;
  }

  @Override
  public boolean update(BookDetail bookDetail) {
    String sql = """
        UPDATE book_details
        SET user_id = ?, book_id = ?, status = ?, current_page = ?, borrowed = ?, return_date = ?,
            rating = ?, review = ?
        WHERE detail_id = ?
        """;

    try {
      Connection connection = databaseManager.getConnection();
      if (connection == null) {
        return false;
      }

      try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, bookDetail.getUserId());
        stmt.setInt(2, bookDetail.getBookId());
        stmt.setString(3, bookDetail.getStatus());
        stmt.setInt(4, bookDetail.getCurrentPage());
        stmt.setBoolean(5, bookDetail.isBorrowed());
        stmt.setString(6, bookDetail.getReturnDate());

        if (bookDetail.getRating() == null) {
          stmt.setNull(7, Types.INTEGER);
        } else {
          stmt.setInt(7, bookDetail.getRating());
        }

        stmt.setString(8, bookDetail.getReview());
        stmt.setInt(9, bookDetail.getDetailId());

        return stmt.executeUpdate() == 1;
      }
    } catch (SQLException e) {
      System.out.println("update failed: " + e.getMessage());
      return false;
    }
  }

  @Override
  public boolean delete(int detailId) {
    String sql = "DELETE FROM book_details WHERE detail_id = ?";

    try {
      Connection connection = databaseManager.getConnection();
      if (connection == null) {
        return false;
      }

      try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, detailId);
        return stmt.executeUpdate() == 1;
      }
    } catch (SQLException e) {
      System.out.println("delete failed: " + e.getMessage());
      return false;
    }
  }




}
