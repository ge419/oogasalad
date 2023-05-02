package oogasalad.view.builder.customTile;


import static org.mockito.Mockito.mock;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.GameHolder;
import oogasalad.util.SaveManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.testfx.framework.junit5.ApplicationTest;


import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;


import org.mockito.Mock;

import javax.swing.text.html.ImageView;

public class CustomImageTest extends ApplicationTest {

    private SaveManager saveManager;
    private CustomImage customImage;

    @Mock
    private SchemaDatabase schemaDatabase;

    @Mock
    private GameHolder gameHolder;

    @Mock
    private ObjectMapper objectMapper;

    @Override
    public void start(Stage stage) throws Exception {
        Path saveDirectory = Paths.get("data/games/mTAXDJfsEvYbP7lsInhB");
        schemaDatabase = mock(SchemaDatabase.class);
        gameHolder = mock(GameHolder.class);
        objectMapper = mock(ObjectMapper.class);
        saveManager = new SaveManager(saveDirectory, schemaDatabase, gameHolder, objectMapper);
    }


    /* NOTES ON THIS TEST:
    This test replicates the calls made to setValue by the CustomTile from SetBindings.
    On Sunday the ImageParameterParameter strategy changed to use the saveManager rather than save the whole file path
    this broke the integration with CustomImage and prevented CustomTiles from changing their image.

    The challenge was integrating the saveManager into CustomImage. Some substantive changes would have had to be made to
    directly inject the saveManger to CustomImage. A simpler solution was just to extend what was injected to CustomTile
    and have it call setValue with an extra parameter.

    Ideally the test would be more modular and actually test the output of the pop-up form saving so that further changes but testing this integration
    to how the popup form works would be caught but initializing the imageStrategyPattern directly has its own set of injection problems.
     */
    @Test
    public void testSetValue() throws InterruptedException, URISyntaxException {
        String fileName = "morning-glory_5.jpg";


        customImage = new CustomImage(new File("data/customObjects/Test1/morning-glory.jpg"));
        URI filePath = new URI(customImage.getImage().getUrl());
        Path path = Paths.get(filePath);
        Assertions.assertTrue(new File(path.toUri()).exists(), "Image file path is not valid: " + filePath);

        customImage.setValue(fileName);

        //The test below would trivially fail
        //customImage.setValue(fileName, saveManager);


        filePath = new URI(customImage.getImage().getUrl());
        path = Paths.get(filePath);
        Assertions.assertTrue(new File(path.toUri()).exists(), "Image file path is not valid: " + filePath);


//        Assertions.assertTrue(Files.exists(Paths.get(filePath2)), "Image file path is not valid: " + filePath2);


        System.out.println(customImage.getImage().toString());
//        Assertions.assertNotNull(customImage.getImage());
    }
}