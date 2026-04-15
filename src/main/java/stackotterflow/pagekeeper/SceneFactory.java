package stackotterflow.pagekeeper;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Stack;

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
        Label label = new Label("Login Scene Placeholder");
        StackPane root = new StackPane(label);

        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    public void showAddBook(User currentUser){
        Label label = new Label("Add Book Scene Placeholder");
        StackPane root = new StackPane(label);

        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Add Book");
        stage.setScene(scene);
        stage.show();
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
