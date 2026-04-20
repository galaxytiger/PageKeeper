/**
 * @author Anthony Torres
 * <p>
 * created: 4/20/2026
 * @since 0.1.0
 */
package stackotterflow.pagekeeper;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistrationController {
  @FXML
  private Label registerWelcome;

  @FXML
  private TextField registerUsername;

  @FXML
  private PasswordField registerPassword;

  private SceneFactory sceneFactory;
  private UserCrud userCrud;

  public void setup(DatabaseManager databaseManager, SceneFactory sceneFactory) {
    this.sceneFactory = sceneFactory;
    this.userCrud = new UserCrud(databaseManager);
  }

  @FXML
  private void handleRegister() {
    String username = registerUsername.getText().trim();
    String password = registerPassword.getText().trim();

    if (username.isEmpty() || password.isEmpty()) {
      registerWelcome.setText("Please enter a username and password.");
      return;
    }

    if (!username.matches("[A-Za-z0-9]+") || !password.matches("[A-Za-z0-9]+")) {
      registerWelcome.setText("Use letters and numbers only.");
      return;
    }

    User existingUser = userCrud.queryByUsername(username);
    if (existingUser != null) {
      registerWelcome.setText("Username already exists.");
      return;
    }

    User newUser = new User("User", username, password);
    boolean inserted = userCrud.insert(newUser);

    if (inserted) {
      registerWelcome.setText("Account created successfully.");
      sceneFactory.showLogin();
    } else {
      registerWelcome.setText("Could not create account.");
    }
  }

  @FXML
  private void handleBackToLogin() {
    sceneFactory.showLogin();
  }

}
