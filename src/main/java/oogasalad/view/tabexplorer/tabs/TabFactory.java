package oogasalad.view.tabexplorer.tabs;

import oogasalad.view.tabexplorer.TabExplorer;
import oogasalad.view.tabexplorer.tabs.settings.SettingsTab;
import oogasalad.view.tabexplorer.tabs.socialcenter.GameDetailsTab;
import oogasalad.view.tabexplorer.tabs.socialcenter.SocialCenterTab;

/**
 * The TabFactory interface provides methods for creating various tabs in a tab explorer view.
 *
 * @author Chika Dueke-Eze
 */
public interface TabFactory {

  /**
   * Method that creates a login tab
   *
   * @param tabExplorer
   * @return a login tab instance
   */
  LoginTab makeLoginTab(TabExplorer tabExplorer);

  /**
   * Method that creates a game launcher tab
   *
   * @param tabExplorer
   * @return a game launcher tab instance
   */
  GameLauncherTab makeGameLauncherTab(TabExplorer tabExplorer);

  /**
   * Method that creates a social center tab
   *
   * @param tabExplorer
   * @return a social center tab instance
   */
  SocialCenterTab makeSocialCenterTab(TabExplorer tabExplorer);

  /**
   * Method that creates a game details tab
   *
   * @param tabExplorer
   * @return a game details tab instance
   */
  GameDetailsTab makeGameDetailsTab(TabExplorer tabExplorer);

  /**
   * Method that creates a settings tab
   *
   * @param tabExplorer
   * @return a settings tab instance
   */
  SettingsTab makeSettingsTab(TabExplorer tabExplorer);
}



