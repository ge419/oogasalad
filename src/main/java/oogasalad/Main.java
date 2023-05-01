package oogasalad;


import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.stage.Stage;
import oogasalad.view.tabexplorer.TabExplorer;
import oogasalad.view.tabexplorer.TabModule;

public class Main extends Application {

  private static final String DEFAULT_LANGUAGE_PROPERTY = "tabexplorer.languages.en-US";

  public static void main(String[] args) {
    launch(args);
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
