package stackotterflow.pagekeeper;

import static org.junit.jupiter.api.Assertions.*;

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

    User insertedUser = userCrud.queryByUsername(username);
    assertNotNull(insertedUser);

    insertedUser.setRole("Admin");
    insertedUser.setPassword("5678");

    boolean updated = userCrud.update(insertedUser);
    assertTrue(updated);

    User updatedUser = userCrud.queryById(insertedUser.getUserId());

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