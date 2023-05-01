package oogasalad.view.builder.popupform;

import com.google.inject.assistedinject.Assisted;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javax.inject.Inject;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.DoubleAttribute;
import oogasalad.model.attribute.DoubleMetadata;
import oogasalad.model.attribute.Metadata;
import oogasalad.view.builder.BuilderUtility;

/**
 * A strategy used by the popup form to display a form element when a double is required from the
 * user via user input. This consists of one labeled double spinner for the user to input a double.
 * Example Usage: VBox form = new VBox(); DoubleParameterStrategy strategy = new
 * DoubleParameterStrategy(myDoubleAttribute, myDoubleMetadata);
 * form.getChildren().add(strategy.renderInput(myResourceBundle, form));
 *
 * @author Jason Fitzpatrick
 */
public class DoubleParameterStrategy implements ParameterStrategy, BuilderUtility {

  private final DoubleAttribute attr;
  private final DoubleMetadata meta;
  private Spinner<Double> element;

  /**
   * Creates an instance of DoubleParameterStrategy Can be used to display form data to a user for a
   * double, validate the input, save to an attribute, and access the corresponding DoubleAttribute
   * and DoubleMetadata
   *
   * @param attr DoubleAttribute
   * @param meta DoubleMetadata
   */
  @Inject
  public DoubleParameterStrategy(
      @Assisted Attribute attr,
      @Assisted Metadata meta) {
    this.attr = DoubleAttribute.from(attr);
    this.meta = DoubleMetadata.from(meta);
  }

  /**
   * Returns a JavaFX form element for a double attribute
   *
   * @param resourceBundle
   * @param form           parent pane of element
   * @return HBox containing labeled JavaFX double spinner
   */
  @Override
  public Node renderInput(ResourceBundle resourceBundle, Pane form, String objectId) {
    String name = meta.getName();
    Node textLabel = new Text(name);
    element = (Spinner<Double>) makeDoubleSpinner(name, meta.getMinValue(), meta.getMaxValue(),
        attr.getValue());
    return makeHBox(String.format("%sDoubleInput", name), textLabel, element);
  }

  /**
   * Saves input to instance's DoubleAttribute
   */
  @Override
  public void saveInput() {
    attr.setValue(getFieldValue());
  }

  /**
   * Uses metadata to validate user input
   *
   * @return boolean (true means input is valid)
   */
  @Override
  public boolean isInputValid() {
    return meta.isValidValue(getFieldValue());
  }

  /**
   * Gets DoubleMetadata
   *
   * @return DoubleMetadata
   */
  @Override
  public Metadata getMetadata() {
    return meta;
  }

  /**
   * Gets DoubleAttribute
   *
   * @return DoubleAttribute
   */
  @Override
  public Attribute getAttribute() {
    return attr;
  }

  private double getFieldValue() {
    return element.getValue();
  }
}

