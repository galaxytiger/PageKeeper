package stackotterflow.pagekeeper;

import javafx.application.Application;
import javafx.stage.Stage;

public class HelloApplication extends Application {

  @Override
  public void start(Stage stage) {
    DatabaseManager databaseManager = new DatabaseManager();
    SceneFactory sceneFactory = new SceneFactory(stage, databaseManager);

    sceneFactory.showLogin();
  }
}
