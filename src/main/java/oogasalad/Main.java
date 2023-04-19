package oogasalad;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.guice.ObjectMapperModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import oogasalad.controller.BuilderController;
import oogasalad.controller.GameController;
import oogasalad.model.attribute.SchemaDatabase;
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
        GameController controller = new GameController();
        try {
            Gameview gameview = controller.loadGV();
            gameview.renderGameview(primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        new BuilderView(new BuilderController());
    }
}
