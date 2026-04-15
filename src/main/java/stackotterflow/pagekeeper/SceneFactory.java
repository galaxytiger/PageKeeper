package stackotterflow.pagekeeper;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
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
}
