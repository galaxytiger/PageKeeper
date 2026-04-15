package stackotterflow.pagekeeper;

/**
 * File for simple testing
 * @author Zeyad Abdelkader
 * created: 04/13/26
 * @since 0.1.0
 */

import javafx.beans.property.SimpleStringProperty;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookBindingTest {

    @Test
    void titlePropertyBindsCorrectly() {
        Book book = new Book(1, "Original Title", "Author", "123", 100, "Summary");
        SimpleStringProperty labelText = new SimpleStringProperty();

        labelText.bind(book.titleProperty());

        assertEquals("Original Title", labelText.get());

        book.setTitle("Updated Title");

        assertEquals("Updated Title", labelText.get());
    }
}
