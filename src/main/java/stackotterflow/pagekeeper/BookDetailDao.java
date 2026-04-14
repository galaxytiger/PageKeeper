/**
 * @author Anthony Torres
 * <p>
 * created: 4/13/2026
 * @since 0.1.0
 */
package stackotterflow.pagekeeper;

public interface BookDetailDao {
  boolean insert(BookDetail bookDetail);
  BookDetail queryById(int detailId);
  boolean update(BookDetail bookDetail);
  boolean delete(int detailId);
}
