//package oogasalad.view.builder;
//
//import java.nio.file.Path;
//import java.util.Optional;
//import javafx.stage.FileChooser;
//import javafx.stage.Stage;
//import oogasalad.controller.BuilderController;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import util.DukeApplicationTest;
//
//public class BuilderViewTest extends DukeApplicationTest {
//
//  @Mock
//  FileChooser fileChooser = Mockito.mock(FileChooser.class);
//  private BuilderView myBuilder;
//  private BuilderController myBuilderController;
//  private static final String TEST_LANGUAGE = "en-US";
//  private static int TEST_CLICK_LOCATION_1 = 150;
//  private static final int TEST_CLICK_LOCATION_2 = 300;
//
//  // todo: fix this test, all wrong right now.
//  @Override
//  public void start(Stage stage) {
//    myBuilderController = new BuilderController(TEST_LANGUAGE, Path.of("data", "monopoly"));
//    myBuilder = myBuilderController.getBuilderView();
//    //myView = new BuilderController(DEFAULT_LANGUAGE).getBuilderView();
//  }
//
//  @Test
//  void testPlaceTile() {
//    createTiles();
//    int expectedTileOnePLacement = TEST_CLICK_LOCATION_1 + 10;
//    int expectedTileTwoPlacement = TEST_CLICK_LOCATION_2 + 10;
//
//    clickOn(lookup("#BoardPane").query(), expectedTileOnePLacement, expectedTileOnePLacement);
//    assert(checkIfExists("TileSelectMode"));
//
//    myBuilder.cancelAction();
//
//    clickOn(lookup("#BoardPane").query(), expectedTileTwoPlacement, expectedTileTwoPlacement);
//    assert(checkIfExists("#TileSelectMode"));
//  }
//
//  @Test
//  void testSetNextTile() {
//    createTiles();
//
//  }
//
//  private void createTiles() {
//    clickOn(lookup("#AddTile").query());
//    clickOn(lookup("#NewTile").query());
//    clickOn(lookup("#BoardPane").query(), TEST_CLICK_LOCATION_1, TEST_CLICK_LOCATION_1);
//
//    clickOn(lookup("#BoardPane").query(), TEST_CLICK_LOCATION_2, TEST_CLICK_LOCATION_2);
//  }
//
//  private boolean checkIfExists(String ID){
//    return Optional.ofNullable(lookup(ID).query()).isPresent();
//  }
//
//}
