package oogasalad.view.tabexplorer.tabs.settings;

import com.google.inject.Inject;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import oogasalad.view.tabexplorer.userpreferences.UserPreferences;

/**
 * Class for settings navbar in {@link SettingsTab}.
 *
 * @author cgd19
 */
public class SettingsNavBar {

  private final UserPreferences userPref;
  private Hyperlink accountLink;
  private Hyperlink statsLink;
  private Hyperlink securityLink;
  private Hyperlink appearanceLink;
  private Hyperlink regionLink;
  private Region spacer1;
  private Separator separator1;
  private Hyperlink tellAFriendLink;
  private Separator separator2;
  private Region spacer2;
  private VBox container;
  private Hyperlink logoutLink;
  private Label versionNumber;
  private VBox settingsNavBarLayout;
  private ResourceBundle languageResourceBundle;

  @Inject
  public SettingsNavBar(UserPreferences userPref, ResourceBundle languageResourceBundle) {
    this.userPref = userPref;
    this.languageResourceBundle = languageResourceBundle;
    userPref.addObserver(this::onLanguageChange);
    init();
  }

  private void onLanguageChange(String pathToBundle) {
    languageResourceBundle = ResourceBundle.getBundle(pathToBundle);

    accountLink.setText(languageResourceBundle.getString("Account"));
    securityLink.setText(languageResourceBundle.getString("Security"));
    statsLink.setText(languageResourceBundle.getString("Stats"));
    appearanceLink.setText(languageResourceBundle.getString("Appearance"));
    regionLink.setText(languageResourceBundle.getString("LangRegion"));
    tellAFriendLink.setText(languageResourceBundle.getString("TellAFriend"));
    logoutLink.setText(languageResourceBundle.getString("Logout"));
  }

  public Hyperlink getAccountLink() {
    return accountLink;
  }

  public Hyperlink getStatsLink() {
    return statsLink;
  }

  public Hyperlink getSecurityLink() {
    return securityLink;
  }

  public Hyperlink getAppearanceLink() {
    return appearanceLink;
  }

  public Hyperlink getRegionLink() {
    return regionLink;
  }

  public Separator getSeparator1() {
    return separator1;
  }

  public Hyperlink getTellAFriendLink() {
    return tellAFriendLink;
  }

  public Separator getSeparator2() {
    return separator2;
  }

  public VBox getContainer() {
    return container;
  }

  public Hyperlink getLogoutLink() {
    return logoutLink;
  }

  public Label getVersionNumber() {
    return versionNumber;
  }

  public VBox getSettingsNavBarLayout() {
    return settingsNavBarLayout;
  }

  private void init() {
    accountLink = new Hyperlink(languageResourceBundle.getString("Account"));
    securityLink = new Hyperlink("Security");
    statsLink = new Hyperlink(languageResourceBundle.getString("Stats"));
    appearanceLink = new Hyperlink(languageResourceBundle.getString("Appearance"));
    regionLink = new Hyperlink(languageResourceBundle.getString("LangRegion"));
    separator1 = new Separator();
    separator1.setPadding(new Insets(5, 0, 5, 0));
    tellAFriendLink = new Hyperlink(languageResourceBundle.getString("TellAFriend"));
    separator2 = new Separator();
    separator2.setPadding(new Insets(5, 0, 5, 0));
    logoutLink = new Hyperlink(languageResourceBundle.getString("Logout"));
    versionNumber = new Label("v0.02");
    container = new VBox(logoutLink, versionNumber);
    spacer1 = new Region();
    VBox.setVgrow(spacer1, Priority.ALWAYS);
    spacer2 = new Region();
    VBox.setVgrow(spacer2, Priority.ALWAYS);
    settingsNavBarLayout = new VBox(accountLink, securityLink, appearanceLink,
        regionLink, separator1, tellAFriendLink, separator2, logoutLink, versionNumber, container);

    settingsNavBarLayout.setId("left-navbar");
  }
}

