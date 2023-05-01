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
import oogasalad.view.tabexplorer.userpreferences.Languages;

public class RegionSettings extends SettingsOptions {

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
  public RegionSettings(@Assisted SettingsTab settingsTab,
      @Assisted TabExplorer tabExplorer,
      AuthenticationHandler authHandler,
      UserDao userDao,
      GameDao gameDao) {
    super(settingsTab, tabExplorer, authHandler, userDao, gameDao);
  }

  @Override
  public void render() {
    Label accountSettingLabel = new Label("Language/Region");
    accountSettingLabel.setPadding(new Insets(20, 0, 20, 0));
    accountSettingLabel.setFont(Font.font("Courier New", 25));

    VBox tab = new VBox();

    HBox horizontalContainer = new HBox();
    Label changeThemeLabel = new Label("Change Language");
    changeThemeLabel.setPrefWidth(150);
    changeThemeLabel.setFont(Font.font("Verdana", 15));

    RadioButton english = new RadioButton("English 🇺🇸");
    RadioButton french = new RadioButton("French 🇫🇷");
    RadioButton korea = new RadioButton("Korea 🇰🇷");
    RadioButton spanish = new RadioButton("Spanish \uD83C\uDDEB\uD83C\uDDF7");

    english.setOnAction(e -> {
      tabExplorer.updateUserPrefLanguage(Languages.ENGLISH.getLocaleStr());
      render();
    });

    french.setOnAction(e -> {
      tabExplorer.updateUserPrefLanguage(Languages.FRENCH.getLocaleStr());
      render();
    });

    ToggleGroup radioGroup = new ToggleGroup();

    english.setToggleGroup(radioGroup);
    french.setToggleGroup(radioGroup);
    korea.setToggleGroup(radioGroup);
    spanish.setToggleGroup(radioGroup);

    VBox checkBoxContainer = new VBox();
    checkBoxContainer.getChildren().addAll(english, french, korea, spanish);
    checkBoxContainer.setSpacing(10);

    horizontalContainer.getChildren().addAll(changeThemeLabel, checkBoxContainer);
    horizontalContainer.setPadding(new Insets(20));

    tab.getChildren().addAll(accountSettingLabel, horizontalContainer);

    settingsTab.setCurrentSettingsOption(tab);
  }
}
