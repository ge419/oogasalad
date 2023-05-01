package oogasalad.view.tabexplorer.tabs.settings.options;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
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

public class AccountSettings extends SettingsOptions {

  private TextField nameField;
  private TextField pronounsField;
  private TextField emailField;
  private TextField ageField;
  private Button saveButton;


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
      GameDao gameDao) {
    super(settingsTab, tabExplorer, authHandler, userDao, gameDao);
  }

  @Override
  public void render() {

    Label accountSettingLabel = new Label("Account");
    accountSettingLabel.setPadding(new Insets(20, 0, 20, 0));
    accountSettingLabel.setFont(Font.font("Courier New", 25));

    VBox tab = new VBox();

    HBox topContainer = new HBox();
    topContainer.setPadding(new Insets(20));
    Label publicAvatarLabel = new Label("Public Avatar");
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
    Label uploadAvatarLabel = new Label("Upload new avatar");
    Button uploadButton = new Button("Upload Button");
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
    Label personalSettingsLabel = new Label("Personal Settings");
    personalSettingsLabel.setPrefWidth(150);
    personalSettingsLabel.setFont(Font.font("Verdana", 15));

    VBox personalSettingsContainer = getPersonalSettingsComponent();
    bottomBox.getChildren().addAll(personalSettingsLabel, personalSettingsContainer);
    bottomBox.setPadding(new Insets(20));

    tab.getChildren().addAll(accountSettingLabel, topContainer, bottomBox);

    settingsTab.setCurrentSettingsOption(tab);

  }


  private VBox getPersonalSettingsComponent() {
    // retrieve current values of account settings from database
    String name = (String) userDao.getUserData(authHandler.getActiveUserID())
        .get(UserSchema.NAME.getFieldName());
    String pronouns = (String) userDao.getUserData(authHandler.getActiveUserID())
        .get(UserSchema.PRONOUNS.getFieldName());
    ;
    String email = (String) userDao.getUserData(authHandler.getActiveUserID())
        .get(UserSchema.EMAIL.getFieldName());
    ;
    long age = (long) userDao.getUserData(authHandler.getActiveUserID())
        .get(UserSchema.AGE.getFieldName());
    ;

// create labels and text fields to display and edit the values
    Label nameLabel = new Label("Name:");
    nameField = new TextField(name);
    VBox nameFieldContainer = createPersonalSettingsEntry(nameLabel, nameField);

    Label pronounsLabel = new Label("Pronouns:");
    pronounsField = new TextField(pronouns);
    VBox pronounsFieldContainer = createPersonalSettingsEntry(pronounsLabel, pronounsField);

    Label emailLabel = new Label("Email:");
    emailField = new TextField(email);
    VBox emailFieldContainer = createPersonalSettingsEntry(emailLabel, emailField);

    Label ageLabel = new Label("Age:");
    ageField = new TextField(Long.toString(age));
    VBox ageFieldContainer = createPersonalSettingsEntry(ageLabel, ageField);

    saveButton = new Button("Save");
    saveButton.setOnAction(e -> handleSaveEvent());

// add the controls to a VBox
    VBox personalSettingsContainer = new VBox(nameFieldContainer, pronounsFieldContainer,
        emailFieldContainer, ageFieldContainer, saveButton);
    personalSettingsContainer.setSpacing(10);
//    personalSettingsContainer.setPadding(new Insets(0,0,0,20));
    return personalSettingsContainer;
  }


  private VBox createPersonalSettingsEntry(Node label, Node inputMedium) {
    VBox container = new VBox(label, inputMedium);
    container.setSpacing(3);
    return container;
  }

//  private VBox createLabelContainers(Label ...labels){
//    VBox container = new VBox(labels);
//    container.setAlignment(Pos.CENTER_RIGHT);
//    container.setPadding(new Insets(0,10,0,0));
//    return container;
//  }

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
      userDao.setUserFullName(userID, name);
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