package stackotterflow.pagekeeper;


/**
 * File for book controller related to issue #12 to implement observable list and bind for updates
 * @author Zeyad Abdelkader
 * created: 04/13/26
 * @since 0.1.0
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;


public class BookController {

    @FXML private TableView<Book> bookTable;
    @FXML private TableColumn<Book, Number> idColumn;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, String> isbnColumn;
    @FXML private TableColumn<Book, Number> pagesColumn;
    @FXML private TableColumn<Book, String> summaryColumn;

    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField isbnField;
    @FXML private TextField totalPagesField;
    @FXML private TextArea summaryField;

    private final DatabaseManager databaseManager = new DatabaseManager();
    private final BookCrud bookCrud = new BookCrud(databaseManager);
    private final ObservableList<Book> books = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().bookIdProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        isbnColumn.setCellValueFactory(cellData -> cellData.getValue().isbnProperty());
        pagesColumn.setCellValueFactory(cellData -> cellData.getValue().totalPagesProperty());
        summaryColumn.setCellValueFactory(cellData -> cellData.getValue().summaryProperty());

        bookTable.setItems(books);

        loadBooks();

        bookTable.getSelectionModel().selectedItemProperty().addListener((obs, oldBook, selectedBook) -> {
            if (selectedBook != null) {
                titleField.setText(selectedBook.getTitle());
                authorField.setText(selectedBook.getAuthor());
                isbnField.setText(selectedBook.getIsbn());
                totalPagesField.setText(String.valueOf(selectedBook.getTotalPages()));
                summaryField.setText(selectedBook.getSummary());
            }
        });
    }

    private void loadBooks() {
        books.setAll(bookCrud.getAllBooks());
    }

    @FXML
    private void handleAddBook() {
        try {
            String title = titleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();
            int totalPages = Integer.parseInt(totalPagesField.getText());
            String summary = summaryField.getText();

            Book newBook = new Book(title, author, isbn, totalPages, summary);

            if (bookCrud.insert(newBook)) {
                books.add(newBook);
                clearForm();
                AppAlerts.showSuccess("Book added successfully.");
            } else {
                AppAlerts.showError("Add Failed", "Could not add book.");
            }
        } catch (NumberFormatException e) {
            AppAlerts.showError("Invalid Input", "Total pages must be a number.");
        }
    }

    @FXML
    private void handleUpdateBook() {
        Book selectedBook = bookTable.getSelectionModel().getSelectedItem();

        if (selectedBook == null) {
            AppAlerts.showWarning("No Selection", "Please select a book to update.");
            return;
        }

        try {
            selectedBook.setTitle(titleField.getText());
            selectedBook.setAuthor(authorField.getText());
            selectedBook.setIsbn(isbnField.getText());
            selectedBook.setTotalPages(Integer.parseInt(totalPagesField.getText()));
            selectedBook.setSummary(summaryField.getText());

            if (bookCrud.update(selectedBook)) {
                bookTable.refresh();
                AppAlerts.showSuccess("Book updated successfully.");
            } else {
                AppAlerts.showError("Update Failed", "Could not update book.");
            }
        } catch (NumberFormatException e) {
            AppAlerts.showError("Invalid Input", "Total pages must be a number.");
        }
    }

    @FXML
    private void handleDeleteBook() {
        Book selectedBook = bookTable.getSelectionModel().getSelectedItem();

        if (selectedBook == null) {
            AppAlerts.showWarning("No Selection", "Please select a book to delete.");
            return;
        }

        if (bookCrud.delete(selectedBook.getBookId())) {
            books.remove(selectedBook);
            clearForm();
            AppAlerts.showSuccess("Book deleted successfully.");
        } else {
            AppAlerts.showError("Delete Failed", "Could not delete book.");
        }
    }

    private void clearForm() {
        titleField.clear();
        authorField.clear();
        isbnField.clear();
        totalPagesField.clear();
        summaryField.clear();
        bookTable.getSelectionModel().clearSelection();
    }
}
