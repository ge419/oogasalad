package oogasalad.view.builder.popupform;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import oogasalad.model.attribute.*;
import oogasalad.view.builder.BuilderUtility;

import java.util.ResourceBundle;

class BooleanParameterStrategy implements ParameterStrategy, BuilderUtility {
    private CheckBox element;
    public BooleanParameterStrategy(){}
    @Override
    public Node renderInput(String name, ResourceBundle resourceBundle) {
        Node textLabel = new Text(name+" (True/False)");
        element = (CheckBox) makeCheckBox(name);
        return makeHBox(String.format("%sBooleanInput", name), textLabel, element);
    }
    @Override
    public boolean validateInput(Metadata metadata) {
        return getValue().getClass().equals(Boolean.class);
    }
    @Override
    public Boolean getValue() {
        return element.isSelected();
    }
    @Override
    public void setValue(Attribute attribute) {
        BooleanAttribute.from(attribute).setValue(getValue());
    }
}
