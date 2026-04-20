package stackotterflow.pagekeeper;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Anthony Torres
 * Testing database CRUD operations for User records INSERT, READ, UPDATE, DELETE
 * created: 4/13/2026
 * @since 0.1.0
 */

import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class UserCrudTest {

  private DatabaseManager db;
  private UserCrud userCrud;

  @BeforeEach
  void setUp() {
    db = new DatabaseManager();
    userCrud = new UserCrud(db);
  }

  @AfterEach
  void tearDown() throws Exception {
    db.close();
  }

  @Test
  void insert() {
    String username = "anthony_" + ThreadLocalRandom.current().nextInt(100000);
    User user = new User("User", username, "1234");

    boolean result = userCrud.insert(user);

    assertTrue(result);
    assertTrue(user.getUserId() > 0);

    User insertedUser = userCrud.queryById(user.getUserId());

    assertNotNull(insertedUser);
    assertEquals(user.getUserId(), insertedUser.getUserId());
    assertEquals("User", insertedUser.getRole());
    assertEquals(username, insertedUser.getUsername());
    assertEquals("1234", insertedUser.getPassword());
  }

  @Test
  void queryById() {
    String username = "anthony_" + ThreadLocalRandom.current().nextInt(100000);
    User user = new User("User", username, "1234");

    boolean inserted = userCrud.insert(user);
    assertTrue(inserted);

    User insertedUser = userCrud.queryByUsername(username);
    assertNotNull(insertedUser);

    User foundUser = userCrud.queryById(insertedUser.getUserId());

    assertNotNull(foundUser);
    assertEquals(insertedUser.getUserId(), foundUser.getUserId());
    assertEquals("User", foundUser.getRole());
    assertEquals(username, foundUser.getUsername());
    assertEquals("1234", foundUser.getPassword());
  }

  @Test
  void update() {
    String username = "anthony_" + ThreadLocalRandom.current().nextInt(100000);
    User user = new User("User", username, "1234");

    boolean inserted = userCrud.insert(user);
    assertTrue(inserted);
    assertTrue(user.getUserId() > 0);

    user.setRole("Admin");
    user.setPassword("5678");

    boolean updated = userCrud.update(user);
    assertTrue(updated);

    User updatedUser = userCrud.queryById(user.getUserId());

    assertNotNull(updatedUser);
    assertEquals("Admin", updatedUser.getRole());
    assertEquals(username, updatedUser.getUsername());
    assertEquals("5678", updatedUser.getPassword());
  }

  @Test
  void delete() {
    String username = "anthony_" + ThreadLocalRandom.current().nextInt(100000);
    User user = new User("User", username, "1234");

    boolean inserted = userCrud.insert(user);
    assertTrue(inserted);

    User insertedUser = userCrud.queryByUsername(username);
    assertNotNull(insertedUser);

    boolean deleted = userCrud.delete(insertedUser.getUserId());
    assertTrue(deleted);

    User deletedUser = userCrud.queryById(insertedUser.getUserId());
    assertNull(deletedUser);
  }
}