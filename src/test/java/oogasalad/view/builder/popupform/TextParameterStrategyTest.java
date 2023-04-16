package oogasalad.view.builder.popupform;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oogasalad.model.attribute.Metadata;
import oogasalad.model.attribute.StringMetadata;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class TextParameterStrategyTest extends DukeApplicationTest {
    private static final String BASE_RESOURCE_PACKAGE = "view.builder.";
    private TextParameterStrategy textParameterStrategy;
    private Metadata metadata;
    @Override
    public void start(Stage stage) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "EnglishBuilderText");
        String key = "attr";
        textParameterStrategy = new TextParameterStrategy();
        metadata = new StringMetadata(key);
        VBox root = new VBox(textParameterStrategy.renderInput(key, resourceBundle));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @Test
    void renderInput() {
        TextField field = lookup("#attr").query();
        writeInputTo(field, "Hello World!");
        assertEquals(field.getText(), "Hello World!");
    }

    @Test
    void validateInput() {
        assertEquals(true, textParameterStrategy.validateInput(metadata));
    }

    @Test
    void getValue() {
        TextField field = lookup("#attr").query();
        writeInputTo(field, "Hello World!");
        assertEquals(textParameterStrategy.getValue(), "Hello World!");
    }

    @Test
    void setValue() {
    }
}