package stackotterflow.pagekeeper;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashBoard {
    private final DatabaseManager databaseManager;
    private final User currentUser;
    private final SceneFactory sceneFactory;

    private final TableView<BookRow> tableView = new TableView<>();
    private final ObservableList<BookRow> bookRows = FXCollections.observableArrayList();
    private final Label statusLabel = new Label("Ready.");

    public DashBoard(DatabaseManager databaseManager, User currentUser, SceneFactory sceneFactory) {
        this.databaseManager = databaseManager;
        this.currentUser = currentUser;
        this.sceneFactory = sceneFactory;
    }

    public Parent createView() {
        Label titleLabel = new Label("PageKeeper Dashboard");
        titleLabel.setFont(new Font(24));

        Label welcomeLabel = new Label("Welcome, " + currentUser.getUsername() + "!");
        welcomeLabel.setFont(new Font(16));

        configureTable();

        Button refreshButton = new Button("Refresh");
        Button addButton = new Button("Add Book");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");
        Button logoutButton = new Button("Logout");

        refreshButton.setOnAction(e -> loadBooks());
        addButton.setOnAction(e -> sceneFactory.showAddBook(currentUser));
        editButton.setOnAction(e -> handleEditSelected());
        deleteButton.setOnAction(e -> handleDeleteSelected());
        logoutButton.setOnAction(e -> sceneFactory.showLogin());

        HBox buttonBar = new HBox(10, refreshButton, addButton, editButton, deleteButton, logoutButton);
        buttonBar.setAlignment(Pos.CENTER_LEFT);

        VBox topSection = new VBox(8, titleLabel, welcomeLabel, buttonBar);
        topSection.setPadding(new Insets(10));

        statusLabel.setStyle("-fx-text-fill: #444;");
        VBox bottomSection = new VBox(statusLabel);
        bottomSection.setPadding(new Insets(10, 10, 0, 10));

        BorderPane root = new BorderPane();
        root.setTop(topSection);
        root.setCenter(tableView);
        root.setBottom(bottomSection);
        root.setPadding(new Insets(10));

        loadBooks();

        return root;
    }

    private void configureTable() {
        TableColumn<BookRow, Number> idColumn = new TableColumn<>("Book ID");
        idColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getBookId()));

        TableColumn<BookRow, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitle()));

        TableColumn<BookRow, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAuthor()));

        TableColumn<BookRow, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));

        TableColumn<BookRow, Number> currentPageColumn = new TableColumn<>("Current Page");
        currentPageColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getCurrentPage()));

        TableColumn<BookRow, Number> ratingColumn = new TableColumn<>("Rating");
        ratingColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getRating()));

        tableView.getColumns().clear();
        tableView.getColumns().addAll(
                idColumn,
                titleColumn,
                authorColumn,
                statusColumn,
                currentPageColumn,
                ratingColumn
        );

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setItems(bookRows);
        tableView.setPlaceholder(new Label("No books found for this user."));
    }

    public void loadBooks() {
        bookRows.clear();

        String sql = """
            SELECT b.book_id, b.title, b.author,
                   bd.status, bd.current_page, bd.rating
            FROM books b
            JOIN book_details bd ON b.book_id = bd.book_id
            WHERE bd.user_id = ?
            ORDER BY b.title ASC
            """;

        Connection connection = databaseManager.getConnection();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, currentUser.getUserId());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int rating = rs.getObject("rating") == null ? 0 : rs.getInt("rating");

                    bookRows.add(new BookRow(
                            rs.getInt("book_id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("status"),
                            rs.getInt("current_page"),
                            rating
                    ));
                }
            }

            statusLabel.setText("Loaded " + bookRows.size() + " book(s).");
        } catch (SQLException e) {
            statusLabel.setText("Failed to load books.");
            AppAlerts.showError("Database Error", "Could not load books:\n" + e.getMessage());
        }
    }

    private void handleEditSelected() {
        BookRow selected = tableView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            AppAlerts.showWarning("No Selection", "Please select a book to edit.");
            return;
        }

        sceneFactory.showEditBook(currentUser, selected.getBookId());
    }

    private void handleDeleteSelected() {
        BookRow selected = tableView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            AppAlerts.showWarning("No Selection", "Please select a book to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Book");
        confirm.setHeaderText("Delete selected book?");
        confirm.setContentText("Are you sure you want to remove \"" + selected.getTitle() + "\" from your dashboard?");

        confirm.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                deleteBookForCurrentUser(selected.getBookId());
            }
        });
    }

    private void deleteBookForCurrentUser(int bookId) {
        String sql = """
            DELETE FROM book_details
            WHERE user_id = ? AND book_id = ?
            """;

        Connection connection = databaseManager.getConnection();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, currentUser.getUserId());
            stmt.setInt(2, bookId);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 1) {
                AppAlerts.showSuccess("Book removed from dashboard.");
                loadBooks();
            } else {
                AppAlerts.showWarning("Delete Failed", "No matching book was removed.");
            }
        } catch (SQLException e) {
            AppAlerts.showError("Database Error", "Could not delete book:\n" + e.getMessage());
        }
    }

    public static class BookRow {
        private final int bookId;
        private final String title;
        private final String author;
        private final String status;
        private final int currentPage;
        private final int rating;

        public BookRow(int bookId, String title, String author, String status, int currentPage, int rating) {
            this.bookId = bookId;
            this.title = title;
            this.author = author;
            this.status = status;
            this.currentPage = currentPage;
            this.rating = rating;
        }

        public int getBookId() {
            return bookId;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public String getStatus() {
            return status;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public int getRating() {
            return rating;
        }
    }
}