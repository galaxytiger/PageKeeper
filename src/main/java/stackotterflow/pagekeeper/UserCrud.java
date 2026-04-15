/**
 * @author Anthony Torres
 * Implements database CRUD operations for User records INSERT, READ, UPDATE, DELETE
 * created: 4/13/2026
 * @since 0.1.0
 */
package stackotterflow.pagekeeper;
import java.sql.*;

public class UserCrud implements UserDao {
  private final DatabaseManager databaseManager;

  public UserCrud(DatabaseManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public boolean insert(User user) {
    String sql = """
      INSERT INTO users (role, username, password)
      VALUES (?, ?, ?)
      """;

    try {
      Connection connection = databaseManager.getConnection();
      if (connection == null) {
        return false;
      }

    try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, user.getRole());
      stmt.setString(2, user.getUsername());
      stmt.setString(3, user.getPassword());

      int rowsAffected = stmt.executeUpdate();
      if (rowsAffected != 1) {
        return false;
      }

      try (ResultSet genKeys = stmt.getGeneratedKeys()) {
        if (genKeys.next()) {
          user.setUserId(genKeys.getInt(1));
        }
      }

      return true;
     }
    } catch (SQLException e) {
      System.err.println("insert user failed: " + e.getMessage());
      return false;
    }
  }

  public User queryByUsername(String username) {
    String sql = """
    SELECT user_id, role, username, password
    FROM users
    WHERE username = ?
    """;

    try {
      Connection connection = databaseManager.getConnection();
      if (connection == null) {
        return null;
      }

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, username);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return new User(
              rs.getInt("user_id"),
              rs.getString("role"),
              rs.getString("username"),
              rs.getString("password")
          );
        }
      }
     }
    } catch (SQLException e) {
      System.err.println("queryByUsername failed: " + e.getMessage());
    }

    return null;
  }

  @Override
  public User queryById(int userId) {
    String sql = """
      SELECT user_id, role, username, password
      FROM users
      WHERE user_id = ?
      """;

    try{
      Connection connection = databaseManager.getConnection();
      if (connection == null) {
        return null;
      }

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, userId);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return new User(
              rs.getInt("user_id"),
              rs.getString("role"),
              rs.getString("username"),
              rs.getString("password")
          );
        }
      }
     }
    } catch (SQLException e) {
      System.err.println("queryById user failed: " + e.getMessage());
    }

    return null;
  }

  @Override
  public boolean update(User user) {
    String sql = """
      UPDATE users
      SET role = ?, username = ?, password = ?
      WHERE user_id = ?
      """;

    try {
      Connection connection = databaseManager.getConnection();
      if (connection == null) {
        return false;
      }

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, user.getRole());
      stmt.setString(2, user.getUsername());
      stmt.setString(3, user.getPassword());
      stmt.setInt(4, user.getUserId());

      return stmt.executeUpdate() == 1;
     }
    } catch (SQLException e) {
      System.err.println("update user failed: " + e.getMessage());
      return false;
    }
  }

  @Override
  public boolean delete(int userId) {
    String sql = "DELETE FROM users WHERE user_id = ?";

    try {
      Connection connection = databaseManager.getConnection();
      if (connection == null) {
        return false;
      }

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, userId);
      return stmt.executeUpdate() == 1;
     }
    } catch (SQLException e) {
      System.err.println("delete user failed: " + e.getMessage());
      return false;
    }
  }
}
