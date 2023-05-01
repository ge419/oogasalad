package oogasalad.view.tabexplorer.tabs.settings.options;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.util.ResourceBundle;
import oogasalad.model.accesscontrol.authentication.AuthenticationHandler;
import oogasalad.model.accesscontrol.dao.GameDao;
import oogasalad.model.accesscontrol.dao.UserDao;
import oogasalad.view.tabexplorer.TabExplorer;
import oogasalad.view.tabexplorer.tabs.settings.SettingsTab;
import oogasalad.view.tabexplorer.userpreferences.UserPreferences;

public abstract class SettingsOptions {

  protected final TabExplorer tabExplorer;
  protected final AuthenticationHandler authHandler;
  protected final UserDao userDao;
  protected final GameDao gameDao;
  protected SettingsTab settingsTab;
  protected ResourceBundle languageResourceBundle;
  protected UserPreferences userPref;

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
  public SettingsOptions(@Assisted SettingsTab settingsTab, @Assisted TabExplorer tabExplorer,
      AuthenticationHandler authHandler, UserDao userDao,
      GameDao gameDao, UserPreferences userPref, ResourceBundle languageResourceBundle) {
    this.settingsTab = settingsTab;
    this.tabExplorer = tabExplorer;
    this.authHandler = authHandler;
    this.userDao = userDao;
    this.gameDao = gameDao;
    this.userPref = userPref;
    this.languageResourceBundle = languageResourceBundle;
    userPref.addObserver(this::onLanguageChange);
  }

  /**
   * Method that renders the settings options clicked on the scene (settings tab)
   */
  public abstract void render();

  /**
   * Method to be called on language change
   */
  public abstract void onLanguageChange(String pathToBundle);


}
