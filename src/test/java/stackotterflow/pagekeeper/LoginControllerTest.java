package stackotterflow.pagekeeper;

/**
 * @author Anthony Torres/Grayson Debenedetto
 * Testing LogIn Controller logic
 * created: 4/20/2026
 * @since 0.1.0
 */

import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;

class LoginControllerTest {




  // A helper method to test via reflection
  private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
    Field field = target.getClass().getDeclaredField(fieldName);
    field.setAccessible(true);
    field.set(target, value);
  }

  @BeforeEach
  void setUp() {

  }

  @BeforeAll
  static void initJavaFX() {
    try {
      Platform.startup(() -> {});
    } catch (IllegalStateException e) {
      // JavaFX already initialized
    }
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void setup() {
  }

  @Test
  void handleLogin() {
  }


 //Testing for username or password
  @Test
  void testFieldsEmpty() throws Exception {
    LoginController login = new LoginController();

//Manually creating new UI elements
    Label prompt = new Label();
    TextField usernameField = new TextField();
    PasswordField passwordField = new PasswordField();

    usernameField.setText("");
    passwordField.setText("");


    setPrivateField(login, "prompt", prompt);
    setPrivateField(login, "usernameField", usernameField);
    setPrivateField(login, "passwordField", passwordField);


    login.handleLogin();


    assertEquals(
            "Please enter your username and password",
            prompt.getText()
    );
  }

  //Testing that a null user returns the correct prompt
  @Test
  void testNullUser() throws Exception {
    LoginController login = new LoginController();

    Label prompt = new Label();
    TextField usernameField = new TextField();
    PasswordField passwordField = new PasswordField();

    usernameField.setText("testuser");
    passwordField.setText("password");

    setPrivateField(login, "prompt", prompt);
    setPrivateField(login, "usernameField", usernameField);
    setPrivateField(login, "passwordField", passwordField);

//Creating a new userCrud and making it return null
    UserCrud fakeCrud = new UserCrud(null) {
      @Override
      public User queryByUsername(String username) {
        return null;
      }
    };

    setPrivateField(login, "userCrud", fakeCrud);


    login.handleLogin();


    assertEquals(
            "User not found, please check username or register",
            prompt.getText()
    );
  }



}