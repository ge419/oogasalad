package oogasalad.view.builder;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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
    public String getValue() {
        return element.getText();
    }
}
