/**
 * @author Anthony Torres
 * Interface to define database operations available for Book
 * created: 4/13/2026
 * @since 0.1.0
 */
package stackotterflow.pagekeeper;

import java.util.List;

public interface BookDao {
  boolean insert(Book book);
  Book queryById(int id);
  boolean update(Book book);
  boolean delete(int id);
  List<Book> getAllBooks();
}
