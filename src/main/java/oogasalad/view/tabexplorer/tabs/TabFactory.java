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

  LoginTab makeLoginTab(TabExplorer tabExplorer);

  GameLauncherTab makeGameLauncherTab(TabExplorer tabExplorer);

  SocialCenterTab makeSocialCenterTab(TabExplorer tabExplorer);

  GameDetailsTab makeGameDetailsTab(TabExplorer tabExplorer);

  SettingsTab makeSettingsTab(TabExplorer tabExplorer);
}



