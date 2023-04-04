package oogasalad;


import javafx.application.Application;
import javafx.stage.Stage;
import oogasalad.view.builder.BuilderView;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application {
    /**
     * A method to test (and a joke :).
     */
    public double getVersion () {
        return 0.001;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        new BuilderView();
    }
}
