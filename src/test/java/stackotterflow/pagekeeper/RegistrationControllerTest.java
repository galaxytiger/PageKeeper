package stackotterflow.pagekeeper;

/**
 * @author Anthony Torres/Grayson Debenedetto
 * Testing Registration Controller logic
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
import java.lang.reflect.Method;

class RegistrationControllerTest {




  // A helper method to test via reflection
  private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
    Field field = target.getClass().getDeclaredField(fieldName);
    field.setAccessible(true);
    field.set(target, value);
  }

  @BeforeAll
  static void initJavaFX() {
    try {
      Platform.startup(() -> {});
    } catch (IllegalStateException e) {
      // JavaFX already initialized
    }
  }
  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void setup() {
  }

  @Test
  void handleRegister() {
  }

  @Test
  void testInvalidCharacter() throws Exception {
    RegistrationController registration = new RegistrationController();

    Label prompt = new Label();
    TextField usernameField = new TextField();
    PasswordField passwordField = new PasswordField();

    usernameField.setText("@");
    passwordField.setText("@");

    setPrivateField(registration, "registerUsername", usernameField);
    setPrivateField(registration, "registerPassword", passwordField);
    setPrivateField(registration, "registerWelcome", prompt); // or "prompt" if you add one

    Method method = RegistrationController.class.getDeclaredMethod("handleRegister");
    method.setAccessible(true);
    method.invoke(registration);

    assertEquals("Use letters and numbers only.", prompt.getText());
  }

  @Test
  void testEmptyFields() throws Exception {
    RegistrationController registration = new RegistrationController();

    Label prompt = new Label();
    TextField usernameField = new TextField();
    PasswordField passwordField = new PasswordField();

    usernameField.setText("");
    passwordField.setText("");

    setPrivateField(registration, "registerUsername", usernameField);
    setPrivateField(registration, "registerPassword", passwordField);
    setPrivateField(registration, "registerWelcome", prompt); // or "prompt" if you add one

    Method method = RegistrationController.class.getDeclaredMethod("handleRegister");
    method.setAccessible(true);
    method.invoke(registration);

    assertEquals("Please enter a username and password.", prompt.getText());
  }
}