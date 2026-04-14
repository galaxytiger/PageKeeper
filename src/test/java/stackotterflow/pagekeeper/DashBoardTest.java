package stackotterflow.pagekeeper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DashBoardTest {

    @Test
    void bookRowStoresValuesCorrectly() {
        DashBoard.BookRow row = new DashBoard.BookRow(
                1,
                "The Hobbit",
                "J.R.R. Tolkien",
                "In Progress",
                120,
                5
        );

        assertEquals(1, row.getBookId());
        assertEquals("The Hobbit", row.getTitle());
        assertEquals("J.R.R. Tolkien", row.getAuthor());
        assertEquals("In Progress", row.getStatus());
        assertEquals(120, row.getCurrentPage());
        assertEquals(5, row.getRating());
    }

    @Test
    void bookRowAllowsZeroRating() {
        DashBoard.BookRow row = new DashBoard.BookRow(
                2,
                "Clean Code",
                "Robert C. Martin",
                "Want to Read",
                0,
                0
        );

        assertEquals(2, row.getBookId());
        assertEquals("Clean Code", row.getTitle());
        assertEquals("Robert C. Martin", row.getAuthor());
        assertEquals("Want to Read", row.getStatus());
        assertEquals(0, row.getCurrentPage());
        assertEquals(0, row.getRating());
    }
}