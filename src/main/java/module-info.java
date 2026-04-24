module stackotterflow.pagekeeper {
  requires javafx.controls;
  requires javafx.fxml;

  requires org.controlsfx.controls;
  requires com.dlsc.formsfx;
  requires net.synedra.validatorfx;
  requires org.kordamp.ikonli.javafx;
  requires java.sql;
  requires com.fasterxml.jackson.databind;
  requires java.net.http;
  requires jdk.management.agent;

  opens stackotterflow.pagekeeper to javafx.fxml;
  exports stackotterflow.pagekeeper;
}