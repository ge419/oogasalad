package oogasalad.gameplay_frontend;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oogasalad.view.builder.BuilderView;
import oogasalad.view.gameplay.Board;
import oogasalad.view.gameplay.Gameview;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

public class GameviewTest extends ApplicationTest {

  @Override
  public void start(Stage primaryStage) {
    ObjectMapper objectMapper = new ObjectMapper();
    File jsonFile = new File("data/example/game_1.json");
    try {
      Gameview gameview = objectMapper.readValue(jsonFile, Gameview.class);
      gameview.renderGameview(primaryStage);
    }
    catch (IOException e){
      e.printStackTrace();
    }
  }

  @Test
  public void testRenderGameview() {
    //TODO: read in list of frontend components from JSON file instead of hard-coding in which
    // components should be there

//    assertTrue(lookup("#" + Board.class.getName()).query().isVisible());
    assertTrue(lookup("#Board").query().isVisible());
    assertTrue(lookup("#Die").query().isVisible());
    assertTrue(lookup("#Tiles").query().isVisible());
    assertTrue(lookup("#Pieces").query().isVisible());
  }

}
