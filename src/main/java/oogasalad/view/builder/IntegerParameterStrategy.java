package oogasalad.view.builder;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.ResourceBundle;

class IntegerParameterStrategy implements ParameterStrategy, BuilderUtility{
    public IntegerParameterStrategy(){}
    private TextField element = new TextField();
    @Override
    public Node renderInput(String name, ResourceBundle resourceBundle) {
        Node textLabel = new Text(name+" (Integer)");
        element = (TextField) makeTextField(name);
        return makeHBox(String.format("%sIntegerInput", name), textLabel, element);
    }
    @Override
    public Integer getValue() {
        try {
            int num = Integer.parseInt(element.getText());
            return num;
        } catch (NumberFormatException nfe) {
            System.out.println("Integer not provided in integer input");
        }
        return 0;
    }
}
