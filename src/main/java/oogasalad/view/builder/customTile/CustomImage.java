package oogasalad.view.builder.customTile;

import com.google.gson.JsonObject;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import oogasalad.model.attribute.ImageMetadata;
import oogasalad.model.attribute.Metadata;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomImage extends ImageView implements CustomElement {

    String name = "";
    Path destinationPath;
    File originalFile;
    double x; double y;

    private boolean editable = false;

    int index = -1;


    public CustomImage(File file) {
        super(new Image(file.toURI().toString()));
        originalFile = file;
        this.setPreserveRatio(true);
    }

    public CustomImage(JsonObject jsonObject) {
        super(new Image(new File(jsonObject.get("filePath").getAsString()).toURI().toString()));
        this.name = jsonObject.get("name").getAsString();
        this.originalFile = new File(jsonObject.get("filePath").getAsString());
        this.setFitWidth(jsonObject.get("width").getAsDouble());
        this.setFitHeight(jsonObject.get("height").getAsDouble());
        this.x = jsonObject.get("x").getAsDouble();
        this.y = jsonObject.get("y").getAsDouble();
        this.index = jsonObject.get("index").getAsInt();
        this.editable = jsonObject.get("editable").getAsBoolean();

    }


    public void fitImage(double rightPaneWidth, double rightPaneHeight) {
        // Resize the image to fit within the bounds of the StackPane
        double maxWidth = rightPaneWidth - 20;
        double maxHeight = rightPaneHeight - 20;
        double width = this.getImage().getWidth();
        double height = this.getImage().getHeight();
        double scale = Math.min(maxWidth / width, maxHeight / height);
        this.setFitWidth(scale * width);
        this.setFitHeight(scale * height);
    }

    public VBox getInfo() {
        List<Node> imageSpecificNodes = new ArrayList<>();

        // Create a label for the size slider
        Label sizeLabel = new Label("Image Size:");

        // Create a slider to scale the image
        Slider sizeSlider = createSizeSlider();
        imageSpecificNodes.addAll(Arrays.asList(sizeLabel, sizeSlider));

        return CustomElementHelper.makeVbox(this, imageSpecificNodes);
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


    private Slider createSizeSlider() {
        Slider sizeSlider = new Slider(10, ((Pane) this.getParent()).getWidth(), this.getFitWidth());
        sizeSlider.setBlockIncrement(10);
        sizeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double scalingFactor = newVal.doubleValue() / oldVal.doubleValue();
            this.setFitWidth(scalingFactor * this.getFitWidth());
            this.setFitHeight(scalingFactor * this.getFitHeight());
        });
        return sizeSlider;
    }

    @Override
    public void setName(String text) {
        this.name = text;
    }

    @Override
    public boolean isEditable() {
        return editable;
    }

    @Override
    public void setEditable(boolean selected) {
        this.editable = selected;
    }

    public JsonObject save(Path directory) throws IOException {
        this.destinationPath = directory;

        if (!Files.exists(destinationPath)) {
            Files.createDirectories(destinationPath);
        }
        saveImage(this.originalFile, directory);

        // Create JSON object and add properties
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "CustomImage");
        jsonObject.addProperty("x", this.getTranslateX());
        jsonObject.addProperty("y", this.getTranslateY());
        jsonObject.addProperty("name", this.name);
        jsonObject.addProperty("height", this.getFitHeight());
        jsonObject.addProperty("width", this.getFitWidth());
        jsonObject.addProperty("filePath", destinationPath.toString()+"/"+this.name);
        jsonObject.addProperty("index", this.getParent().getChildrenUnmodifiable().indexOf(this));
        jsonObject.addProperty("editable", this.editable);

        return jsonObject;
    }




    private void saveImage(File originalFile, Path directoryPath) throws IOException {
        String saveName = this.name;
        saveName = resolveImageName(saveName);

        System.out.println("saveName = " + saveName);
        Path newFilePath = directoryPath.resolve(saveName);
        if (!Files.exists(newFilePath)) {
            Files.createFile(newFilePath); //Line Errors
        }

        Files.copy(originalFile.toPath(), newFilePath, StandardCopyOption.REPLACE_EXISTING);
    }

    private String resolveImageName(String saveName) {
        if (this.name == null) {
            this.name = this.originalFile.getName();
            System.out.println("this.originalFile.getName() = " + this.originalFile.getName());
        }
        this.name = this.name.endsWith(".png") ? this.name : this.name + ".png";
        return name;
    }

    @Override
    public Metadata getMetaData() {
        ImageMetadata metadata = new ImageMetadata(this.name);
        metadata.setDefaultValue(this.destinationPath.toString());
        Boolean named = (name == null);
        String temp = (named ? "this" : "the " + name);
        metadata.setEditable(editable);
        metadata.setViewable(editable);
        metadata.setDescription("The path to " + temp + " image");
        return metadata;
    }
}
