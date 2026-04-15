package stackotterflow.pagekeeper;

/**
 * File for simple testing
 * @author Zeyad Abdelkader
 * created: 04/13/26
 * @since 0.1.0
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookObservableListTest {

    @Test
    void observableListReflectsAddEditRemove() {
        ObservableList<Book> books = FXCollections.observableArrayList();

        Book book = new Book(1, "Clean Code", "Robert Martin", "123", 464, "Coding book");

        books.add(book);
        assertEquals(1, books.size());
        assertEquals("Clean Code", books.get(0).getTitle());

        book.setTitle("Clean Code Updated");
        assertEquals("Clean Code Updated", books.get(0).getTitle());

        books.remove(book);
        assertEquals(0, books.size());
    }
}
