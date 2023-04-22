package oogasalad.view.tabexplorer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * Navigation Bar for the TabExplorer.
 */
public class NavBar {
  private Button gameLauncherButton;
  private Button socialCenterButton;
  private Button userProfileButton;
  private Button userPreferencesButton;
  private Button loginButton;
  private Region spacer;
  private HBox navBarLayout;

  public NavBar() {
    initialize();
  }

  /**
   * Returns the HBox layout component of the navigation bar.
   * @return the HBox layout component of the navigation bar
   */
  public HBox getNavBarLayout() {
    return navBarLayout;
  }

  /**
   * Returns the game launcher button component of the navigation bar.
   * @return the game launcher button component of the navigation bar
   */
  public Button getGameLauncherButton() {
    return gameLauncherButton;
  }

  /**
   * Returns the social center button component of the navigation bar.
   * @return the social center button component of the navigation bar
   */
  public Button getSocialCenterButton() {
    return socialCenterButton;
  }

  /**
   * Returns the user profile button component of the navigation bar.
   * @return the user profile button component of the navigation bar
   */
  public Button getUserProfileButton() {
    return userProfileButton;
  }

  /**
   * Returns the user preference button component of the navigation bar.
   * @return the user preference button component of the navigation bar
   */
  public Button getUserPreferencesButton() {
    return userPreferencesButton;
  }

  /**
   * Returns the login (or logout) button component of the navigation bar.
   * @return the login (or logout) button component of the navigation bar
   */
  public Button getLoginButton() {
    return loginButton;
  }

  private void initialize(){
    //todo load from properties file
    gameLauncherButton = new Button("Launcher");
    socialCenterButton = new Button("Soc. Center");
    userProfileButton = new Button("Profile");
    userPreferencesButton = new Button("Preferences");
    loginButton = new Button("Login");
    spacer = new Region();

    HBox.setMargin(gameLauncherButton, new Insets(0, 0, 0, 10));
    HBox.setMargin(loginButton, new Insets(0, 10, 0, 0));

    navBarLayout = new HBox();
    navBarLayout.getChildren().addAll(gameLauncherButton, socialCenterButton,
        userProfileButton, userPreferencesButton, spacer, loginButton);
    HBox.setHgrow(spacer, Priority.ALWAYS);
    navBarLayout.setSpacing(10);
    navBarLayout.setAlignment(Pos.CENTER);
  }
}
