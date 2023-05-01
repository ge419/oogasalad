package oogasalad.view.tabexplorer.tabs.settings.options;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import oogasalad.model.accesscontrol.authentication.AuthenticationHandler;
import oogasalad.model.accesscontrol.dao.GameDao;
import oogasalad.model.accesscontrol.dao.UserDao;
import oogasalad.view.tabexplorer.TabExplorer;
import oogasalad.view.tabexplorer.tabs.settings.SettingsTab;
import oogasalad.view.tabexplorer.userpreferences.Theme;
import oogasalad.view.tabexplorer.userpreferences.UserPreferences;

/**
 * Class for appearance settings options.
 *
 * @author cgd19
 */
public class AppearanceSettings extends SettingsOptions {

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
  public AppearanceSettings(@Assisted SettingsTab settingsTab,
      @Assisted TabExplorer tabExplorer,
      AuthenticationHandler authHandler,
      UserDao userDao,
      GameDao gameDao, UserPreferences userPref, ResourceBundle languageResourceBundle) {
    super(settingsTab, tabExplorer, authHandler, userDao, gameDao, userPref,
        languageResourceBundle);
  }

  @Override
  public void render() {

    Label accountSettingLabel = new Label("Appearance");
    accountSettingLabel.setPadding(new Insets(20, 0, 20, 0));
    accountSettingLabel.setFont(Font.font("Courier New", 25));

    VBox tab = new VBox();

    HBox horizontalContainer = new HBox();
    Label changeThemeLabel = new Label("Change theme");
    changeThemeLabel.setPrefWidth(150);
    changeThemeLabel.setFont(Font.font("Verdana", 15));

    RadioButton lightMode = new RadioButton("Light Mode");
    RadioButton darkMode = new RadioButton("Dark Mode");
    RadioButton dukeMode = new RadioButton("DDMF Mode");

    lightMode.setOnAction(e -> tabExplorer.updateTheme(Theme.LIGHT.getThemeValue()));
    darkMode.setOnAction(e -> tabExplorer.updateTheme(Theme.DARK.getThemeValue()));
    dukeMode.setOnAction(e -> tabExplorer.updateTheme(Theme.DDMF.getThemeValue()));

    ToggleGroup radioGroup = new ToggleGroup();

    lightMode.setToggleGroup(radioGroup);
    darkMode.setToggleGroup(radioGroup);
    dukeMode.setToggleGroup(radioGroup);

    VBox checkBoxContainer = new VBox();

    checkBoxContainer.getChildren().addAll(lightMode, darkMode, dukeMode);
    checkBoxContainer.setSpacing(10);

    horizontalContainer.getChildren().addAll(changeThemeLabel, checkBoxContainer);
    horizontalContainer.setPadding(new Insets(20));

    tab.getChildren().addAll(accountSettingLabel, horizontalContainer);

    settingsTab.setCurrentSettingsOption(tab);
  }

  @Override
  public void onLanguageChange(String pathToBundle) {

  }
}
