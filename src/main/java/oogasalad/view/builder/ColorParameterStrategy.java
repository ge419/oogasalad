package oogasalad.view.builder;

import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

class ColorParameterStrategy implements ParameterStrategy, BuilderUtility {

  private ColorPicker element = new ColorPicker();

  public ColorParameterStrategy() {
  }

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