package oogasalad.view.builder.customTile;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.StackPane;
import oogasalad.model.attribute.Metadata;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class CustomColorBoxTest {

    @BeforeAll
    public static void setUpJavaFX() {
        // Initializes JavaFX runtime environment
        JFXPanel jfxPanel = new JFXPanel();
        Platform.runLater(() -> {
            // No-op method to initialize JavaFX toolkit
        });
    }

    @Test
    void testCustomColorBoxMetadata() throws IOException {

        CustomColorBox colorBoxInit = new CustomColorBox();
        StackPane parent = new StackPane(colorBoxInit); //Used in Save to get colorboxindex


        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        File outputFile = new File(tempDir, "testCustomColorBox.json");



        JsonObject jsonObject = colorBoxInit.save(mock(Path.class));
        // Create a new CustomColorBox instance from the JsonObject
        CustomColorBox customColorBoxLoaded = new CustomColorBox(jsonObject);
        Metadata metadata = customColorBoxLoaded.getMetaData();
        assertEquals(metadata.getKey(), jsonObject.get("name").getAsString());
    }
}