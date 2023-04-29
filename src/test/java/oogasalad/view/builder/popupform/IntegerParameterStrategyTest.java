package oogasalad.view.builder.popupform;

import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oogasalad.model.attribute.*;
import oogasalad.model.constructable.Tile;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class IntegerParameterStrategyTest extends DukeApplicationTest {
    private static final String BASE_RESOURCE_PACKAGE = "view.builder.";
    private IntegerParameterStrategy integerParameterStrategy;
    private IntMetadata meta;
    private IntAttribute attr;
    @Override
    public void start(Stage stage) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "EnglishBuilderText");
        String attributeKey = "attr";
        meta = new IntMetadata(attributeKey);
        meta.setName(attributeKey);
        attr = meta.makeIntAttribute();
        integerParameterStrategy = new IntegerParameterStrategy(attr, meta);
        VBox root = new VBox();
        Tile tileMock = mock(Tile.class);
        root.getChildren().add(integerParameterStrategy.renderInput(resourceBundle, root,
            tileMock.getId()));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @Test
    void renderInput() {
        int val = 1;
        Spinner<Integer> field = lookup("#attr").query();
        setValue(field, val);
        assertEquals(field.getValue(), val);
    }
    @Test
    void isInputValid() {
        int val = 1;
        Spinner<Integer> field = lookup("#attr").query();
        setValue(field, val);
        assertEquals(true, integerParameterStrategy.isInputValid());
    }
    @Test
    void saveInput() {
        int val = 1;
        Spinner<Integer> field = lookup("#attr").query();
        setValue(field, val);
        integerParameterStrategy.saveInput();
        assertEquals(attr.getValue(), val);
    }
    @Test
    void getMetadata() {
        IntMetadata intMeta = (IntMetadata) integerParameterStrategy.getMetadata();
        assertEquals(intMeta.getKey(), meta.getKey());
        assertEquals(intMeta.getName(), meta.getName());
    }
    @Test
    void getAttribute() {
        IntAttribute intAttr = (IntAttribute) integerParameterStrategy.getAttribute();
        assertEquals(intAttr.getKey(), attr.getKey());
    }
}