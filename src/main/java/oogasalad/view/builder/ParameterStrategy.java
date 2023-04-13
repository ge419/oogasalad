package oogasalad.view.builder;

import java.util.ResourceBundle;
import javafx.scene.Node;

public interface ParameterStrategy {

  Node renderInput(String name, ResourceBundle resourceBundle);

  Object getValue();
}
