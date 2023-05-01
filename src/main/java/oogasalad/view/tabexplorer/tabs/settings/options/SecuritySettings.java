package oogasalad.view.tabexplorer.tabs.settings.options;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import oogasalad.model.accesscontrol.authentication.AuthenticationHandler;
import oogasalad.model.accesscontrol.dao.GameDao;
import oogasalad.model.accesscontrol.dao.UserDao;
import oogasalad.model.accesscontrol.database.schema.UserSchema;
import oogasalad.view.tabexplorer.TabExplorer;
import oogasalad.view.tabexplorer.tabs.settings.SettingsTab;
import oogasalad.view.tabexplorer.userpreferences.UserPreferences;

/**
 * Class for security settings options.
 *
 * @author cgd19
 */
public class SecuritySettings extends SettingsOptions {

  private TextField oldPwdField;
  private TextField newPwdField;
  private TextField confirmNewPwdField;
  private Button updatePwd;

  /**
   * Class for sub-settings that aren't exactly "Tabs" and so shouldn't implement Tab, only update
   * in settings page. need
   *
   * @param settingsTab
   * @param tabExplorer
   * @param authHandler
   * @param userDao
   * @param gameDao
   */
  @Inject
  public SecuritySettings(@Assisted SettingsTab settingsTab,
      @Assisted TabExplorer tabExplorer,
      AuthenticationHandler authHandler,
      UserDao userDao,
      GameDao gameDao, UserPreferences userPref, ResourceBundle languageResourceBundle) {
    super(settingsTab, tabExplorer, authHandler, userDao, gameDao, userPref,
        languageResourceBundle);
  }

  @Override
  public void render() {

    Label accountSettingLabel = new Label("Security");
    accountSettingLabel.setPadding(new Insets(20, 0, 20, 0));
    accountSettingLabel.setFont(Font.font("Courier New", 25));

    VBox tab = new VBox();

    HBox changePwd = new HBox();
    Label personalSettingsLabel = new Label("Personal Settings");
    personalSettingsLabel.setPrefWidth(150);
    personalSettingsLabel.setFont(Font.font("Verdana", 15));

    VBox personalSettingsContainer = getPwdForm();
    changePwd.getChildren().addAll(personalSettingsLabel, personalSettingsContainer);
    changePwd.setPadding(new Insets(20));

    tab.getChildren().addAll(accountSettingLabel, changePwd);

    settingsTab.setCurrentSettingsOption(tab);

  }

  @Override
  public void onLanguageChange(String pathToBundle) {

  }

  private VBox getPwdForm() {

//     create labels and text fields to display and edit the values
    Label nameLabel = new Label("Old password");
    oldPwdField = new PasswordField();
    VBox oldPwdContainer = createGroup(nameLabel, oldPwdField);

    Label pronounsLabel = new Label("New password");
    newPwdField = new PasswordField();
    VBox pwdContainer = createGroup(pronounsLabel, newPwdField);

    Label emailLabel = new Label("Confirm new password");
    confirmNewPwdField = new PasswordField();
    VBox confirmPwdContainer = createGroup(emailLabel, confirmNewPwdField);

    updatePwd = new Button("Change Password");
    updatePwd.setOnAction(e -> handlePwdChange());

    // add the controls to a VBox
    VBox personalSettingsContainer = new VBox(oldPwdContainer, pwdContainer,
        confirmPwdContainer, updatePwd);
    personalSettingsContainer.setSpacing(15);
    return personalSettingsContainer;

  }

  private VBox createGroup(Node label, Node inputMedium) {
    VBox container = new VBox(label, inputMedium);
    container.setSpacing(3);
    return container;

  }

  private void handlePwdChange() {
    String userID = authHandler.getActiveUserID();
    String oldPwdFieldText = oldPwdField.getText();
    String newPwdFieldText = newPwdField.getText();
    String confirmNewPwdFieldText = confirmNewPwdField.getText();
    String currPwd = (String) userDao.getUserData(userID).get(UserSchema.PASSWORD.getFieldName());

    if (!currPwd.equals(oldPwdFieldText) || !newPwdFieldText.equals(confirmNewPwdFieldText)) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Invalid input(s)");
      alert.setHeaderText(null);
      alert.setContentText(
          "Check that:\n 1) Old password is correct \n 2) New passwords match\n");
      alert.showAndWait();
    } else {
      userDao.updatePassword(userID, newPwdFieldText);
      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle("Success!");
      alert.setHeaderText(null);
      alert.setContentText(
          "Your password has been successfully changed!");
      alert.showAndWait();
    }
    render();
  }

}
