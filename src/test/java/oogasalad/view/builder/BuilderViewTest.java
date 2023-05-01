package oogasalad.view.builder;

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
import org.mockito.Mockito;
import org.testfx.service.query.EmptyNodeQueryException;
import util.DukeApplicationTest;

public class BuilderViewTest extends DukeApplicationTest {

  private static final String TEST_IMAGE = "src/test/resources/view/builder/design_example_board.png";
  private static final String RESOURCE_PATH = "src/test/resources/view/builder/en-US-BuilderText.properties";
  private static final String TEST_LANGUAGE = "en-US";
  private static final int TEST_CLICK_LOCATION_FINAL = 450;
  private static final int DEFAULT_BOARD_SIZE = 500;
  private static final int DEFAULT_CLICK_OFFSET = 5;
  private static final String TEST_GAME_ID = "hello";
  private static final int TEST_CLICK_LOCATION_INITIAL = 150;
  @Mock
  FileChooser fileChooser = Mockito.mock(FileChooser.class);
  @Mock
  GameDao myGameDao = Mockito.mock(GameDao.class);
  @Mock
  private BuilderView myBuilder;
  private BuilderController myBuilderController;
  private List<Integer> myClickLocations;

  // todo: fix this test, all wrong right now.
  @Override
  public void start(Stage stage) {
    myBuilderController = new BuilderController(TEST_LANGUAGE, TEST_GAME_ID, myGameDao);
    myBuilder = myBuilderController.getBuilderView();
    myBuilder.loadBoardSize(DEFAULT_BOARD_SIZE, DEFAULT_BOARD_SIZE);
    myClickLocations = new ArrayList<>();
  }

  @Test
  void testPaneSwitch() {
    clickOn(lookup("#ChangeRules").query());
    checkIfExists("#RulesPane");
    clickOn(lookup("#BackButton").query());
    checkIfExists("#BoardPane");
  }

  @Test
  void testAboutMenu() {
    clickOn(lookup("#AboutMenu").query());
    clickOn("#AboutButton");
  }

  @Test
  void testDragToggle() {
    createTiles();
    Node board = lookup("#BoardPane").query();

    Node tile = lookup("#Tile0").query();
    drag(tile, MouseButton.PRIMARY).dropBy(100, 100);
    int firstDragLocation = (int) tile.getBoundsInParent().getMinX();
    clickOn(lookup("#ToggleMenu").query());
    clickOn("#DragToggle");
    drag(tile, MouseButton.PRIMARY).dropBy(-100, -100);
    int toggledOffLocation = (int) tile.getBoundsInParent().getMinX();
    drag(tile, MouseButton.PRIMARY).dropBy(100, 100);

    clickOn(lookup("#ToggleMenu").query());
    clickOn("#DragToggle");
    drag(tile, MouseButton.PRIMARY).dropBy(-100, -100);
    int toggledOnLocation = (int) tile.getBoundsInParent().getMinX();

    assert (firstDragLocation == toggledOffLocation);
  }

  @Test
  void testUploadImage() {
//    Mockito.doReturn(Optional.of(new File(TEST_IMAGE))).when(myTest).fileLoad(myBuilder.getLanguage(), "UploadImageTitle");
//    when(myTest.fileLoad(myBuilder.getLanguage(), "UploadImageTitle")).thenReturn(Optional.of(new File(TEST_IMAGE)));
////    Mockito.when(myBuilder.fileLoad(myBuilder.getLanguage(), "UploadImageTitle")).thenReturn(
////        Optional.of(new File(TEST_IMAGE)));
////    when(fileChooser.showOpenDialog(this.targetWindow())).thenReturn(new File(TEST_IMAGE));
//    clickOn(lookup("#UploadImage").query());
//
//    Node image = lookup("#image0").query();
//    drag(image, MouseButton.PRIMARY).dropBy(100, 100);
//
//    checkIfExists("#image0");
  }

  @Test
  void testGuidelinesToggle() {
    createTiles();
    clickTwoTiles("#Tile0", "#Tile1");
    Node trail = lookup("#test0").query();
    assert (trail.getOpacity() >= 0.99);
    clickOn(lookup("#ToggleMenu").query());
    clickOn("#GuidelinesToggle");

    assert (trail.getOpacity() <= 0.01);

    clickOn(lookup("#ToggleMenu").query());
    clickOn("#GuidelinesToggle");

    assert (trail.getOpacity() >= 0.99);
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

  private boolean checkIfExists(String ID) {
    try {
      lookup(ID).query();
      return true;
    } catch (EmptyNodeQueryException e) {
      return false;
    }
  }

  private void clickTwoTiles(String tileOneID, String tileTwoID) {
    clickTile(tileOneID);
    clickTile(tileTwoID);
  }

  private void clickBoard(int x, int y) {
    clickOn(lookup("#BoardPane").query(), x, y);
  }

  private void clickTile(String tile) {
    clickOn(lookup(tile).query(), DEFAULT_CLICK_OFFSET, DEFAULT_CLICK_OFFSET);
  }

}
