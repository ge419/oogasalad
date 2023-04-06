package oogasalad.view.builder;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import util.DukeApplicationTest;

public class BuilderViewTest extends DukeApplicationTest {

    private BuilderView myView;
    @Mock
    FileChooser fileChooser = Mockito.mock(FileChooser.class);

    @Override
    public void start(Stage stage){
        myView = new BuilderView();
    }

    @Test
    void testLoadImage(){
        String goodFile = "view/builder/design_example_board.png";
        String notanImage = "view/builder/NOT_AN_IMAGE.nope";
        String badImage = "view/builder/BAD_IMAGE.png";
        String nothing = "";

        clickOn(lookup("#UploadImage").query());
        // TODO: we need to figure out how to simulate the filechooser to upload an image with DependencyInjection maybe?
    }
}
