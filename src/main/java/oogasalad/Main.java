package oogasalad;


import com.google.inject.Guice;
import com.google.inject.Injector;

import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.stage.Stage;
import oogasalad.controller.BuilderController;
import oogasalad.controller.GameController;
import oogasalad.view.tabexplorer.TabExplorer;
import oogasalad.view.tabexplorer.TabModule;
import oogasalad.view.tabexplorer.userpreferences.Languages;

/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Main extends Application {

  private static final String DEFAULT_LANGUAGE_PROPERTY = "tabexplorer.languages.en-US";

  public static void main(String[] args) {
    launch(args);
  }

  /**
   * A method to test (and a joke :).
   */
  public double getVersion() {
    return 0.001;
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    ResourceBundle resourceBundle = ResourceBundle.getBundle(DEFAULT_LANGUAGE_PROPERTY);
    Injector injector = Guice.createInjector(
        new TabModule(resourceBundle),
        binder -> binder.bind(Stage.class).toInstance(primaryStage)
    );
    TabExplorer launcher = injector.getInstance(TabExplorer.class);
    launcher.render();
  }
}
