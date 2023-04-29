package oogasalad.view.builder;

import java.util.ResourceBundle;
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
   * @param errorMessage desired error message to show
   * @param language currently used resourcebundle for language
   */
  public static void displayError(String errorMessage, ResourceBundle language){
    Alert alert = new Alert(AlertType.ERROR);
    alert.setHeaderText(language.getString("ErrorWindowTitle"));
    alert.setContentText(errorMessage);
    alert.showAndWait();
  }

}
