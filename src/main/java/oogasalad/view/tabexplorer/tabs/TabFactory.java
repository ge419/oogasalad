package oogasalad.view.tabexplorer.tabs;

import oogasalad.view.tabexplorer.TabExplorer;
import oogasalad.view.tabexplorer.tabs.socialcenter.SocialCenterTab;

/**
 * The TabFactory interface provides methods for creating various tabs in a tab explorer view.
 *
 * @author Chika Dueke-Eze
 */
public interface TabFactory {

  /**
   * Creates a LoginTab instance for the specified TabExplorer.
   * @param tabExplorer the TabExplorer instance for the LoginTab
   * @return a LoginTab instance
   */
  LoginTab makeLoginTab(TabExplorer tabExplorer);
  /**
   * Creates a GameLauncherTab instance for the specified TabExplorer.
   * @param tabExplorer the TabExplorer instance for the GameLauncherTab
   * @returna GameLauncherTab instance
   */
  GameLauncherTab makeGameLauncherTab(TabExplorer tabExplorer);
  /**
   * Creates a SocialCenterTab instance for the specified TabExplorer.
   * @param tabExplorer the TabExplorer instance for the SocialCenterTab
   * @return a SocialCenterTab instance
   */
  SocialCenterTab makeSocialCenterTab(TabExplorer tabExplorer);
}



