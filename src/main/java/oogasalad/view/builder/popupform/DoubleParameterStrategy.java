package oogasalad.view.builder.popupform;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import oogasalad.model.attribute.*;
import oogasalad.view.builder.BuilderUtility;

import java.util.ResourceBundle;

class DoubleParameterStrategy implements ParameterStrategy, BuilderUtility {
    public DoubleParameterStrategy(){}
    private TextField element = new TextField();
    @Override
    public Node renderInput(String name, ResourceBundle resourceBundle) {
        Node textLabel = new Text(name+" (Double)");
        element = (TextField) makeTextField(name);
        return makeHBox(String.format("%sDoubleInput", name), textLabel, element);
    }
    @Override
    public boolean validateInput(Metadata metadata) {
        DoubleMetadata doubleMetadata = (DoubleMetadata) metadata;
        return getValue() > doubleMetadata.getMinValue() && getValue() < doubleMetadata.getMaxValue();
    }
    @Override
    public Double getValue() {
        try {
            double num = Double.parseDouble(element.getText());
            return num;
        } catch (NumberFormatException nfe) {
            System.out.println("Double not provided in double input");
            // should be a log and popup error
        }
        return 0.0;
    }
    @Override
    public void setValue(Attribute attribute) {
        DoubleAttribute.from(attribute).setValue(getValue());
    }
}
