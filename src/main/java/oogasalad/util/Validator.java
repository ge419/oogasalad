package oogasalad.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Validator {

  public static Alert validateUserPersonalInfo(String name, String pronouns, String email, String age ){
    Alert alert = null;
    if (!isValidEmail(email) || !isInteger(age) || name.isBlank() || pronouns.isBlank()) {
      alert = new Alert(AlertType.ERROR);
      alert.setTitle("Invalid input(s)");
      alert.setHeaderText(null);
      alert.setContentText(
          "Ensure that:\n 1) No field is empty\n 2) You have a correct email address\n 3) You have"
              + " a correct age (integer)");
    }
    return alert;
  }

  private static boolean isValidEmail(String email) {
    String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    return email.matches(regex);
  }

  private static boolean isInteger(String s) {
    try {
      Integer.parseInt(s);
    } catch (NumberFormatException e) {
      return false;
    } catch (NullPointerException e) {
      return false;
    }
    // only got here if we didn't return false
    return true;
  }

}
