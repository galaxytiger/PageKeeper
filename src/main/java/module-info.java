module stackotterflow.pagekeeper {
  requires javafx.controls;
  requires javafx.fxml;

  requires org.controlsfx.controls;
  requires com.dlsc.formsfx;
  requires net.synedra.validatorfx;
  requires org.kordamp.ikonli.javafx;

  opens stackotterflow.pagekeeper to javafx.fxml;
  exports stackotterflow.pagekeeper;
}