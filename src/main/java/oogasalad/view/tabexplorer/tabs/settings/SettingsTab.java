package oogasalad.view.tabexplorer.tabs.settings;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import oogasalad.model.accesscontrol.authentication.AuthenticationHandler;
import oogasalad.model.accesscontrol.dao.GameDao;
import oogasalad.model.accesscontrol.dao.UserDao;
import oogasalad.view.tabexplorer.TabExplorer;
import oogasalad.view.tabexplorer.tabs.Tab;
import oogasalad.view.tabexplorer.tabs.settings.options.AccountSettings;
import oogasalad.view.tabexplorer.tabs.settings.options.AppearanceSettings;
import oogasalad.view.tabexplorer.tabs.settings.options.RegionSettings;
import oogasalad.view.tabexplorer.tabs.settings.options.SecuritySettings;
import oogasalad.view.tabexplorer.tabs.settings.options.SettingsOptions;
import oogasalad.view.tabexplorer.tabs.settings.options.SettingsOptionsFactory;

public class SettingsTab implements Tab {

  private final TabExplorer tabExplorer;
  private final AuthenticationHandler authHandler;
  private final UserDao userDao;
  private final GameDao gameDao;
  private final SettingsOptionsFactory settingsOptionsFactory;
  private final SettingsNavBar settingsNavBar;
  private AccountSettings accountSettings;
  private SecuritySettings securitySettings;
  private AppearanceSettings appearanceSettings;
  private RegionSettings regionSettings;
  private Hyperlink accountLink;
  private Hyperlink securityLink;
  private Hyperlink appearanceLink;
  private Hyperlink regionLink;
  private Hyperlink tellAFriendLink;
  private Hyperlink logoutLink;
  private BorderPane root;


  @Inject
  public SettingsTab(
      @Assisted TabExplorer tabExplorer, AuthenticationHandler authHandler,
      UserDao userDao, GameDao gameDao, SettingsOptionsFactory settingsOptionsFactory, SettingsNavBar settingsNavBar) {
    this.tabExplorer = tabExplorer;
    this.authHandler = authHandler;
    this.userDao = userDao;
    this.gameDao = gameDao;
    this.settingsOptionsFactory = settingsOptionsFactory;
    this.settingsNavBar = settingsNavBar;
    initOptions();
    initLinks();
  }

  @Override
  public void renderTabContent() {

    root = new BorderPane();
    VBox vbox = new VBox();
    vbox.setPrefWidth(200);
//    vbox.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    vbox.getChildren().add(new Label("Online Users"));


    VBox container = new VBox();

    container.setPadding(new Insets(0,50,0,50));

    root.setLeft(settingsNavBar.getSettingsNavBarLayout());
    accountSettings.render();
    //displayDefaultOptionsPage

    tabExplorer.setCurrentTab(root);
  }

  public void setCurrentSettingsOption(Node node){
    root.setCenter(node);
  }

  public void refreshNavBar(){

  }

  private void initOptions(){
    accountSettings = settingsOptionsFactory.makeAccountSettingsPage(this, tabExplorer);
    securitySettings = settingsOptionsFactory.makeSecuritySettingsPage(this,tabExplorer);
    appearanceSettings = settingsOptionsFactory.makeAppearanceSettingsPage(this,tabExplorer);
    regionSettings = settingsOptionsFactory.makeRegionsSettings(this,tabExplorer);
  }

  private void initLinks(){
    accountLink = settingsNavBar.getAccountLink();
    securityLink = settingsNavBar.getSecurityLink();
    appearanceLink = settingsNavBar.getAppearanceLink();
    regionLink = settingsNavBar.getRegionLink();
    tellAFriendLink = settingsNavBar.getTellAFriendLink();
    logoutLink = settingsNavBar.getLogoutLink();
    setLinkAction();
  }

  private void setLinkAction(){
    accountLink.setOnAction(e->handleLinkClick(accountSettings));
    securityLink.setOnAction(e->handleLinkClick(securitySettings));
    appearanceLink.setOnAction(e->handleLinkClick(appearanceSettings));
    regionLink.setOnAction(e->handleLinkClick(regionSettings));
    tellAFriendLink.setOnAction(e->{
      String url = "https://twitter.com/intent/tweet?text=%E2%9C%85%20Yo!%20Checkout%20my%20awesome%20monopoly%20game%20builder%20%F0%9F%9A%80%20www.bcubed.app";
      try {
        Desktop.getDesktop().browse(new URI(url));
      } catch (IOException | URISyntaxException ex) {
        ex.printStackTrace();
      }
    });
    logoutLink.setOnAction(e->tabExplorer.handleLoginBtnClick());
//    statsLink.setOnAction(e->handleLinkClick(st));
  }


  private void handleLinkClick(SettingsOptions settingsOptions){
    settingsOptions.render();
  }



}
