package oogasalad.view.builder.popupform;

import com.google.inject.assistedinject.Assisted;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javax.inject.Inject;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.ColorAttribute;
import oogasalad.model.attribute.ColorMetadata;
import oogasalad.model.attribute.Metadata;
import oogasalad.view.builder.BuilderUtility;

/**
 * A strategy used by the popup form to display a form element when a JavaFX Color is required from
 * the user via user input. This consists of one labeled ColorPicker for the user to input a color.
 * Example Usage: VBox form = new VBox(); ColorParameterStrategy strategy = new
 * ColorParameterStrategy(myColorAttribute, myColorMetadata);
 * form.getChildren().add(strategy.renderInput(myResourceBundle, form));
 *
 * @author Jason Fitzpatrick
 */
public class ColorParameterStrategy implements ParameterStrategy, BuilderUtility {

  private final ColorAttribute attr;
  private final ColorMetadata meta;
  private ColorPicker element = new ColorPicker();

  /**
   * Creates an instance of ColorParameterStrategy Can be used to display form data to a user for a
   * color, validate the input, save to an attribute, and access the corresponding ColorAttribute
   * and ColorMetadata
   *
   * @param attr ColorAttribute
   * @param meta ColorMetadata
   */
  @Inject
  public ColorParameterStrategy(
      @Assisted Attribute attr,
      @Assisted Metadata meta) {
    this.attr = ColorAttribute.from(attr);
    this.meta = (ColorMetadata) ColorMetadata.from(meta);
  }

  /**
   * Returns a JavaFX form element for a color attribute
   *
   * @param resourceBundle
   * @param form           parent pane of element
   * @return HBox containing labeled JavaFX ColorPicker
   */
  @Override
  public Node renderInput(ResourceBundle resourceBundle, Pane form, String objectId) {
    String name = meta.getName();
    Node textLabel = new Text(name);
    element = (ColorPicker) makeColorPicker(name);
    return makeHBox(String.format("%sColorInput", name), textLabel, element);
  }

  /**
   * Saves input to instance's ColorAttribute
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
   * Gets ColorMetadata
   *
   * @return ColorMetadata
   */
  @Override
  public Metadata getMetadata() {
    return meta;
  }

  /**
   * Gets ColorAttribute
   *
   * @return ColorAttribute
   */
  @Override
  public Attribute getAttribute() {
    return attr;
  }

  private String getFieldValue() {
    return element.getValue().toString();
  }
}