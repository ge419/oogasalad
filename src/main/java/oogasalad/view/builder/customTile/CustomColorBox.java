package oogasalad.view.builder.customTile;

import com.google.gson.JsonObject;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oogasalad.model.attribute.ColorMetadata;
import oogasalad.model.attribute.Metadata;
import oogasalad.view.builder.BuilderUtility;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;

public class CustomColorBox extends Rectangle implements CustomElement, BuilderUtility {

    private String name = "";
    private String defaultColor;
    private double defaultOpacity;

    private double x; private double y;

    private boolean editable = false;
    private int index = -1; // it is -1 unless it is loaded from JSON

    private final String UNDERSPECIFIED_COLOR = "#FF0000";
    private final double UNDERSPECIFIED_OPACITY = 1;
    private final double UNDERSPECIFIED_LENGTH = 50;


    public CustomColorBox() {
        super();
        this.setFill(Color.web(UNDERSPECIFIED_COLOR, UNDERSPECIFIED_OPACITY));
        this.setWidth(UNDERSPECIFIED_LENGTH);
        this.setHeight(UNDERSPECIFIED_LENGTH);
    }

    public CustomColorBox(JsonObject jsonObject) {
        this.name = jsonObject.get("name").getAsString();
        this.defaultColor = jsonObject.get("color").getAsString();

        this.defaultOpacity = jsonObject.get("opacity").getAsDouble();
        this.setFill(Color.web(defaultColor,defaultOpacity));
        System.out.println("defaultOpacity = " + defaultOpacity);
        this.setEditable(jsonObject.get("editable").getAsBoolean());

        this.setWidth(jsonObject.get("width").getAsDouble());
        this.setHeight(jsonObject.get("height").getAsDouble());

        double translateX = jsonObject.get("translatex").getAsDouble();
        double translateY = jsonObject.get("translatey").getAsDouble();
        this.setTranslateX(translateX);
        this.setTranslateY(translateY);

        int index = jsonObject.get("index").getAsInt();
    }



    @Override
    public JsonObject save(Path folderPath) throws IOException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "CustomColorBox");
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("color", this.getFill().toString());
        jsonObject.addProperty("opacity", this.getOpacity());
        jsonObject.addProperty("index", this.getParent().getChildrenUnmodifiable().indexOf(this));
        jsonObject.addProperty("translatex", this.getTranslateX());
        jsonObject.addProperty("translatey", this.getTranslateY());
        jsonObject.addProperty("width", this.getWidth());
        jsonObject.addProperty("height", this.getHeight());
        jsonObject.addProperty("editable", this.editable);
        jsonObject.addProperty("viewable", this.editable);

        return jsonObject;
    }
    @Override
    public VBox getInfo(ResourceBundle languageBundle) {
        ArrayList colorBoxSpecificElements = new ArrayList();
        Label widthLabel = new Label("Width");
        colorBoxSpecificElements.add(widthLabel);
        colorBoxSpecificElements.add(createWidthSlider());
        Label heightLabel = new Label("Height");
        colorBoxSpecificElements.add(heightLabel);
        colorBoxSpecificElements.add(createHeightSlider());
        return makeVbox(this, colorBoxSpecificElements);
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
    public void setName(String text) {
        name = text;

    }

    @Override
    public boolean isEditable() {
        return editable;
    }

    @Override
    public void setEditable(boolean selected) {
        this.editable = selected;
    }

    private Slider createWidthSlider() {
        Slider widthSlider = new Slider(10, ((Pane) this.getParent()).getWidth(), this.getWidth()*1.1); //1.1 so users don't have to perfectly align
        widthSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            this.setWidth(newVal.doubleValue());
        });
        return widthSlider;
    }

    private Slider createHeightSlider() {
        Slider heightSlider = new Slider(10, ((Pane) this.getParent()).getHeight(), this.getHeight()*1.1); //1.1 so users don't have to perfectly align
        heightSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            this.setHeight(newVal.doubleValue());
        });
        return heightSlider;
    }

    @Override
    public Metadata getMetaData() {
        ColorMetadata metadata = new ColorMetadata(name.isEmpty() ? "Colorbox-" + UUID.randomUUID().toString() : name);
        metadata.setName(name);
        metadata.setDefaultValue(this.defaultColor);
        metadata.setEditable(editable);
        metadata.setViewable(editable);
        metadata.setDescription("A custom colorbox");
        return metadata;
    }

    @Override
    public void setValue(String loadedValue) {
        this.defaultColor = loadedValue;
        this.setFill(Color.web(defaultColor, this.defaultOpacity));
    }

}
