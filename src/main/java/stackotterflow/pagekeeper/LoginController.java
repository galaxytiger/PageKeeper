/**
 * @author Anthony Torres
 * Log in Scene allowing users to log in. Checks db to see if user exists, if not, prompts to register
 * created: 4/20/2026
 * @since 0.1.0
 */
package stackotterflow.pagekeeper;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

  @FXML
  private Label welcome;

  @FXML
  private Label prompt;

  @FXML
  private TextField usernameField;

  @FXML
  private PasswordField passwordField;

  private DatabaseManager databaseManager;
  private SceneFactory sceneFactory;
  private UserCrud userCrud;

  public void setup(DatabaseManager databaseManager, SceneFactory sceneFactory) {
    this.databaseManager = databaseManager;
    this.sceneFactory = sceneFactory;
    this.userCrud = new UserCrud(databaseManager);
  }

  @FXML
  public void handleLogin() {
    String username = usernameField.getText().trim();
    String password = passwordField.getText();

    if (username.isEmpty() || password.isEmpty()) {
      prompt.setText("Please enter your username and password");
      return;
    }

    User user = userCrud.queryByUsername(username);

    if (user == null) {
      prompt.setText("User not found, please check username or register");
    }

    if (!user.getPassword().equals(password)) {
      prompt.setText("Incorrect password. please try again");
    }
    prompt.setText("Login successful.");
    sceneFactory.showDashboard(user);
  }

  @FXML
  private void handleRegistration() {
    prompt.setText("Need to set up Registration");
  }

}
