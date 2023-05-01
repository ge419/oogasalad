package oogasalad.view.builder.popupform;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.Metadata;

/**
 * Text parameter used for schema types, updates respective attribute in real time.
 *
 * @author Dominic Martinez
 */
public class BindingParameterStrategy extends TextParameterStrategy {

  /**
   * Creates an instance of BindingParameterStrategy. Displays a text box to the user, but updates
   * the provided attribute immediately when the text is changed.
   *
   * @param attr StringAttribute
   * @param meta StringMetadata
   */
  @Inject
  public BindingParameterStrategy(
      @Assisted Attribute attr,
      @Assisted Metadata meta) {
    super(attr, meta);
  }

  @Override
  public Node renderInput(ResourceBundle resourceBundle, Pane form, String objectId) {
    Node node = super.renderInput(resourceBundle, form, objectId);
//    getElement().textProperty().addListener((observable, oldValue, newValue) -> saveInput());
    return node;
  }
}
