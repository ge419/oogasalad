package oogasalad.view.builder.popupform;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.Metadata;

import java.util.ResourceBundle;

public interface ParameterStrategy {

    Node renderInput(ResourceBundle resourceBundle, Pane form);

    void saveInput();

    boolean isInputValid();

    Metadata getMetadata();

    Attribute getAttribute();
}
