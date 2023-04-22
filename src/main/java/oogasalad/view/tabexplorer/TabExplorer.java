package oogasalad.view.tabexplorer;

import com.google.inject.Inject;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oogasalad.model.accesscontrol.authentication.AuthenticationHandler;
import oogasalad.model.accesscontrol.database.DatabaseAccessor;
import oogasalad.view.tabexplorer.tabs.GameLauncherTab;
import oogasalad.view.tabexplorer.tabs.LoginTab;
import oogasalad.view.tabexplorer.tabs.Tab;
import oogasalad.view.tabexplorer.tabs.TabFactory;


/**
 * The TabExplorer class serves as the current entry point to our application. This class is
 * responsible for displaying the main user interface of the application, including the navigation
 * bar, and the various tabs that allow the user to interact with the app.
 *
 * @author Chika Dueke-Eze
 */
public class TabExplorer {

  private final TabFactory tabFactory;
  private final AuthenticationHandler authHandler;
  private final DatabaseAccessor db;
  private final Stage primaryStage;
  private BorderPane root;
  private Button gameLauncherButton;
  private Button socialCenterButton;
  private Button userProfileButton;
  private Button userPreferencesButton;
  private Button loginButton;
  private GameLauncherTab gameLauncherTab;
  private LoginTab loginTab;
  private final NavBar navBar;


  /** Creates a new TabExplorer instance with the specified dependencies.
   *  @param authHandler The AuthenticationHandler used to manage user authentication.
   *  @param db The DatabaseAccessor used to interact with the app's database.
   *  @param primaryStage The main Stage object used to display the app's UI.
   *  @param navBar The NavBar object used to display the app's navigation bar.
   *  @param tabFactory The TabFactory object used to create and manage the app's tabs.
   */
  @Inject
  public TabExplorer(AuthenticationHandler authHandler, DatabaseAccessor db, Stage primaryStage,
      NavBar navBar,
      TabFactory tabFactory) {
    this.authHandler = authHandler;
    this.db = db;
    this.primaryStage = primaryStage;
    this.navBar = navBar;
    this.tabFactory = tabFactory;
    gameLauncherButton = navBar.getGameLauncherButton();
    initTabs();
    initButtons();

  }

  /**
   * Renders the app's UI by setting up the root BorderPane and displaying the default tab.
   */
  public void render() {
    root = new BorderPane();
    root.setTop(navBar.getNavBarLayout());
    displayDefaultTab();
    primaryStage.setTitle("B-cubed");
    primaryStage.setScene(new Scene(root, 700, 700));
    primaryStage.show();
  }

  /**
   * Sets the currently displayed tab to the specified Node.
   * @param node The Node to set as the current tab.
   */
  public void setCurrentTab(Node node) {
    root.setCenter(node);
  }

  /**
   * Displays the default tab, which is either the GameLauncherTab or the LoginTab,
   * depending on the user's login status.
   */
  public void displayDefaultTab() {
    if (authHandler.getUserLogInStatus()) {
      gameLauncherTab.renderTabContent();
    } else {
      requestSignIn();
    }
  }

  /**
   * Handles a click on the login button by either logging the user out if they are already
   * logged in, or displaying the LoginTab if they are not logged in.
   */
  public void handleLoginBtnClick() {
    if (authHandler.getUserLogInStatus()) {
      authHandler.logout();
      render(); //displayDefaultTab
    } else {
      loginTab.renderTabContent();
    }
  }

  private void handleButtonClick(Tab tab) {
    if (authHandler.getUserLogInStatus()) {
      tab.renderTabContent();
    } else {
      requestSignIn();
    }
  }

  private void initTabs() {
    // gameLauncherTab = inject.getInstance(
    gameLauncherTab = tabFactory.makeGameLauncherTab(this);
    loginTab = tabFactory.makeLoginTab(this);
  }

  private void initButtons() {
    // get buttons from navbar
    gameLauncherButton = navBar.getGameLauncherButton();
    socialCenterButton = navBar.getSocialCenterButton();
    userProfileButton = navBar.getUserProfileButton();
    userPreferencesButton = navBar.getUserPreferencesButton();
    loginButton = navBar.getLoginButton();
    // set buttons action
    gameLauncherButton.setOnAction(e -> {
      handleButtonClick(gameLauncherTab);
    });
    loginButton.setOnAction(e -> {
      handleLoginBtnClick();
    });
  }

  private void requestSignIn() {
    Text loginText = new Text("Please ");
    Hyperlink loginLink = new Hyperlink("login");
    loginLink.setOnAction(event -> {
      // call the login method when the login link is clicked
      handleLoginBtnClick();
    });
    Text startText = new Text(" to start.");
    HBox pleaseLoginBox = new HBox();
    pleaseLoginBox.setAlignment(Pos.CENTER);
    pleaseLoginBox.getChildren().addAll(loginText, loginLink, startText);

    setCurrentTab(pleaseLoginBox);
  }
}
