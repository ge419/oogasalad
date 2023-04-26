package oogasalad.view.builder;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import oogasalad.controller.BuilderController;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import util.DukeApplicationTest;

public class BuilderViewTest extends DukeApplicationTest {

  @Mock
  FileChooser fileChooser = Mockito.mock(FileChooser.class);
  private BuilderView myView;
  private static final String DEFAULT_LANGUAGE = "English";

  // todo: fix this test, all wrong right now.
  @Override
  public void start(Stage stage) {
    myView = new BuilderController(DEFAULT_LANGUAGE).getBuilderView();
  }

  @Test
  void testLoadImage() {
    String goodFile = "view/builder/design_example_board.png";
    String notanImage = "view/builder/NOT_AN_IMAGE.nope";
    String badImage = "view/builder/BAD_IMAGE.png";
    String nothing = "";

    //clickOn(lookup("#UploadImage").query());
    // TODO: we need to figure out how to simulate the filechooser to upload an image with DependencyInjection maybe?
  }

  @Test
  void testPlaceTile() {
    createTiles();


  }

  @Test
  void testSetNextTile() {
    createTiles();

    clickOn(lookup("#BoardPane").query(), 165, 165);
    clickOn(lookup("#BoardPane").query(), 315, 315);

    // todo: use dependency injection to check graph
  }

  private void createTiles() {
    clickOn(lookup("#AddTile").query());
    clickOn(lookup("Property").query());
    clickOn(lookup("#BoardPane").query(), 150, 150);

    clickOn(lookup("Property").query());
    clickOn(lookup("#BoardPane").query(), 300, 300);
  }

}
