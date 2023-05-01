package oogasalad.view.builder;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import oogasalad.controller.BuilderController;
import oogasalad.model.accesscontrol.dao.GameDao;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.testfx.service.query.EmptyNodeQueryException;
import util.DukeApplicationTest;

/**
 * <p>Tests of tile interactions, such as creating tile paths and deleting tiles.</p>
 *
 * @author tmh85
 */
public class TileInteractionTests extends DukeApplicationTest {

  @Mock
  FileChooser fileChooser = mock(FileChooser.class);
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
    myBuilderController = new BuilderController(TEST_LANGUAGE, "0TTYFzGUPQG2HXB2BsB4",
        mock(GameDao.class));
    myBuilder = myBuilderController.getBuilderView();
    myBuilder.loadBoardSize(DEFAULT_BOARD_SIZE, DEFAULT_BOARD_SIZE);
    myClickLocations = new ArrayList<>();
    //myView = new BuilderController(DEFAULT_LANGUAGE).getBuilderView();
  }

  @Test
  void testPlaceTile() {
    createTiles();
    int expectedTileOnePLacement = TEST_CLICK_LOCATION_INITIAL + 10;
    int expectedTileTwoPlacement = TEST_CLICK_LOCATION_INITIAL + 110;

    clickBoard(expectedTileOnePLacement, expectedTileOnePLacement);
    assert(checkIfExists("#TileSelectMode"));

    clickOn(lookup("#CancelButton").query());

    clickBoard(expectedTileTwoPlacement, expectedTileTwoPlacement);
    assert(checkIfExists("#TileSelectMode"));
  }

  @Test
  void testSetNextTile() {
    createTiles();
    int expectedTileOnePlacement = myClickLocations.get(0);
    int expectedTileTwoPlacement = myClickLocations.get(1);

    clickBoard(expectedTileOnePlacement, expectedTileOnePlacement);
    clickBoard(expectedTileTwoPlacement, expectedTileTwoPlacement);

    assert(checkIfExists("#test0"));

    clickBoard(expectedTileTwoPlacement, expectedTileTwoPlacement);
    clickBoard(expectedTileOnePlacement, expectedTileOnePlacement);

    assert(checkIfExists("#test1"));
  }

  @Test
  void testTileDeletion(){
    createTiles();
    clickOn(lookup("#DeleteButton").query());
    for (int i = 0; i <= 3; i++){
      clickTile("#Tile" + i);
      assert(!checkIfExists("#Tile" + i));
    }
  }

  @Test
  void testTilePathDeletion(){
    createTiles();
    clickOn(lookup("#AddTile").query());
    // creates test0
    clickTwoTiles("#Tile0", "#Tile1");
    //creates test1
    clickTwoTiles("#Tile1", "#Tile0");
    clickOn(lookup("#RemoveNext").query());
    clickTwoTiles("#Tile0", "#Tile1");
    assert(!checkIfExists("#test0"));

    clickTwoTiles("#Tile1", "#Tile0");
    assert(!checkIfExists("#test1"));
  }

  @Test
  void testTileDrag(){
    createTiles();
    int firstTileLocation = myClickLocations.get(0);
    Node tile = lookup("#Tile0").query();
    drag(tile, MouseButton.PRIMARY).dropBy(100, 100);
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
