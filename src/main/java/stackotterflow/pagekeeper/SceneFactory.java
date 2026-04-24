package stackotterflow.pagekeeper;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SceneFactory {
    private final Stage stage;
    private final DatabaseManager databaseManager;

    public SceneFactory(Stage stage, DatabaseManager databaseManager) {
        this.stage = stage;
        this.databaseManager = databaseManager;
    }

    public void showDashboard(User currentUser){
        DashBoard dashBoard = new DashBoard(databaseManager, currentUser, this);
        Parent root = dashBoard.createView();

        Scene scene = new Scene(root, 900, 500);
        stage.setTitle("PageKeeper Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    public void showLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Parent root = loader.load();
            LoginController controller = loader.getController();
            controller.setup(databaseManager, this);

            Scene scene = new Scene(root, 600, 400);
            stage.setTitle("PageKeeper Login");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            AppAlerts.showError("Load Error", "Could not load login screen:\n" + e.getMessage());
        }
    }

    public void showRegistration () {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("registration-view.fxml"));
            Parent root = loader.load();
            RegistrationController controller = loader.getController();
            controller.setup(databaseManager, this);

            Scene scene = new Scene(root, 600, 400);
            stage.setTitle("PageKeeper Registration");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            AppAlerts.showError("Load Error", "Could not load login screen:\n" + e.getMessage());
        }
    }

    public void showAddBook(User currentUser){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addBook-view.fxml"));
            Parent root = loader.load();

            BookController controller = loader.getController();
            controller.setup(databaseManager, this, currentUser);

            Scene scene = new Scene(root, 1000, 700);
            stage.setTitle("Add Book");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            AppAlerts.showError("Load Error", "Could not load add book screen:\n" + e.getMessage());
        }
    }

    public void showEditBook(User currentUser, int bookId){
        Label label = new Label("Edit Book Placeholder for Book ID: " + bookId);
        StackPane root = new StackPane(label);

        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Edit Book");
        stage.setScene(scene);
        stage.show();
    }
}
