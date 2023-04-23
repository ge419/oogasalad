package oogasalad.view.builder;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import java.util.Map;
import java.util.ResourceBundle;
import oogasalad.controller.BuilderController;

/**
 * Basic Guice module for the {@link BuilderView}
 *
 * @author tmh85
 */
public class BuilderModule extends AbstractModule {

  private static final String BASE_RESOURCE_PACKAGE = "view.builder.";
  private static final String DEFAULT_STYLESHEET = "/view/builder/builderDefaultStyle.css";

  @Override
  protected void configure() {
    Map<String, String> annotatedNames = Map.of(
        "DefaultLanguage", "EnglishBuilderText",
        "TopBarMethods", "TopBar",
        "MainSideBarMethods", "SideBar1",
        "TileBarMethods", "TileMenu",
        "FileMenuMethods", "FileMenu",
        "AboutMenuMethods", "AppearanceMenu",
        "AppearanceMenuMethods", "AboutMenu",
        "SettingsMenuMethods", "SettingsMenu",
        "ToggleMenuMethods", "ToggleMenu"
    );
    bind(BuilderController.class).toInstance(new BuilderController());
    for (String resourceName : annotatedNames.keySet()) {
      bind(ResourceBundle.class).annotatedWith(Names.named(resourceName))
          .toInstance(
              ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + annotatedNames.get(resourceName)));
    }
    bind(String.class).toInstance(DEFAULT_STYLESHEET);
  }
}
