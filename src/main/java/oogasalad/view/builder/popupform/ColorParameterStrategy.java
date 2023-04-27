package oogasalad.view.builder.popupform;

import java.util.ResourceBundle;

import com.google.inject.assistedinject.Assisted;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import oogasalad.model.attribute.*;
import oogasalad.view.builder.BuilderUtility;

import javax.inject.Inject;

class ColorParameterStrategy implements ParameterStrategy, BuilderUtility {
    private final ColorAttribute attr;
    private final ColorMetadata meta;
    private ColorPicker element = new ColorPicker();

    @Inject
    public ColorParameterStrategy(
            @Assisted Attribute attr,
            @Assisted Metadata meta) {
        this.attr = ColorAttribute.from(attr);
        this.meta = (ColorMetadata) ColorMetadata.from(meta);
    }

    @Override
    public Node renderInput(ResourceBundle resourceBundle, Pane form) {
        String name = meta.getName();
        Node textLabel = new Text(name);
        element = (ColorPicker) makeColorPicker(name);
        return makeHBox(String.format("%sColorInput", name), textLabel, element);
    }

    @Override
    public void saveInput() {
        attr.setValue(getFieldValue());
    }

    @Override
    public boolean isInputValid() {
        return meta.isValidValue(getFieldValue());
    }

    @Override
    public Metadata getMetadata() {
        return meta;
    }

    @Override
    public Attribute getAttribute() {
        return attr;
    }

    private String getFieldValue() {
        return element.getValue().toString();
    }
}