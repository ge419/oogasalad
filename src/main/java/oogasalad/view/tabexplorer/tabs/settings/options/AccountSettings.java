package oogasalad.view.tabexplorer.tabs.settings.options;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import oogasalad.model.accesscontrol.authentication.AuthenticationHandler;
import oogasalad.model.accesscontrol.dao.GameDao;
import oogasalad.model.accesscontrol.dao.UserDao;
import oogasalad.model.accesscontrol.database.schema.UserSchema;
import oogasalad.util.FileUploader;
import oogasalad.util.PathFinder;
import oogasalad.util.Validator;
import oogasalad.view.tabexplorer.TabExplorer;
import oogasalad.view.tabexplorer.tabs.settings.SettingsTab;
import oogasalad.view.tabexplorer.userpreferences.UserPreferences;

/**
 * Class for accounts settings options.
 *
 * @author cgd19
 */
public class AccountSettings extends SettingsOptions {

  private TextField nameField;
  private TextField pronounsField;
  private TextField emailField;
  private TextField ageField;
  private Button saveButton;
  private Label uploadAvatarLabel;
  private Label publicAvatarLabel;
  private Label accountSettingLabel;
  private Label personalSettingsLabel;
  private Button uploadButton;
  private Label nameLabel;
  private Label pronounsLabel;
  private Label emailLabel;
  private Label ageLabel;


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
  public AccountSettings(@Assisted SettingsTab settingsTab,
      @Assisted TabExplorer tabExplorer,
      AuthenticationHandler authHandler,
      UserDao userDao,
      GameDao gameDao, UserPreferences userPref, ResourceBundle languageResourceBundle) {
    super(settingsTab, tabExplorer, authHandler, userDao, gameDao, userPref,
        languageResourceBundle);
  }

  @Override
  public void render() {

    accountSettingLabel = new Label(languageResourceBundle.getString("Account"));
    accountSettingLabel.setPadding(new Insets(20, 0, 20, 0));
    accountSettingLabel.setFont(Font.font("Courier New", 25));

    VBox tab = new VBox();

    HBox topContainer = new HBox();
    topContainer.setPadding(new Insets(20));
    publicAvatarLabel = new Label(languageResourceBundle.getString("PublicAvatar"));
    publicAvatarLabel.setFont(Font.font("Verdana", 15));
    publicAvatarLabel.setPrefWidth(150);

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    Region spacer2 = new Region();
    HBox.setHgrow(spacer2, Priority.ALWAYS);

    HBox container = new HBox();
    ImageView imageView = new ImageView(
        new Image(PathFinder.getUserAvatar(authHandler.getActiveUserID())));
    imageView.setFitWidth(60);
    imageView.setFitHeight(60);

    VBox uploadButtonContainer = new VBox();
    uploadAvatarLabel = new Label(languageResourceBundle.getString("UploadNewAvatar"));
    uploadButton = new Button(languageResourceBundle.getString("UploadButton"));
    uploadButton.setOnAction(e -> {
      FileUploader.uploadUserAvatar(authHandler.getActiveUserID());
      tabExplorer.refreshNavBar();
      render();
    });
    uploadButtonContainer.getChildren().addAll(uploadAvatarLabel, uploadButton);

    container.setSpacing(15);
    container.getChildren().addAll(imageView, uploadButtonContainer);

    topContainer.setPrefHeight(60);
    topContainer.getChildren().addAll(publicAvatarLabel, container);

    HBox bottomBox = new HBox();
    personalSettingsLabel = new Label(languageResourceBundle.getString("PersonalSettings"));
    personalSettingsLabel.setPrefWidth(150);
    personalSettingsLabel.setFont(Font.font("Verdana", 15));

    VBox personalSettingsContainer = getPersonalSettingsComponent();
    bottomBox.getChildren().addAll(personalSettingsLabel, personalSettingsContainer);
    bottomBox.setPadding(new Insets(20));

    tab.getChildren().addAll(accountSettingLabel, topContainer, bottomBox);

    settingsTab.setCurrentSettingsOption(tab);
  }

