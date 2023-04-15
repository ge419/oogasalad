package oogasalad.view.builder.popupform;

import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.Metadata;
import oogasalad.view.builder.BuilderUtility;

import java.util.ResourceBundle;

class ColorParameterStrategy implements ParameterStrategy, BuilderUtility {
    public ColorParameterStrategy(){}
    private ColorPicker element = new ColorPicker();
    @Override
    public Node renderInput(String name, ResourceBundle resourceBundle) {
        Node textLabel = new Text(name);
        element = (ColorPicker) makeColorPicker(name);
        return makeHBox(String.format("%sColorInput", name), textLabel, element);
    }
    @Override
    public Color getValue() {
        return element.getValue();
    }

    @Override
    public boolean validateInput(Metadata metadata) {
        return getValue().getClass().equals(Color.class);
    }

    @Override
    public void setValue(Attribute attribute) {
        //ColorAttribute.from(attribute).setValue(getValue());
    }
}