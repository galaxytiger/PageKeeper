/**
 * @author Anthony Torres
 * Interface to define database operations available for Book
 * created: 4/13/2026
 * @since 0.1.0
 */
package stackotterflow.pagekeeper;

public interface BookDao {
  boolean insert(Book book);
  Book queryById(int id);
  boolean update(Book book);
  boolean delete(int id);
}
