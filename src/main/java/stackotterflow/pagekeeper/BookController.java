package stackotterflow.pagekeeper;

/**
 * File for book controller related to issue #12 to implement observable list and bind for updates
 * @author Zeyad Abdelkader
 * Updated by Anthony Torres
 * created: 04/13/26
 * @since 0.2.0
 */
import java.io.IOException;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class BookController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<BookSearchResult> searchResultsTable;

    @FXML
    private TableColumn<BookSearchResult, String> resultTitleColumn;

    @FXML
    private TableColumn<BookSearchResult, String> resultAuthorColumn;

    @FXML
    private TableColumn<BookSearchResult, Number> resultYearColumn;

    @FXML
    private TableColumn<BookSearchResult, String> resultIsbnColumn;

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField isbnField;

    @FXML
    private TextField totalPagesField;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private TextField currentPageField;

    @FXML
    private TextArea summaryField;

    @FXML
    private ImageView coverImageView;

    private final ObservableList<BookSearchResult> searchResults = FXCollections.observableArrayList();

  private SceneFactory sceneFactory;
    private User currentUser;

    private BookCrud bookCrud;
    private BookDetailCrud bookDetailCrud;
    private OpenLibraryClient openLibraryClient;

    private BookSearchResult selectedResult;

    public void setup(DatabaseManager databaseManager, SceneFactory sceneFactory, User currentUser) {
      this.sceneFactory = sceneFactory;
        this.currentUser = currentUser;
        this.bookCrud = new BookCrud(databaseManager);
        this.bookDetailCrud = new BookDetailCrud(databaseManager);
        this.openLibraryClient = new OpenLibraryClient();
    }

    @FXML
    public void initialize() {
        resultTitleColumn.setCellValueFactory(cellData ->
            new SimpleStringProperty(safe(cellData.getValue().getTitle())));

        resultAuthorColumn.setCellValueFactory(cellData ->
            new SimpleStringProperty(safe(cellData.getValue().getAuthor())));

        resultYearColumn.setCellValueFactory(cellData ->
            new SimpleIntegerProperty(cellData.getValue().getYear()));

        resultIsbnColumn.setCellValueFactory(cellData ->
            new SimpleStringProperty(safe(cellData.getValue().getIsbn())));

        searchResultsTable.setItems(searchResults);

        statusComboBox.setItems(FXCollections.observableArrayList(
            "Want to Read",
            "In Progress",
            "Read"
        ));
        statusComboBox.setValue("Want to Read");
        currentPageField.setText("0");

        searchResultsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                selectedResult = newValue;
                populateFormFromSearchResult(newValue);
                loadSummaryForSelection(newValue);
            }
        });
    }

    @FXML
    private void handleSearch() {
        String query = searchField.getText() == null ? "" : searchField.getText().trim();

        if (query.isEmpty()) {
            AppAlerts.showWarning("Search Required", "Enter a title, author, or ISBN.");
            return;
        }

        try {
            List<BookSearchResult> results = openLibraryClient.searchBooks(query);
            searchResults.setAll(results);

            if (results.isEmpty()) {
                AppAlerts.showWarning("No Results", "No books found for that search.");
            }
        } catch (IOException | InterruptedException e) {
            AppAlerts.showError("Search Failed", "Could not search Open Library:\n" + e.getMessage());
        }
    }

    private void loadCoverImage(BookSearchResult result) {
        String coverUrl = openLibraryClient.buildCoverUrl(result.getCoverID());

        if (coverUrl == null) {
            coverImageView.setImage(null);
            return;
        }

        try {
            Image image = new Image(coverUrl, true);
            coverImageView.setImage(image);
        } catch (Exception e) {
            coverImageView.setImage(null);
        }
    }

    private void populateFormFromSearchResult(BookSearchResult result) {
        titleField.setText(safe(result.getTitle()));
        authorField.setText(safe(result.getAuthor()));
        isbnField.setText(safe(result.getIsbn()));
        totalPagesField.setText(result.getTotalPages() == null ? "" : String.valueOf(result.getTotalPages()));
        summaryField.setText("");

        loadCoverImage(result);
    }

    private void loadSummaryForSelection(BookSearchResult result) {
        if (result.getOpenLibkey() == null || result.getOpenLibkey().isBlank()) {
            summaryField.setText("");
            return;
        }

        try {
            String summary = openLibraryClient.fetchSummary(result.getOpenLibkey());
            summaryField.setText(summary == null ? "" : summary);
        } catch (IOException | InterruptedException e) {
            summaryField.setText("");
        }
    }

    @FXML
    private void handleAddBook() {
        if (currentUser == null) {
            AppAlerts.showError("Setup Error", "Current user was not provided to BookController.");
            return;
        }

        try {
            String title = trimToNull(titleField.getText());
            String author = trimToNull(authorField.getText());
            String isbn = trimToNull(isbnField.getText());
            Integer totalPages = parseOptionalInteger(totalPagesField.getText());
            Integer currentPage = parseOptionalInteger(currentPageField.getText());
            String summary = trimToNull(summaryField.getText());
            String status = statusComboBox.getValue();

            if (title == null || author == null) {
                AppAlerts.showWarning("Missing Fields", "Title and author are required.");
                return;
            }

            if (totalPages == null) {
                totalPages = 0;
            }

            if (currentPage == null) {
                currentPage = 0;
            }

            if (totalPages < 0 || currentPage < 0) {
                AppAlerts.showWarning("Invalid Numbers", "Pages cannot be negative.");
                return;
            }

            Integer year = selectedResult == null ? null : selectedResult.getYear();

            Book newBook = new Book(title, author, isbn, totalPages, summary, year);

            if (!bookCrud.insert(newBook)) {
                AppAlerts.showError("Save Failed", "Could not save book.");
                return;
            }

            BookDetail detail = new BookDetail(
                currentUser.getUserId(),
                newBook.getBookId(),
                status,
                currentPage,
                false,
                null,
                null,
                null
            );

            if (!bookDetailCrud.insert(detail)) {
                AppAlerts.showError("Save Failed", "Book saved, but could not link it to the current user.");
                return;
            }

            AppAlerts.showSuccess("Book added to library.");
            clearForm();
        } catch (NumberFormatException e) {
            AppAlerts.showError("Invalid Input", "Total pages and current page must be valid numbers.");
        }
    }

    @FXML
    private void handleClearForm() {
        clearForm();
    }

    @FXML
    private void handleBackToDashboard() {
        sceneFactory.showDashboard(currentUser);
    }

    private void clearForm() {
        searchField.clear();
        searchResults.clear();
        searchResultsTable.getSelectionModel().clearSelection();
        selectedResult = null;

        titleField.clear();
        authorField.clear();
        isbnField.clear();
        totalPagesField.clear();
        summaryField.clear();
        currentPageField.setText("0");
        statusComboBox.setValue("Want to Read");
        coverImageView.setImage(null);
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private Integer parseOptionalInteger(String value) {
        String trimmed = trimToNull(value);
        if (trimmed == null) {
            return null;
        }
        return Integer.parseInt(trimmed);
    }
}
