package oogasalad.view.builder.popupform;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.Metadata;

import java.util.ResourceBundle;

/**
 * A strategy used by the popup form to display a form element when a data is required from the user
 * via user input. This must be overridden to be used.
 *
 * @author Jason Fitzpatrick, Dominic Martinez
 */
public interface ParameterStrategy {

  /**
   * Returns a form element as a JavaFX node to be displayed
   *
   * @param resourceBundle
   * @param form           the container of the form element returned
   * @return JavaFX Node
   */
  Node renderInput(ResourceBundle resourceBundle, Pane form, String objectId);

  /**
   * Saves the user input to the attribute given in the constructor
   */
  void saveInput();

  /**
   * Uses metadata in the constructor
   *
   * @return
   */
  boolean isInputValid();

  /**
   * Returns metadata given in the constructor
   *
   * @return Metadata
   */
  Metadata getMetadata();

  /**
   * Returns attribute given in the constructor
   *
   * @return Attribute
   */
  Attribute getAttribute();
}
