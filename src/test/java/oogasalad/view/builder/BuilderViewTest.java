package oogasalad.view.builder;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import oogasalad.controller.BuilderController;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testfx.service.query.EmptyNodeQueryException;
import org.testfx.service.query.PointQuery;
import util.DukeApplicationTest;

public class BuilderViewTest extends DukeApplicationTest {

  @Mock
  FileChooser fileChooser = Mockito.mock(FileChooser.class);
  private BuilderView myBuilder;
  private BuilderController myBuilderController;
  private static final String TEST_LANGUAGE = "en-US";
  private static int TEST_CLICK_LOCATION_INITIAL = 150;
  private static final int TEST_CLICK_LOCATION_FINAL = 450;
  private static final int DEFAULT_BOARD_SIZE = 500;
  private static final int DEFAULT_CLICK_OFFSET = 5;
  private List<Integer> myClickLocations;

  // todo: fix this test, all wrong right now.
  @Override
  public void start(Stage stage) {
//    myBuilderController = new BuilderController(TEST_LANGUAGE, Path.of("data", "monopoly"));
//    myBuilder = myBuilderController.getBuilderView();
//    myBuilder.loadBoardSize(DEFAULT_BOARD_SIZE, DEFAULT_BOARD_SIZE);
    myClickLocations = new ArrayList<>();
  }

  @Test
  private void testPaneSwitch(){
    clickOn(lookup("#ChangeRules").query());
//    assert()
  }


  private void createTiles() {
    clickOn(lookup("#AddTile").query());
    clickOn(lookup("#NewTile").query());
    for (int i = TEST_CLICK_LOCATION_INITIAL; i <= TEST_CLICK_LOCATION_FINAL; i += 100) {
      clickBoard(i, i);
      myClickLocations.add(i + DEFAULT_CLICK_OFFSET);
    }
    clickOn(lookup("#CancelButton").query());
    clickOn(lookup("#BackButton").query());
  }

  private boolean checkIfExists(String ID){
    try {
      lookup(ID).query();
      return true;
    }
    catch (EmptyNodeQueryException e){
      return false;
    }
  }

  private void clickTwoTiles(String tileOneID, String tileTwoID){
    clickTile(tileOneID);
    clickTile(tileTwoID);
  }

  private void clickBoard(int x, int y){
    clickOn(lookup("#BoardPane").query(), x, y);
  }

  private void clickTile(String tile){
    clickOn(lookup(tile).query(), DEFAULT_CLICK_OFFSET, DEFAULT_CLICK_OFFSET);
  }

}
