package oogasalad.view.tabexplorer.tabs.settings.options;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import oogasalad.model.accesscontrol.authentication.AuthenticationHandler;
import oogasalad.model.accesscontrol.dao.GameDao;
import oogasalad.model.accesscontrol.dao.UserDao;
import oogasalad.view.tabexplorer.TabExplorer;
import oogasalad.view.tabexplorer.tabs.settings.SettingsTab;

public abstract class SettingsOptions {

  protected final TabExplorer tabExplorer;
  protected final AuthenticationHandler authHandler;
  protected final UserDao userDao;
  protected final GameDao gameDao;
  protected SettingsTab settingsTab;

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
      GameDao gameDao) {
    this.settingsTab = settingsTab;
    this.tabExplorer = tabExplorer;
    this.authHandler = authHandler;
    this.userDao = userDao;
    this.gameDao = gameDao;
  }

  /**
   * Method that renders the settings options clicked on the scene (settings tab)
   */
  public abstract void render();


}
