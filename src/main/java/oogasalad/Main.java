package oogasalad;


import javafx.application.Application;
import javafx.stage.Stage;
import oogasalad.view.builder.BuilderView;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import oogasalad.view.gameplay.Gameview;

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
    public void start(Stage primaryStage) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        File jsonFile = new File("data/example/game_1.json");
        try {
            Gameview gameview = objectMapper.readValue(jsonFile, Gameview.class);
            gameview.renderGameview(primaryStage);
        }
        catch (IOException e){
            e.printStackTrace();
        }
//        new BuilderView();
    }
}
