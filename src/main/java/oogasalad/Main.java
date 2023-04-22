package oogasalad;


import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import oogasalad.controller.BuilderController;
import oogasalad.controller.GameController;
import oogasalad.controller.GameControllerModule;
import oogasalad.controller.GameHolder;
import oogasalad.view.builder.BuilderView;
import oogasalad.view.gameplay.Gameview;

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
        GameHolder gameHolderInstance = new GameHolder();
        Injector injector = Guice.createInjector(new GameControllerModule(gameHolderInstance));
        GameController controller = injector.getInstance(GameController.class);
        try {
            controller.setGame(primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        new BuilderView(new BuilderController());
    }
}
