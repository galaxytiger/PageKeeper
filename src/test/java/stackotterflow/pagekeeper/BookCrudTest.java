package stackotterflow.pagekeeper;

/**
 * @author Anthony Torres
 * Test database CRUD operations for Book records INSERT, READ, UPDATE, DELETE
 * created: 4/20/2026
 * @since 0.1.0
 */

import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookCrudTest {

  private DatabaseManager db;
  private BookCrud bookCrud;


  @BeforeEach
  void setUp() {
    db = new DatabaseManager();
    bookCrud = new BookCrud(db);
  }

  @AfterEach
  void tearDown() throws Exception {
    db.close();
  }

  private String fakeISBN() {
    long randIsbn = ThreadLocalRandom.current().nextLong(1_000_000_000_000L, 10_000_000_000_000L);
    return String.valueOf(randIsbn);
  }

  @Test

  void insert() {
    String isbn = fakeISBN();
    Book book = new Book("Project Hail Mary", "Andy Weir", isbn, 496, "Not interstellar", 2021);
    boolean inserted = bookCrud.insert(book);

    assertTrue(inserted);
    assertTrue(book.getBookId() > 0);
    Book savedBook = bookCrud.queryById(book.getBookId());
    assertNotNull(savedBook);
    assertEquals("Project Hail Mary", savedBook.getTitle());
    assertEquals("Andy Weir", savedBook.getAuthor());
    assertEquals(isbn, savedBook.getIsbn());
    assertEquals(496, savedBook.getTotalPages());
    assertEquals("Not interstellar", savedBook.getSummary());
  }

  @Test
  void queryById() {
    String isbn = fakeISBN();
    Book book = new Book("Dune", "Frank Herbert", isbn, 412, "Jesus in space", 1965);
    assertTrue(bookCrud.insert(book));

    Book savedBook = bookCrud.queryById(book.getBookId());

    assertNotNull(savedBook);
    assertEquals(book.getBookId(), savedBook.getBookId());
    assertEquals("Dune", savedBook.getTitle());
    assertEquals("Frank Herbert", savedBook.getAuthor());
    assertEquals(isbn, savedBook.getIsbn());
    assertEquals(412, savedBook.getTotalPages());
    assertEquals("Jesus in space", savedBook.getSummary());
  }

  @Test
  void update() {
    String isbn = fakeISBN();
    Book book = new Book("Invincible", "unknown", isbn, 1, "unknown", 0);
    assertTrue(bookCrud.insert(book));

    book.setTitle("Invincible Vol. 1");
    book.setAuthor("Robert Kirkman");
    book.setIsbn(isbn);
    book.setTotalPages(1136);
    book.setSummary("superhero guy in space");

    boolean updated = bookCrud.update(book);
    assertTrue(updated);

    Book updatedBook = bookCrud.queryById(book.getBookId());
    assertNotNull(updatedBook);
    assertEquals("Invincible Vol. 1", updatedBook.getTitle());
    assertEquals("Robert Kirkman", updatedBook.getAuthor());
    assertEquals(isbn, updatedBook.getIsbn());
    assertEquals(1136, updatedBook.getTotalPages());
    assertEquals("superhero guy in space", updatedBook.getSummary());
  }

  @Test
  void delete() {
    String isbn = fakeISBN();
    Book book = new Book("Dune", "Frank Herbert", isbn, 412, "Jesus in space", 1965);
    assertTrue(bookCrud.insert(book));
    boolean deleted = bookCrud.delete(book.getBookId());

    assertTrue(deleted);
    assertNull(bookCrud.queryById(book.getBookId()));
  }
}