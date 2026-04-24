package stackotterflow.pagekeeper;

/**
 * @author Anthony Torres
 * Test database CRUD operations for Book progress records INSERT, READ, UPDATE, DELETE
 * created: 4/20/2026
 * @since 0.1.0
 */

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookDetailCrudTest {
  private DatabaseManager db;
  private UserCrud userCrud;
  private BookCrud bookCrud;
  private BookDetailCrud bookDetailCrud;

  @BeforeEach
  void setUp() {
    db = new DatabaseManager();
    userCrud = new UserCrud(db);
    bookCrud = new BookCrud(db);
    bookDetailCrud = new BookDetailCrud(db);
  }

  @AfterEach
  void tearDown() throws Exception {
    db.close();
  }

  private String fakeUsername() {
    return "user" + ThreadLocalRandom.current().nextInt(1_000_000);
  }

  private String fakeIsbn() {
    long randIsbn = ThreadLocalRandom.current().nextLong(1_000_000_000_000L, 10_000_000_000_000L);
    return String.valueOf(randIsbn);
  }

  private User makeUser() {
    User user = new User("User", fakeUsername(), "1234");
    assertTrue(userCrud.insert(user));
    assertTrue(user.getUserId() > 0);
    return user;
  }

  private Book makeBook() {
    Book book = new Book("Dune", "Frank Herbert", fakeIsbn(), 412, "Sci-fi", 1965);
    assertTrue(bookCrud.insert(book));
    assertTrue(book.getBookId() > 0);
    return book;
  }


  @Test
  void insert() {
    User user = makeUser();
    Book book = makeBook();

    BookDetail detail = new BookDetail(
        user.getUserId(),
        book.getBookId(),
        "In Progress",
        50,
        false,
        null,
        5,
        "Great so far"
    );

    boolean inserted = bookDetailCrud.insert(detail);

    assertTrue(inserted);
    assertTrue(detail.getDetailId() > 0);

    BookDetail savedDetail = bookDetailCrud.queryById(detail.getDetailId());
    assertNotNull(savedDetail);
    assertEquals(user.getUserId(), savedDetail.getUserId());
    assertEquals(book.getBookId(), savedDetail.getBookId());
    assertEquals("In Progress", savedDetail.getStatus());
    assertEquals(50, savedDetail.getCurrentPage());
    assertFalse(savedDetail.isBorrowed());
    assertNull(savedDetail.getReturnDate());
    assertEquals(5, savedDetail.getRating());
    assertEquals("Great so far", savedDetail.getReview());
  }

  @Test
  void queryById() {
    User user = makeUser();
    Book book = makeBook();

    BookDetail detail = new BookDetail(
        user.getUserId(),
        book.getBookId(),
        "Want to Read",
        0,
        false,
        null,
        null,
        null
    );
    assertTrue(bookDetailCrud.insert(detail));
    assertTrue(detail.getDetailId() > 0);

    BookDetail foundDetail = bookDetailCrud.queryById(detail.getDetailId());

    assertNotNull(foundDetail);
    assertEquals(detail.getDetailId(), foundDetail.getDetailId());
    assertEquals(user.getUserId(), foundDetail.getUserId());
    assertEquals(book.getBookId(), foundDetail.getBookId());
    assertEquals("Want to Read", foundDetail.getStatus());
    assertEquals(0, foundDetail.getCurrentPage());
    assertFalse(foundDetail.isBorrowed());
    assertNull(foundDetail.getRating());
    assertNull(foundDetail.getReview());
  }

  @Test
  void update() {
    User user = makeUser();
    Book book = makeBook();

    BookDetail detail = new BookDetail(
        user.getUserId(),
        book.getBookId(),
        "Want to Read",
        0,
        false,
        null,
        null,
        null
    );
    assertTrue(bookDetailCrud.insert(detail));

    detail.setStatus("Read");
    detail.setCurrentPage(412);
    detail.setBorrowed(true);
    detail.setReturnDate("2026-05-01");
    detail.setRating(5);
    detail.setReview("Excellent");

    boolean updated = bookDetailCrud.update(detail);
    assertTrue(updated);

    BookDetail updatedDetail = bookDetailCrud.queryById(detail.getDetailId());
    assertNotNull(updatedDetail);
    assertEquals("Read", updatedDetail.getStatus());
    assertEquals(412, updatedDetail.getCurrentPage());
    assertTrue(updatedDetail.isBorrowed());
    assertEquals("2026-05-01", updatedDetail.getReturnDate());
    assertEquals(5, updatedDetail.getRating());
    assertEquals("Excellent", updatedDetail.getReview());
  }

  @Test
  void delete() {
    User user = makeUser();
    Book book = makeBook();

    BookDetail detail = new BookDetail(
        user.getUserId(),
        book.getBookId(),
        "In Progress",
        25,
        false,
        null,
        null,
        null
    );
    assertTrue(bookDetailCrud.insert(detail));

    boolean deleted = bookDetailCrud.delete(detail.getDetailId());

    assertTrue(deleted);
    assertNull(bookDetailCrud.queryById(detail.getDetailId()));
  }
}