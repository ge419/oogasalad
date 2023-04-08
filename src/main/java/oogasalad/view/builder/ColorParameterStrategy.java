package oogasalad.view.builder;

import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

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
}