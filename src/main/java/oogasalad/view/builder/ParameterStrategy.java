package oogasalad.view.builder;

import javafx.scene.Node;

import java.util.ResourceBundle;

public interface ParameterStrategy {
    Node renderInput(String name, ResourceBundle resourceBundle);
    Object getValue();
}
