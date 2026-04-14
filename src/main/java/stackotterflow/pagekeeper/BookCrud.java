/**
 * @author Anthony Torres
 * <p>
 * created: 4/13/2026
 * @since 0.1.0
 */
package stackotterflow.pagekeeper;
import java.sql.*;

public class BookCrud implements BookDao {
  private final DatabaseManager databaseManager;

  public BookCrud(DatabaseManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public boolean insert(Book book) {
    String sql = """
      INSERT INTO books (title, author, isbn, total_pages, summary)
      VALUES (?, ?, ?, ?, ?)
      """;

    try {
      Connection connection = databaseManager.getConnection();
      if (connection == null) {
        return false;
      }

      try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, book.getTitle());
        stmt.setString(2, book.getAuthor());
        stmt.setString(3, book.getIsbn());
        stmt.setInt(4, book.getTotalPages());
        stmt.setString(5, book.getSummary());

        return stmt.executeUpdate() == 1;
      }
    } catch (SQLException e) {
      System.err.println("insert failed: " + e.getMessage());
      return false;
    }
  }

  @Override
  public Book queryById(int id) {
    String sql = """
      SELECT book_id, title, author, isbn, total_pages, summary
      FROM books
      WHERE book_id = ?
      """;

    try {
      Connection connection = databaseManager.getConnection();
      if (connection == null) {
        return null;
      }

      try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, id);

        try (ResultSet rs = stmt.executeQuery()) {
          if (rs.next()) {
            return new Book(
                rs.getInt("book_id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("isbn"),
                rs.getInt("total_pages"),
                rs.getString("summary")
            );
          }
        }
      }
    } catch (SQLException e) {
      System.err.println("queryById failed: " + e.getMessage());
    }

    return null;
  }

  @Override
  public boolean update(Book book) {
    String sql = """
      UPDATE books
      SET title = ?, author = ?, isbn = ?, total_pages = ?, summary = ?
      WHERE book_id = ?
      """;

    try {
      Connection connection = databaseManager.getConnection();
      if (connection == null) {
        return false;
      }

      try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, book.getTitle());
        stmt.setString(2, book.getAuthor());
        stmt.setString(3, book.getIsbn());
        stmt.setInt(4, book.getTotalPages());
        stmt.setString(5, book.getSummary());
        stmt.setInt(6, book.getBookId());

        return stmt.executeUpdate() == 1;
      }
    } catch (SQLException e) {
      System.err.println("update failed: " + e.getMessage());
      return false;
    }
  }

  @Override
  public boolean delete(int id) {
    String sql = "DELETE FROM books WHERE book_id = ?";

    try {
      Connection connection = databaseManager.getConnection();
      if (connection == null) {
        return false;
      }

      try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, id);
        return stmt.executeUpdate() == 1;
      }
    } catch (SQLException e) {
      System.err.println("delete failed: " + e.getMessage());
      return false;
    }
  }
}
