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

public class Main extends Application {

  private static final String SERVICE_ACCOUNT_CRED_PATH = "/accesscontrol/service_account.json";
  public static final String TEST_SERVICE_ACCOUNT_CRED_PATH = "/accesscontrol/test_service_account.json";
  private static final String DEFAULT_LANGUAGE_PROPERTY = "tabexplorer.languages.en-US";
  private static final String DEFAULT_THEME_PROPERTY = "light";

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    ResourceBundle resourceBundle = ResourceBundle.getBundle(DEFAULT_LANGUAGE_PROPERTY);
    Injector injector = Guice.createInjector(
        new TabModule(resourceBundle, SERVICE_ACCOUNT_CRED_PATH),
        binder -> binder.bind(Stage.class).toInstance(primaryStage)
    );
    TabExplorer launcher = injector.getInstance(TabExplorer.class);
    launcher.render();
  }
}
