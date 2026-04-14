package stackotterflow.pagekeeper;

import javafx.scene.control.Alert;

public class AppAlerts {
    public static void showWarning(String title, String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
