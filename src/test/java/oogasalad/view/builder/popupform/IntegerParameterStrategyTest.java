package oogasalad.view.builder.popupform;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oogasalad.model.attribute.IntMetadata;
import oogasalad.model.attribute.Metadata;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class IntegerParameterStrategyTest extends DukeApplicationTest {
    private static final String BASE_RESOURCE_PACKAGE = "view.builder.";
    private IntegerParameterStrategy integerParameterStrategy;
    private Metadata metadata;
    @Override
    public void start(Stage stage) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "EnglishBuilderText");
        String key = "attr";
        integerParameterStrategy = new IntegerParameterStrategy();
        metadata = new IntMetadata(key);
        VBox root = new VBox(integerParameterStrategy.renderInput(key, resourceBundle));
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
        TextField field = lookup("#attr").query();
        writeInputTo(field, "0");
        assertEquals(true, integerParameterStrategy.validateInput(metadata));

        writeInputTo(field, "Hello World!");
        assertEquals(false, integerParameterStrategy.validateInput(metadata));

        writeInputTo(field, "0.125");
        assertEquals(false, integerParameterStrategy.validateInput(metadata));
    }

    @Test
    void getValue() {
        TextField field = lookup("#attr").query();
        writeInputTo(field, "10");
        assertEquals(integerParameterStrategy.getValue(), 10);
    }

    @Test
    void setValue() {
    }
}