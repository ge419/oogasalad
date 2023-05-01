package oogasalad.view.tabexplorer;

import com.google.inject.Inject;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oogasalad.controller.BuilderController;
import oogasalad.controller.GameController;
import oogasalad.model.accesscontrol.authentication.AuthenticationHandler;
import oogasalad.model.accesscontrol.dao.GameDao;
import oogasalad.model.accesscontrol.dao.UserDao;
import oogasalad.model.accesscontrol.database.schema.UserSchema;
import oogasalad.util.PathFinder;
import oogasalad.view.tabexplorer.tabs.GameLauncherTab;
import oogasalad.view.tabexplorer.tabs.LoginTab;
import oogasalad.view.tabexplorer.tabs.Tab;
import oogasalad.view.tabexplorer.tabs.TabFactory;
import oogasalad.view.tabexplorer.tabs.settings.SettingsTab;
import oogasalad.view.tabexplorer.tabs.socialcenter.SocialCenterTab;
import oogasalad.view.tabexplorer.userpreferences.Languages;
import oogasalad.view.tabexplorer.userpreferences.UserPreferences;


/**
 * The TabExplorer class serves as the current entry point to our application. This class is
 * responsible for displaying the main user interface of the application, including the navigation
 * bar, and the various tabs that allow the user to interact with the app.
 *
 * @author Chika Dueke-Eze
 */
public class TabExplorer {

  private static final int STAGE_WIDTH = 700;
  private static final int STAGE_HEIGHT = 700;
  private static final String STYLESHEET_PROPERTIES_PATH = "/tabexplorer/stylesheets/";
  private final TabFactory tabFactory;
  private final AuthenticationHandler authHandler;
  private final Stage primaryStage;
  private final NavBar navBar;
  private final UserDao userDao;
  private final UserPreferences userPref;
  private final GameDao gameDao;
  private BorderPane root;
  private Button gameLauncherButton;
  private Button socialCenterButton;
  private Button userProfileButton;
  private Button userPreferencesButton;
  private MenuButton menuButton;
  private MenuItem setting;
  private MenuItem logout;
  private Button loginButton;
  private GameLauncherTab gameLauncherTab;
  private SocialCenterTab socialCenterTab;
  private SettingsTab settingsTab;
  private LoginTab loginTab;
  private ResourceBundle languageResourceBundle;
  private String preferred_language;
  private String preferred_theme;
  private Scene scene;

  /**
   * Constructor for the tab explorer.
   *
   * @param authHandler            object for auth handling
   * @param primaryStage           the javaFX primary stage
   * @param navBar                 navbar in view
   * @param tabFactory             factory for creating tabs
   * @param userDao                DAO for user
   * @param userPref               publisher for changes to things like languages
   * @param languageResourceBundle resource bundle for language
   * @param gameDao                DAO for game
   */
  @Inject
  public TabExplorer(AuthenticationHandler authHandler, Stage primaryStage, NavBar navBar,
      TabFactory tabFactory, UserDao userDao, UserPreferences userPref,
      ResourceBundle languageResourceBundle, GameDao gameDao) {
    this.authHandler = authHandler;
    this.primaryStage = primaryStage;
    this.navBar = navBar;
    this.tabFactory = tabFactory;
    this.userDao = userDao;
    this.languageResourceBundle = languageResourceBundle;
    this.userPref = userPref;
    this.gameDao = gameDao;
    userPref.addObserver(this::onLanguageChange);
    initTabs();
    initButtons();
  }

  /**
   * Renders the tab explorer.
   */