  @Override
  public void onLanguageChange(String pathToBundle) {
    languageResourceBundle = ResourceBundle.getBundle(pathToBundle);

    saveButton.setText(languageResourceBundle.getString("Save") + ":");
    uploadAvatarLabel.setText(languageResourceBundle.getString("UploadNewAvatar"));
    publicAvatarLabel.setText(languageResourceBundle.getString("PublicAvatar"));
    accountSettingLabel.setText(languageResourceBundle.getString("Account"));
    personalSettingsLabel.setText(languageResourceBundle.getString("PersonalSettings"));
    uploadButton.setText(languageResourceBundle.getString("UploadButton"));
    nameLabel.setText(languageResourceBundle.getString("Name"));
    pronounsLabel.setText(languageResourceBundle.getString("Pronouns"));
    emailLabel.setText(languageResourceBundle.getString("Email"));
    ageLabel.setText(languageResourceBundle.getString("Age"));
  }


  private VBox getPersonalSettingsComponent() {
    // retrieve current values of account settings from database
    String name = (String) userDao.getUserData(authHandler.getActiveUserID())
        .get(UserSchema.NAME.getFieldName());
    String pronouns = (String) userDao.getUserData(authHandler.getActiveUserID())
        .get(UserSchema.PRONOUNS.getFieldName());
    String email = (String) userDao.getUserData(authHandler.getActiveUserID())
        .get(UserSchema.EMAIL.getFieldName());
    long age = (long) userDao.getUserData(authHandler.getActiveUserID())
        .get(UserSchema.AGE.getFieldName());

    // create labels and text fields to display and edit the values
    nameLabel = new Label(languageResourceBundle.getString("Name") + ":");
    nameField = new TextField(name);
    VBox nameFieldContainer = createPersonalSettingsEntry(nameLabel, nameField);

    pronounsLabel = new Label(languageResourceBundle.getString("Pronouns") + ":");
    pronounsField = new TextField(pronouns);
    VBox pronounsFieldContainer = createPersonalSettingsEntry(pronounsLabel, pronounsField);

    emailLabel = new Label(languageResourceBundle.getString("Email") + ":");
    emailField = new TextField(email);
    VBox emailFieldContainer = createPersonalSettingsEntry(emailLabel, emailField);

    ageLabel = new Label(languageResourceBundle.getString("Age") + ":");
    ageField = new TextField(Long.toString(age));
    VBox ageFieldContainer = createPersonalSettingsEntry(ageLabel, ageField);

    saveButton = new Button(languageResourceBundle.getString("Save") + ":");
    saveButton.setOnAction(e -> handleSaveEvent());

    VBox personalSettingsContainer = new VBox(nameFieldContainer, pronounsFieldContainer,
        emailFieldContainer, ageFieldContainer, saveButton);
    personalSettingsContainer.setSpacing(10);
    return personalSettingsContainer;
  }


  private VBox createPersonalSettingsEntry(Node label, Node inputMedium) {
    VBox container = new VBox(label, inputMedium);
    container.setSpacing(3);
    return container;
  }

  private void handleSaveEvent() {
    String userID = authHandler.getActiveUserID();
    String name = nameField.getText();
    String pronouns = pronounsField.getText();
    String email = emailField.getText();
    String age = ageField.getText();

    Alert alert = Validator.validateUserPersonalInfo(name, pronouns, email, age);

    // run basic validations
    if (alert != null) {
      alert.showAndWait();
    } else {
      // validations pass - update DB
      userDao.updateUserFullName(userID, name);
      userDao.updateUserPronouns(userID, pronouns);
      userDao.updateEmailAddress(userID, email);
      userDao.updateAge(userID, Integer.parseInt(age));

      // DB latency hack to force refresh
      tabExplorer.refreshNavBar();
      tabExplorer.refreshNavBar();

      render();
    }
  }
}