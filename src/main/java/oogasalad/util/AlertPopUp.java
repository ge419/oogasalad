package oogasalad.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertPopUp {

  public static void show(AlertType alertType, String title, String contextText) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(contextText);
    alert.showAndWait();
  }
}
