/**
 * @author Anthony Torres
 * A SQLite DB that supports all four CRUD operations: insert, read, update, and delete
 * created: 4/12/26
 * @since 0.1.0
 */
package stackotterflow.pagekeeper;
import java.sql.*;

public class DatabaseManager {
  private static final String DB_URL = "jdbc:sqlite:pagekeeper.db";
  private Connection connection;

  public DatabaseManager() {
    try {
      connection = DriverManager.getConnection(DB_URL);
      try (Statement stmt = connection.createStatement()) {
        stmt.execute("PRAGMA foreign_keys = ON;");
      }
      System.out.println("Database connected.");
      initializeDatabase();
    } catch (SQLException e) {
      System.err.println("Connection failed: " + e.getMessage());
    }
  }

  public Connection getConnection() {
    return connection;
  }

  // tables for db
  private void initializeDatabase() {
    // user table
    String createUsersTable = """
      CREATE TABLE IF NOT EXISTS users (
          user_id INTEGER PRIMARY KEY AUTOINCREMENT,
          role TEXT NOT NULL,
          username TEXT NOT NULL UNIQUE,
          password TEXT NOT NULL
      );
      """;
    // table for book metadata
    String createBooksTable = """
      CREATE TABLE IF NOT EXISTS books (
          book_id INTEGER PRIMARY KEY AUTOINCREMENT,
          title TEXT NOT NULL,
          author TEXT NOT NULL,
          isbn TEXT UNIQUE,
          total_pages INTEGER NOT NULL CHECK (total_pages > 0)
      );
      """;
    // table for book progress
    String createBookDetailsTable = """
      CREATE TABLE IF NOT EXISTS book_details (
          detail_id INTEGER PRIMARY KEY AUTOINCREMENT,
          user_id INTEGER NOT NULL,
          book_id INTEGER NOT NULL,
          status TEXT NOT NULL CHECK (status IN ('Want to Read', 'In Progress', 'Read')),
          current_page INTEGER DEFAULT 0 CHECK (current_page >= 0),
          borrowed INTEGER NOT NULL DEFAULT 0 CHECK (borrowed IN (0, 1)),
          return_date TEXT,
          rating INTEGER CHECK (rating BETWEEN 1 AND 5 OR rating IS NULL),
          review TEXT,
          FOREIGN KEY (user_id) REFERENCES users(user_id),
          FOREIGN KEY (book_id) REFERENCES books(book_id),
          UNIQUE (user_id, book_id)
      );
      """;


    try (Statement stmt = connection.createStatement()) {
      stmt.execute(createUsersTable);
      stmt.execute(createBooksTable);
      stmt.execute(createBookDetailsTable);
      System.out.println("Tables created successfully.");
    } catch (SQLException e) {
      System.err.println("createTables failed: " + e.getMessage());
    }
  }

  public void close() throws SQLException {
    if (connection != null) {
      connection.close();
    }
  }
}

