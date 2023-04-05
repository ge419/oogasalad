package oogasalad;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import oogasalad.gameplay_frontend.Board;
import oogasalad.gameplay_frontend.Gameview;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main {
    /**
     * Start of the program.
     */
    public static void main (String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        File jsonFile = new File("data/example/game_1.json");
        try {
            Gameview gameview = objectMapper.readValue(jsonFile, Gameview.class);
            gameview.renderGameview();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
