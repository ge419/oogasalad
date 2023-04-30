package oogasalad.view.builder.customTile;

import com.google.gson.JsonObject;
import javafx.beans.property.Property;
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

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomText extends Label implements CustomElement {
    private String name = "";
    private String defaultContents;
    private int fontSize;
    private boolean bold;

    double x; double y;
    int index = -1;

    private boolean editable = false;
    private final int UNDERSPECIFIED_FONTSIZE = 14 ;


    public CustomText(String defaultContents) {
        this.defaultContents = defaultContents;
        this.fontSize = UNDERSPECIFIED_FONTSIZE;
        // Set the initial properties of the text field
        setText(defaultContents);
    }

    public CustomText(JsonObject jsonObject) {
        super(jsonObject.get("name").getAsString());

        this.name =  jsonObject.get("name").getAsString();
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


    public void setName(String name) {
        this.name = name;
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

    public VBox getInfo() {
        List<Node> nodes = new ArrayList<>();

        // Create a text field to edit the default text displayed
        TextField defaultTextField = new TextField(this.defaultContents);
        defaultTextField.setOnAction(event -> setDefaultContents(defaultTextField.getText()));
        nodes.add(new Label("Default Contents:"));
        nodes.add(defaultTextField);

        // Create a slider to adjust the font size
        Slider fontSizeSlider = new Slider(8, 72, this.getFontSize());
        fontSizeSlider.setBlockIncrement(1);
        fontSizeSlider.valueProperty().addListener((obs, oldVal, newVal) -> setFontSize(newVal.intValue()));
        nodes.add(new Label("Font Size:"));
        nodes.add(fontSizeSlider);

        // Create a checkbox to toggle bold font
        CheckBox boldCheckbox = new CheckBox("Bold");
        boldCheckbox.setSelected(this.isBold());
        boldCheckbox.setOnAction(event -> setBold(boldCheckbox.isSelected()));
        nodes.add(new Label("Bold:"));

        return CustomElementHelper.makeVbox(this, nodes);
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

    @Override
    public Metadata getMetaData() {
        StringMetadata metadata = new StringMetadata(name.isEmpty() ? "Textfield-" + UUID.randomUUID().toString() : name);
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
