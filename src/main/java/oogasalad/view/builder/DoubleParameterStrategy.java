package oogasalad.view.builder;

import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

class DoubleParameterStrategy implements ParameterStrategy, BuilderUtility {

  private TextField element = new TextField();

  public DoubleParameterStrategy() {
  }

  @Override
  public Node renderInput(String name, ResourceBundle resourceBundle) {
    Node textLabel = new Text(name + " (Double)");
    element = (TextField) makeTextField(name);
    return makeHBox(String.format("%sDoubleInput", name), textLabel, element);
  }

  @Override
  public Double getValue() {
    try {
      double num = Double.parseDouble(element.getText());
      return num;
    } catch (NumberFormatException nfe) {
      System.out.println("Double not provided in double input");
    }
    return 0.0;
  }
}
