package oogasalad.view.builder.popupform;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.Metadata;
import oogasalad.model.attribute.StringAttribute;
import oogasalad.view.builder.BuilderUtility;

import java.util.ResourceBundle;

public class TextParameterStrategy implements ParameterStrategy, BuilderUtility {
    private TextField element;

    public TextParameterStrategy() {
        element = new TextField();
    }

    @Override
    public Node renderInput(String name, ResourceBundle resourceBundle) {
        Node textLabel = new Text(name+" (String)");
        element = (TextField) makeTextField(name);
        return makeHBox(String.format("%sTextInput", name), textLabel, element);
    }
    @Override
    public boolean validateInput(Metadata metadata) {
        return getValue().getClass().equals(String.class);
    }

    @Override
    public String getValue() {
        return element.getText();
    }
    @Override
    public void setValue(Attribute attribute) {
        StringAttribute.from(attribute).setValue(getValue());
    }
}
