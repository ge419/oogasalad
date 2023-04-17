package oogasalad.view.builder.popupform;

import com.google.inject.assistedinject.Assisted;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javax.inject.Inject;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.Metadata;
import oogasalad.model.attribute.StringAttribute;
import oogasalad.model.attribute.StringMetadata;
import oogasalad.view.builder.BuilderUtility;

public class TextParameterStrategy implements ParameterStrategy, BuilderUtility {

  private final StringAttribute attr;
  private final StringMetadata meta;
  private TextField element;

  @Inject
  public TextParameterStrategy(
      @Assisted Attribute attr,
      @Assisted Metadata meta) {
    this.attr = StringAttribute.from(attr);
    this.meta = StringMetadata.from(meta);
    element = new TextField();
  }

  @Override
  public Node renderInput() {
    String name = meta.getName();
    Node textLabel = new Text(name + " (String)");
    element = (TextField) makeTextField(name);
    return makeHBox(String.format("%sTextInput", name), textLabel, element);
  }

  @Override
  public boolean validateInput() {
    return getValue().getClass().equals(String.class);
  }
}
