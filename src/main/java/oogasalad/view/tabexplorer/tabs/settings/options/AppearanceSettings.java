package oogasalad.view.tabexplorer.tabs.settings.options;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
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
      GameDao gameDao) {
    super(settingsTab, tabExplorer, authHandler, userDao, gameDao);
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

    lightMode.setOnAction(e -> tabExplorer.updateTheme("light"));
    darkMode.setOnAction(e -> tabExplorer.updateTheme("dark"));

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
}
