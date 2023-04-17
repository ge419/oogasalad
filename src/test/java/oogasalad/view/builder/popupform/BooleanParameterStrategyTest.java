package oogasalad.view.builder.popupform;

import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.BooleanAttribute;
import oogasalad.model.attribute.BooleanMetadata;
import oogasalad.model.attribute.Metadata;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class BooleanParameterStrategyTest extends DukeApplicationTest {
    private static final String BASE_RESOURCE_PACKAGE = "view.builder.";
    private BooleanParameterStrategy booleanParameterStrategy;
    private BooleanMetadata meta;
    private BooleanAttribute attr;
    @Override
    public void start(Stage stage) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "EnglishBuilderText");
        String attributeKey = "attr";
        meta = new BooleanMetadata(attributeKey);
        meta.setName(attributeKey);
        attr = meta.makeBooleanAttribute();
        booleanParameterStrategy = new BooleanParameterStrategy(attr, meta);
        VBox root = new VBox(booleanParameterStrategy.renderInput(resourceBundle));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @Test
    void renderInput() {
        CheckBox field = lookup("#attr").query();
        clickOn(field);
        assertEquals(field.isSelected(), true);
        clickOn(field);
        assertEquals(field.isSelected(), false);
    }

    @Test
    void isInputValid() {
        CheckBox field = lookup("#attr").query();
        clickOn(field);
        assertEquals(true, booleanParameterStrategy.isInputValid());

        clickOn(field);
        assertEquals(true, booleanParameterStrategy.isInputValid());
    }
    @Test
    void saveInput() {
        CheckBox field = lookup("#attr").query();
        clickOn(field);
        booleanParameterStrategy.saveInput();
        assertEquals(true, attr.getValue());
        clickOn(field);
        booleanParameterStrategy.saveInput();
        assertEquals(false, attr.getValue());
    }
    @Test
    void getMetadata() {
        BooleanMetadata booleanMeta = (BooleanMetadata) booleanParameterStrategy.getMetadata();
        assertEquals(booleanMeta.getKey(), meta.getKey());
        assertEquals(booleanMeta.getName(), meta.getName());
    }
    @Test
    void getAttribute() {
        BooleanAttribute booleanAttr = (BooleanAttribute) booleanParameterStrategy.getAttribute();
        assertEquals(booleanAttr.getKey(), attr.getKey());
    }
}