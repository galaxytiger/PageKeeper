/**
 * @author Anthony Torres
 * Interface to define database operations available for User
 * created: 4/13/2026
 * @since 0.1.0
 */
package stackotterflow.pagekeeper;

public interface UserDao {
  boolean insert(User user);
  User queryById(int userId);
  boolean update(User user);
  boolean delete(int userId);
}
