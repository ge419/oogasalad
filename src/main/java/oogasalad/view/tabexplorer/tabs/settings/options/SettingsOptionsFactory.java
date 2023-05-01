package oogasalad.view.tabexplorer.tabs.settings.options;

import oogasalad.view.tabexplorer.TabExplorer;
import oogasalad.view.tabexplorer.tabs.settings.SettingsTab;

/**
 * Factory method to create new settings options factory
 *
 * @author cgd19
 */
public interface SettingsOptionsFactory {

  /**
   * Create new accounts settings page.
   * @param settingsTab
   * @param tabExplorer
   * @return account settings page
   */
  AccountSettings makeAccountSettingsPage(SettingsTab settingsTab, TabExplorer tabExplorer);

  /**
   * Create new security settings page.
   * @param settingsTab
   * @param tabExplorer
   * @return security settings page
   */
  SecuritySettings makeSecuritySettingsPage(SettingsTab settingsTab, TabExplorer tabExplorer);

  /**
   * Create new appearance settings page.
   * @param settingsTab
   * @param tabExplorer
   * @return appearance settings page
   */
  AppearanceSettings makeAppearanceSettingsPage(SettingsTab settingsTab, TabExplorer tabExplorer);

  /**
   * Create new regions settings page.
   * @param settingsTab
   * @param tabExplorer
   * @return regions settings page
   */
  RegionSettings makeRegionsSettings(SettingsTab settingsTab, TabExplorer tabExplorer);

}
