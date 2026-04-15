package stackotterflow.pagekeeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

  @Override
  public void start(Stage stage) {
    DatabaseManager databaseManager = new DatabaseManager();

    // Temporary test user
    User testUser = new User(1, "user", "claudio", "password");

    SceneFactory sceneFactory = new SceneFactory(stage, databaseManager);
    sceneFactory.showDashboard(testUser);
  }
}
