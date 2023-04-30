package oogasalad.view.tabexplorer;

import com.google.inject.Inject;
import java.io.IOException;
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
import oogasalad.controller.GameController;
import oogasalad.model.accesscontrol.authentication.AuthenticationHandler;
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

  private final TabFactory tabFactory;
  private AuthenticationHandler authHandler;
  private Stage primaryStage;
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
  private NavBar navBar;
  private UserDao userDao;
  private ResourceBundle languageResourceBundle;
  private String preferred_language;
  private String preferred_theme;
  //  public static String LANGUAGE_PROPERTIES_PATH = "tabexplorer.languages.";
  private static String STYLESHEET_PROPERTIES_PATH = "/tabexplorer/stylesheets/";
  private static final int STAGE_WIDTH = 700;
  private static final int STAGE_HEIGHT= 700;
  private UserPreferences userPref;
  private Scene scene;



  /**
   *
   * @param authHandler
   * @param primaryStage
   * @param navBar
   * @param tabFactory
   */

  @Inject
  public TabExplorer(AuthenticationHandler authHandler, Stage primaryStage, NavBar navBar,
      TabFactory tabFactory, UserDao userDao, UserPreferences userPref, ResourceBundle languageResourceBundle){
    this.authHandler = authHandler;
    this.primaryStage = primaryStage;
    this.navBar = navBar;
    this.tabFactory = tabFactory;
    this.userDao = userDao;
    this.languageResourceBundle = languageResourceBundle;
    this.userPref = userPref;
//    languageResourceBundle = userPref.getLanguageResourceBundle();
//    gameLauncherButton = navBar.getGameLauncherButton();
    userPref.addObserver(this::onLanguageChange);
    initTabs();
    initButtons();

  }

  public void render(){
    root = new BorderPane();
    root.setTop(navBar.getNavBarLayout());
    displayDefaultTab();
    primaryStage.setTitle(languageResourceBundle.getString("PrimaryStageTitle"));
    scene = new Scene(root, STAGE_WIDTH, STAGE_HEIGHT);
    String styleSheet = getClass().getResource(STYLESHEET_PROPERTIES_PATH+"light.css").toExternalForm();
    System.out.println(styleSheet);
    scene.getStylesheets().add(styleSheet);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public void setCurrentTab(Node node){
    root.setCenter(node);
  }

  /**
   * displayDefaultTab()...when logged in, GameLauncher, else LoginTab
   */
  public void displayDefaultTab(){
    if (authHandler.getUserLogInStatus()){
//      setLanguageResourceBundle();
//       userPref.setLanguageResourceBundle(authHandler.getActiveUserID());
      renderMenuButton();
//      refreshNavBar();
      initMenuButton();
      gameLauncherTab.renderTabContent();
    } else{
      requestSignIn();
    }
  }

  public void handleLoginBtnClick(){
    if (authHandler.getUserLogInStatus()){
      authHandler.logout();
      navBar.setLoginButton();
      displayDefaultTab(); //displayDefaultTab
    } else{
      loginTab.renderTabContent();
    }
  }


  public void refreshNavBar(){
//    renderMenuButton();
    String name = (String) userDao.getUserData(authHandler.getActiveUserID()).get(UserSchema.NAME.getFieldName());
    navBar.updateMenuButton(name, authHandler.getActiveUserName(), authHandler.getActiveUserID());
//    navBar.refresh();
    System.out.println("in refresh nav bar");
  }



  public void updateUserPrefLanguage(String preferred_language){
//    userPref.setUserPreferredLanguage(authHandler.getActiveUserID(), preferred_language);
    userPref.setPreferredLanguage(preferred_language);
//    refreshNavBar();
  }

  public void updateTheme(String newTheme){
    String styleSheet = getClass().getResource(STYLESHEET_PROPERTIES_PATH+newTheme+".css").toExternalForm();
    System.out.println(styleSheet);
    scene.getStylesheets().clear();
    scene.getStylesheets().add(styleSheet);
  }

  public void launchGame(String gameID){

    GameController gameController = new GameController(Paths.get(PathFinder.getGameDataPath(gameID)),
        Languages.ENGLISH.getLocaleStr());
    Stage gameStage = new Stage();
    try {
      gameController.setGame(gameStage);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }


  private void onLanguageChange(String pathToLanguageBundle){
    languageResourceBundle = ResourceBundle.getBundle(pathToLanguageBundle);
    primaryStage.setTitle(languageResourceBundle.getString("PrimaryStageTitle"));
  }



  private void renderMenuButton(){
    String name = (String) userDao.getUserData(authHandler.getActiveUserID()).get(UserSchema.NAME.getFieldName());
    navBar.setMenuButton(name, authHandler.getActiveUserName(), authHandler.getActiveUserID());
    menuButton = navBar.getMenuButton();
    setting = navBar.getSettingMenuItem();
    logout = navBar.getLogoutMenuItem();
    initMenuItems();
  }

  private void initMenuButton(){
    menuButton = navBar.getMenuButton();
    setting = navBar.getSettingMenuItem();
    logout = navBar.getLogoutMenuItem();
    initMenuItems();
  }

  private void initMenuItems(){
    setting.setOnAction(e->{handleButtonClick(settingsTab);});
    logout.setOnAction(e->handleLoginBtnClick());
  }


  private void handleButtonClick(Tab tab){
    if (authHandler.getUserLogInStatus()){
      refreshNavBar(); //latency issues sometiems DB call is slow from settings, doing this as a hack
      tab.renderTabContent();
    }else{
      requestSignIn();
    }
  }



  public void setLanguageResourceBundle(){
//    preferred_language = (String) userDao.getUserData(authHandler.getActiveUserID()).get(UserSchema.PREFERRED_LANGUAGE.getFieldName());
//    System.out.println("Preferred Language: "+preferred_language);
//    languageResourceBundle = ResourceBundle.getBundle(LANGUAGE_PROPERTIES_PATH +preferred_language);
  }

  private void initTabs(){
    gameLauncherTab = tabFactory.makeGameLauncherTab(this);
    loginTab = tabFactory.makeLoginTab(this);
    socialCenterTab = tabFactory.makeSocialCenterTab(this);
    settingsTab = tabFactory.makeSettingsTab(this);
  }

  private void initButtons(){
    // get buttons from navbar
    gameLauncherButton = navBar.getGameLauncherButton();
    socialCenterButton = navBar.getSocialCenterButton();
    loginButton = navBar.getLoginButton();


    gameLauncherButton.setOnAction(e->{handleButtonClick(gameLauncherTab);});
    loginButton.setOnAction(e->{
      handleLoginBtnClick();});
    socialCenterButton.setOnAction(e->handleButtonClick(socialCenterTab));
  }

  private void requestSignIn(){
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
