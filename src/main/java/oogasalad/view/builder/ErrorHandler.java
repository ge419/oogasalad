package oogasalad.view.builder;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Contains static methods that will deal with any error messages you want to display.
 *
 * @author tmh85
 * @author jf295
 */
public class ErrorHandler {

  /**
   * <p>Create an alert prompt that will notify the user of a given error message.</p>
   *
   * @param errorMessage desired error message to show
   */
  public static void displayError(String errorMessage) {
    Alert alert = createAlert(errorMessage);
    alert.showAndWait();
  }

  /**
   * <p>Create an alert prompt that will notify the user of a given error message.</p>
   * <p>Also allows you to set the header message of the alert window that will appear.</p>
   *
   * @param errorMessage  desired error message to show
   * @param headerMessage desired header message to show
   */
  public static void displayError(String errorMessage, String headerMessage) {
    Alert alert = createAlert(errorMessage);
    alert.setHeaderText(headerMessage);
    alert.showAndWait();
  }

  /**
   * <p>Initializes a new alert object and also sets its error message.</p>
   *
   * @param errorMessage given error message
   * @return new alert
   */
  private static Alert createAlert(String errorMessage) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setContentText(errorMessage);
    return alert;
  }

}
