package oogasalad.view.tabexplorer;

import com.google.inject.Inject;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import oogasalad.model.accesscontrol.authentication.AuthenticationHandler;
import oogasalad.util.PathFinder;
import oogasalad.view.tabexplorer.userpreferences.UserPreferences;

/**
 * Navigation Bar for the TabExplorer.
 */
public class NavBar {

  private Button gameLauncherButton;
  private Button socialCenterButton;
  //  private Button userProfileButton;
//  private Button userPreferencesButton;
  private Button loginButton;
  private Region spacer;
  private Region spacer2;
  private HBox navBarLayout;
  private Label bCubedLogo;
  private MenuItem userInfo;
  private MenuItem settings;
  private MenuItem logout;
  private MenuButton menuButton;
  private AuthenticationHandler authHandler;
  private ResourceBundle languageResourceBundle;
  private final UserPreferences userPref;
  private Label userFullNameLabel;
  private Label userNameLabel;
  private ImageView imageView;

  @Inject
  public NavBar(UserPreferences userPref, ResourceBundle languageResourceBundle) {
//    this.authHandler = authHandler;
    this.userPref = userPref;
    this.languageResourceBundle = languageResourceBundle;
    userPref.addObserver(this::onLanguageChange);
    initialize();

  }

  public void onLanguageChange(String pathToBundle) {
    languageResourceBundle = ResourceBundle.getBundle(pathToBundle);
    updateButtonText();
  }

  public HBox getNavBarLayout() {
    return navBarLayout;
  }

  public Button getGameLauncherButton() {
    return gameLauncherButton;
  }

  public Button getSocialCenterButton() {
    return socialCenterButton;
  }

//  }

  public Button getLoginButton() {
    return loginButton;
  }

  public MenuButton getMenuButton() {
    return menuButton;
  }

  public void updateMenuButton(String userFullName, String userName, String userID) {
    userFullNameLabel.setText(userFullName);
    userNameLabel.setText(userName);
    imageView.setImage(new Image(PathFinder.getUserAvatar(userID)));
  }

  public void setMenuButton(String userFullName, String userName,
      String userID) { // todo negative test try setting menu button when authhandler is not active, actually prob not
    userFullNameLabel = new Label(userFullName);
    userNameLabel = new Label("@" + userName);
    VBox userInfoBox = new VBox(userFullNameLabel, userNameLabel);
    userInfo = new CustomMenuItem(userInfoBox);
    userInfo.setDisable(true);

    settings = new MenuItem(languageResourceBundle.getString("Settings"));
    logout = new MenuItem(languageResourceBundle.getString("Logout"));

    SeparatorMenuItem separator = new SeparatorMenuItem();

    imageView = new ImageView(new Image(PathFinder.getUserAvatar(userID)));
    imageView.setFitWidth(30);
    imageView.setFitHeight(30);

    menuButton = new MenuButton(null, imageView, userInfo, separator, settings, logout);

    navBarLayout.getChildren().set(navBarLayout.getChildren().size() - 1, menuButton);
  }

  public void setLoginButton() {
    navBarLayout.getChildren().set(navBarLayout.getChildren().size() - 1, loginButton);
  }

  public MenuItem getSettingMenuItem() {
    return menuButton.getItems().get(2);
  }

  public MenuItem getLogoutMenuItem() {
    return menuButton.getItems().get(3);
  }


  private void updateButtonText() {
    //todo load from properties file
    gameLauncherButton.setText(languageResourceBundle.getString("LauncherBtn"));
    socialCenterButton.setText(languageResourceBundle.getString("SocialCenterBtn"));
    settings.setText(languageResourceBundle.getString("Settings"));
    logout.setText(languageResourceBundle.getString("Logout"));
  }

  private void initialize() {

//    initButtons();

    gameLauncherButton = new Button(languageResourceBundle.getString("LauncherBtn"));
    gameLauncherButton.setOnMouseEntered(e -> gameLauncherButton.setCursor(Cursor.HAND));
    socialCenterButton = new Button(languageResourceBundle.getString("SocialCenterBtn"));
    socialCenterButton.setOnMouseEntered(e -> socialCenterButton.setCursor(Cursor.HAND));

    bCubedLogo = new Label("b^3cubed");
    bCubedLogo.setTextFill(Color.PURPLE);
    loginButton = new Button("Login");

    spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    spacer2 = new Region();
    HBox.setHgrow(spacer2, Priority.ALWAYS);

    HBox.setMargin(gameLauncherButton, new Insets(0, 0, 0, 10));
    HBox.setMargin(loginButton, new Insets(0, 10, 0, 0));

    navBarLayout = new HBox();
    navBarLayout.getChildren().addAll(bCubedLogo, spacer2, gameLauncherButton, socialCenterButton,
        spacer, loginButton);

    navBarLayout.setSpacing(10);
    navBarLayout.setAlignment(Pos.CENTER);
    navBarLayout.setStyle("-fx-background-color: #dbdbdb;");
  }
}
