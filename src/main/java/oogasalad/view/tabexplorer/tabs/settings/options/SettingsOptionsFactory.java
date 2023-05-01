package oogasalad.view.tabexplorer.tabs.settings.options;

import oogasalad.view.tabexplorer.TabExplorer;
import oogasalad.view.tabexplorer.tabs.settings.SettingsTab;

public interface SettingsOptionsFactory {

  AccountSettings makeAccountSettingsPage(SettingsTab settingsTab, TabExplorer tabExplorer);

  SecuritySettings makeSecuritySettingsPage(SettingsTab settingsTab, TabExplorer tabExplorer);

  AppearanceSettings makeAppearanceSettingsPage(SettingsTab settingsTab, TabExplorer tabExplorer);

  RegionSettings makeRegionsSettings(SettingsTab settingsTab, TabExplorer tabExplorer);

}
