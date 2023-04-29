package oogasalad;


import java.io.IOException;
import java.nio.file.Path;
import javafx.application.Application;
import javafx.stage.Stage;
import oogasalad.controller.GameController;

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

      GameController controller = new GameController(saveDir);
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
