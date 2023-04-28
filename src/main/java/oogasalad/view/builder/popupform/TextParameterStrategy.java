package oogasalad.view.builder.popupform;

import com.google.inject.assistedinject.Assisted;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javax.inject.Inject;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.Metadata;
import oogasalad.model.attribute.StringAttribute;
import oogasalad.model.attribute.StringMetadata;
import oogasalad.view.builder.BuilderUtility;
/**
 * A strategy used by the popup form to display a form element when
 * an image path is required from the user via user input. This consists
 * of one button that opens a file dialog when clicked.
 * Example Usage:
 * VBox form = new VBox();
 * StringParameterStrategy strategy = new StringParameterStrategy(myStringAttribute, myStringMetadata);
 * form.getChildren().add(strategy.renderInput(myResourceBundle, form));
 * @author Jason Fitzpatrick, Dominic Martinez
 */
public class TextParameterStrategy implements ParameterStrategy, BuilderUtility {

  private final StringAttribute attr;
  private final StringMetadata meta;
  private TextField element;
  /**
   * Creates an instance of TextParameterStrategy
   * Can be used to display form data to a user for a string,
   * validate the input, save to an attribute, and access
   * the corresponding StringAttribute and StringMetadata
   * @param attr StringAttribute
   * @param meta StringMetadata
   */
  @Inject
  public TextParameterStrategy(
      @Assisted Attribute attr,
      @Assisted Metadata meta) {
    this.attr = StringAttribute.from(attr);
    this.meta = StringMetadata.from(meta);
    element = new TextField();
  }
  /**
   * Returns a JavaFX form element for a string attribute
   * @param resourceBundle
   * @param form parent pane of element
   * @return HBox containing labeled JavaFX text field
   */
  @Override
  public Node renderInput(ResourceBundle resourceBundle, Pane form) {
    String name = meta.getName();
    Node textLabel = new Text(name);
    element = (TextField) makeTextField(name);
    element.setText(attr.getValue());
    return makeHBox(String.format("%sTextInput", name), textLabel, element);
  }
  /**
   * Saves input to instance's StringAttribute
   */
  @Override
  public void saveInput() {
    attr.setValue(getFieldValue());
  }
  /**
   * Uses metadata to validate user input
   * @return boolean (true means input is valid)
   */
  @Override
  public boolean isInputValid() {
    return meta.isValidValue(getFieldValue());
  }
  /**
   * Gets StringMetadata
   * @return StringMetadata
   */
  @Override
  public Metadata getMetadata() {
    return meta;
  }
  /**
   * Gets StringAttribute
   * @return StringAttribute
   */
  @Override
  public Attribute getAttribute() {
    return attr;
  }

  private String getFieldValue() {
    return element.getText();
  }
}
