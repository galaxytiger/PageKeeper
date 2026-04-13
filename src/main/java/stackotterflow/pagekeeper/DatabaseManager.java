/**
 * @author Anthony Torres
 * A SQLite DB that supports all four CRUD operations: insert, read, update, and delete
 * created: 4/12/26
 * @since 0.1.0
 */
package stackotterflow.pagekeeper;
import java.sql.*;

public class DatabaseManager {
  private static final String DB_URL = "jdbc:sqlite:app.db";
  private Connection connection;

  public DatabaseManager() {
    try {
      connection = DriverManager.getConnection(DB_URL);
      System.out.println("Database connected.");
      createTables();
    } catch (SQLException e) {
      System.err.println("Connection failed: " + e.getMessage());
    }
  }

  private void createTables() {
    String createRolesTable = """
        CREATE TABLE IF NOT EXISTS roles (
            role_id INTEGER PRIMARY KEY AUTOINCREMENT,
            role_name TEXT NOT NULL UNIQUE
        )
        """;

    String createUsersTable = """
        CREATE TABLE IF NOT EXISTS users (
            user_id INTEGER PRIMARY KEY AUTOINCREMENT,
            role_id INTEGER NOT NULL,
            username TEXT NOT NULL UNIQUE,
            email TEXT NOT NULL UNIQUE,
            password TEXT NOT NULL,
            FOREIGN KEY (role_id) REFERENCES roles(role_id)
        )
        """;

    String createBooksTable = """
        CREATE TABLE IF NOT EXISTS books (
            book_id INTEGER PRIMARY KEY AUTOINCREMENT,
            user_id INTEGER NOT NULL,
            title TEXT NOT NULL,
            author TEXT NOT NULL,
            isbn TEXT,
            total_pages INTEGER NOT NULL,
            current_page INTEGER DEFAULT 0,
            progress_percent INTEGER DEFAULT 0,
            status TEXT NOT NULL,
            rating REAL,
            date_added TEXT,
            date_completed TEXT,
            return_date TEXT,
            api_source_id TEXT,
            FOREIGN KEY (user_id) REFERENCES users(user_id)
        )
        """;

    try (Statement stmt = connection.createStatement()) {
      stmt.execute(createRolesTable);
      stmt.execute(createUsersTable);
      stmt.execute(createBooksTable);
      stmt.executeUpdate("INSERT OR IGNORE INTO roles (role_id, role_name) VALUES (1, 'User')");
      stmt.executeUpdate("INSERT OR IGNORE INTO roles (role_id, role_name) VALUES (2, 'Admin')");
      System.out.println("Tables created successfully.");
    } catch (SQLException e) {
      System.err.println("createTables failed: " + e.getMessage());
    }
  }
}

