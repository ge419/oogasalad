package oogasalad;


import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.IOException;
import java.nio.file.Path;
import javafx.application.Application;
import javafx.stage.Stage;
import oogasalad.controller.GameController;
import oogasalad.controller.GameControllerModule;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.SaveManagerModule;

/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Main extends Application {

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
      Path saveDir = Path.of("data", "monopoly");
      GameHolder game = new GameHolder();
      // TODO: use ControllableModule and SaveManager instead of putting game holder in module
      Injector injector = Guice.createInjector(
          new SaveManagerModule(saveDir),
          new GameControllerModule()
      );

      GameController controller = injector.getInstance(GameController.class);
      try {
        controller.setGame(primaryStage);
      } catch (IOException e) {
        e.printStackTrace();
      }
//        new BuilderView(new BuilderController());
    }
    //new BuilderView(new BuilderController());
//    injector.getInstance(BuilderView.class);
  }
