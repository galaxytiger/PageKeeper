package stackotterflow.pagekeeper;

/**
 * File for simple testing
 * @author Zeyad Abdelkader
 * created: 04/13/26
 * @since 0.1.0
 */

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    @Test
    void bookPropertiesStoreAndUpdateValues() {
        Book book = new Book(1, "Old Title", "Old Author", "123", 100, "Old Summary");

        assertEquals(1, book.getBookId());
        assertEquals("Old Title", book.getTitle());
        assertEquals("Old Author", book.getAuthor());
        assertEquals("123", book.getIsbn());
        assertEquals(100, book.getTotalPages());
        assertEquals("Old Summary", book.getSummary());

        book.setTitle("New Title");
        book.setAuthor("New Author");
        book.setIsbn("456");
        book.setTotalPages(250);
        book.setSummary("New Summary");

        assertEquals("New Title", book.getTitle());
        assertEquals("New Author", book.getAuthor());
        assertEquals("456", book.getIsbn());
        assertEquals(250, book.getTotalPages());
        assertEquals("New Summary", book.getSummary());
    }
}
