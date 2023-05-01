package oogasalad.view.builder.customTile;

import com.google.gson.JsonObject;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import oogasalad.model.attribute.Metadata;
import oogasalad.model.attribute.StringMetadata;
import oogasalad.view.builder.BuilderUtility;

public class CustomText extends Label implements CustomElement, BuilderUtility {

  private final int UNDERSPECIFIED_FONTSIZE = 14;
  double x;
  double y;
  int index = -1;
  private String name = "";
  private String defaultContents;
  private int fontSize;
  private boolean bold;
  private boolean editable = false;


  public CustomText(String defaultContents) {
    this.defaultContents = defaultContents;
    this.fontSize = UNDERSPECIFIED_FONTSIZE;
    // Set the initial properties of the text field
    setText(defaultContents);
  }

  public CustomText(JsonObject jsonObject) {
    super(jsonObject.get("name").getAsString());

    this.name = jsonObject.get("name").getAsString();
    this.editable = jsonObject.get("editable").getAsBoolean();

    // Set the text field properties
    this.defaultContents = jsonObject.get("defaultContents").getAsString();
    this.fontSize = jsonObject.get("fontSize").getAsInt();
    this.bold = jsonObject.get("bold").getAsBoolean();
    setText(defaultContents);
    setFont(Font.font("Arial", bold ? FontWeight.BOLD : FontWeight.NORMAL, fontSize));

    // Set the position properties
    this.setTranslateX(jsonObject.get("translateX").getAsDouble());
    this.setTranslateY(jsonObject.get("translateY").getAsDouble());
    this.x = jsonObject.get("translateX").getAsDouble();
    this.y = jsonObject.get("translateY").getAsDouble();


  }

  public String getDefaultContents() {
    return defaultContents;
  }

  public void setDefaultContents(String defaultContents) {
    this.defaultContents = defaultContents;
    this.setText(defaultContents);
  }

  public int getFontSize() {
    return fontSize;
  }

  public void setFontSize(int fontSize) {
    this.fontSize = fontSize;
    setFont(Font.font("Arial", bold ? FontWeight.BOLD : FontWeight.NORMAL, fontSize));
  }

  public boolean isBold() {
    return bold;
  }

  public void setBold(boolean bold) {
    this.bold = bold;
    setFont(Font.font("Arial", bold ? FontWeight.BOLD : FontWeight.NORMAL, fontSize));
  }

  @Override
  public boolean isEditable() {
    return editable;
  }

  @Override
  public void setEditable(boolean selected) {
    this.editable = selected;
  }

  public JsonObject save(Path path) {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("name", this.name);
    jsonObject.addProperty("defaultContents", this.defaultContents);
    jsonObject.addProperty("fontSize", this.fontSize);
    jsonObject.addProperty("bold", this.bold);
    jsonObject.addProperty("translateX", this.getTranslateX());
    jsonObject.addProperty("translateY", this.getTranslateY());
    jsonObject.addProperty("type", "CustomText");
    jsonObject.addProperty("index", this.getParent().getChildrenUnmodifiable().indexOf(this));
    jsonObject.addProperty("editable", this.editable);
    jsonObject.addProperty("viewable", this.editable);

    return jsonObject;
  }

  public VBox getInfo(ResourceBundle languageBundle) {
    List<Node> nodes = new ArrayList<>();

    nodes.add(makeLabel("defaultText", languageBundle));
    Node defaultTextField = makeTextField(languageBundle.getString("defaultText"));
    ((TextField) defaultTextField).setOnAction(
        event -> setDefaultContents(((TextField) defaultTextField).getText()));
    nodes.add(defaultTextField);

    // Create a slider to adjust the font size
    Slider fontSizeSlider = (Slider) makeSlider("fontSizeSlider", 8, 72, this.getFontSize());
    fontSizeSlider.setBlockIncrement(1);
    fontSizeSlider.valueProperty()
        .addListener((obs, oldVal, newVal) -> setFontSize(newVal.intValue()));
    nodes.add(makeLabel("fontSize", languageBundle));
    nodes.add(fontSizeSlider);

    // Create a checkbox to toggle bold font
    CheckBox boldCheckbox = (CheckBox) makeCheckBox("Bold", languageBundle);
    boldCheckbox.setSelected(this.isBold());
    boldCheckbox.setOnAction(event -> setBold(boldCheckbox.isSelected()));
    nodes.add(makeLabel("Bold", languageBundle));

    return makeVbox(this, nodes);
  }

  @Override
  public void setLocation() {
    this.setTranslateX(x);
    this.setTranslateY(y);
  }

  @Override
  public int getIndex() {
    return index;
  }

  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public Metadata getMetaData() {
    StringMetadata metadata = new StringMetadata(
        name.isEmpty() ? "Textfield-" + UUID.randomUUID() : name);
    metadata.setName(name);
    metadata.setDefaultValue(this.defaultContents);
    metadata.setEditable(editable);
    metadata.setViewable(editable);
    return metadata;
  }

  @Override
  public void setValue(String loadedValue) {
    this.setText(loadedValue);
    this.defaultContents = loadedValue;
  }


}