  public void render() {
    root = new BorderPane();
    root.setTop(navBar.getNavBarLayout());
    displayDefaultTab();
    primaryStage.setTitle(languageResourceBundle.getString("PrimaryStageTitle"));
    scene = new Scene(root, STAGE_WIDTH, STAGE_HEIGHT);
    String styleSheet = getClass().getResource(STYLESHEET_PROPERTIES_PATH + "light.css")
        .toExternalForm();
    scene.getStylesheets().add(styleSheet);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /**
   * Called by tabs to set current focus of tab to a node.
   *
   * @param node
   */
  public void setCurrentTab(Node node) {
    root.setCenter(node);
  }

  /**
   * Sets the display default tab. If user is logged in, that's game launcher; else, login tab.
   */
  public void displayDefaultTab() {
    if (authHandler.getUserLogInStatus()) {
      renderMenuButton();
      gameLauncherTab.renderTabContent();
    } else {
      requestSignIn();
    }
  }

  /**
   * Method to handle login/logout button click.
   */
  public void handleLoginBtnClick() {
    if (authHandler.getUserLogInStatus()) {
      authHandler.logout();
      navBar.setLoginButton();
      displayDefaultTab();
    } else {
      loginTab.renderTabContent();
    }
  }

  /**
   * Method to refresh contents of navbar after change to things like language.
   */
  public void refreshNavBar() {
    String name = (String) userDao.getUserData(authHandler.getActiveUserID())
        .get(UserSchema.NAME.getFieldName());
    navBar.updateMenuButton(name, authHandler.getActiveUserName(), authHandler.getActiveUserID());
  }

  /**
   * Method to update user preferred language.
   *
   * @param preferred_language
   */
  public void updateUserPrefLanguage(String preferred_language) {
    userPref.setPreferredLanguage(preferred_language);
  }

  /**
   * Method to update theme
   *
   * @param newTheme
   */
  public void updateTheme(String newTheme) {
    String styleSheet = getClass().getResource(STYLESHEET_PROPERTIES_PATH + newTheme + ".css")
        .toExternalForm();
    scene.getStylesheets().clear();
    scene.getStylesheets().add(styleSheet);
  }

  /**
   * Method to launch game (gameplay) in the game launcher
   *
   * @param gameID
   */

  public void launchGame(String gameID) {
    GameController gameController = new GameController(
        Languages.ENGLISH.getLocaleStr(), Paths.get(PathFinder.getGameDataPath(gameID)));
    System.out.println("paths: " + Paths.get(PathFinder.getGameDataPath(gameID)));
    Stage gameStage = new Stage();
    gameController.setGame(gameStage);
  }

  /**
   * Method to launch game builder in the game launcher.
   *
   * @param gameID
   */
  public void launchGameBuilder(String gameID) {
    BuilderController builderController = new BuilderController("en-US", gameID, gameDao);

  }

  private void onLanguageChange(String pathToLanguageBundle) {
    languageResourceBundle = ResourceBundle.getBundle(pathToLanguageBundle);
    primaryStage.setTitle(languageResourceBundle.getString("PrimaryStageTitle"));
  }


  private void renderMenuButton() {
    String name = (String) userDao.getUserData(authHandler.getActiveUserID())
        .get(UserSchema.NAME.getFieldName());
    navBar.setMenuButton(name, authHandler.getActiveUserName(), authHandler.getActiveUserID());
    navBar.onLanguageChange(userPref.getPreferredLanguagePath());

    initMenuButton();
  }

  private void initMenuButton() {
    menuButton = navBar.getMenuButton();
    setting = navBar.getSettingMenuItem();
    logout = navBar.getLogoutMenuItem();
    initMenuItems();
  }

  private void initMenuItems() {
    setting.setOnAction(e -> {
      handleButtonClick(settingsTab);
    });
    logout.setOnAction(e -> handleLoginBtnClick());
  }


  private void handleButtonClick(Tab tab) {
    if (authHandler.getUserLogInStatus()) {
      refreshNavBar(); //latency issues sometiems DB call is slow from settings, doing this as a hack
      tab.renderTabContent();
    } else {
      requestSignIn();
    }
  }

  private void initTabs() {
    gameLauncherTab = tabFactory.makeGameLauncherTab(this);
    loginTab = tabFactory.makeLoginTab(this);
    socialCenterTab = tabFactory.makeSocialCenterTab(this);
    settingsTab = tabFactory.makeSettingsTab(this);
  }

  private void initButtons() {
    // get buttons from navbar
    gameLauncherButton = navBar.getGameLauncherButton();
    socialCenterButton = navBar.getSocialCenterButton();
    loginButton = navBar.getLoginButton();

    gameLauncherButton.setOnAction(e -> {
      handleButtonClick(gameLauncherTab);
    });
    loginButton.setOnAction(e -> {
      handleLoginBtnClick();
    });
    socialCenterButton.setOnAction(e -> handleButtonClick(socialCenterTab));
  }

  private void requestSignIn() {
    Text loginText = new Text(languageResourceBundle.getString("Please"));
    Hyperlink loginLink = new Hyperlink(languageResourceBundle.getString("Login"));
    loginLink.setOnAction(event -> {
      // call the login method when the login link is clicked
      handleLoginBtnClick();
    });
    Text startText = new Text(languageResourceBundle.getString("ToStart"));
    HBox pleaseLoginBox = new HBox();
    pleaseLoginBox.setAlignment(Pos.CENTER);
    pleaseLoginBox.getChildren().addAll(loginText, loginLink, startText);

    setCurrentTab(pleaseLoginBox);
  }
}
