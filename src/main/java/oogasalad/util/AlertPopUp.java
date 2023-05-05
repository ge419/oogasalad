package oogasalad.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Helper class for alerts.
 *
 * @author cgd19
 */
public class AlertPopUp {

  public static void show(AlertType alertType, String title, String contextText) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(contextText);
    alert.showAndWait();
  }
}
