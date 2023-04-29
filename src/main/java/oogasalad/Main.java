package oogasalad;


import com.fasterxml.jackson.module.guice.ObjectMapperModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.IOException;
import java.nio.file.Path;
import javafx.application.Application;
import javafx.stage.Stage;
import oogasalad.controller.BuilderController;
import oogasalad.controller.BuilderControllerModule;
import oogasalad.controller.GameController;
import oogasalad.controller.GameControllerModule;
import oogasalad.model.constructable.ConstructableModule;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.engine.EngineModule;
import oogasalad.util.SaveManager;
import oogasalad.view.builder.BuilderView;

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
        // TODO: use ControllableModule and SaveManager instead of putting game holder in module
        Injector saveInjector = Guice.createInjector(
            new ObjectMapperModule(),
            new EngineModule(),
            new ConstructableModule(saveDir)
        );
        GameHolder gameHolder = saveInjector.getInstance(SaveManager.class).loadGame();
        
        Injector injector = Guice.createInjector(new GameControllerModule(gameHolder),
            new BuilderControllerModule("English"));
        GameController controller = injector.getInstance(GameController.class);
        try {
            controller.setGame(primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
      injector.getInstance(BuilderController.class);
    }
  }
