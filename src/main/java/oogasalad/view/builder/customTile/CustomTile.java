package oogasalad.view.builder.customTile;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import oogasalad.model.attribute.*;
import oogasalad.model.constructable.Tile;
import oogasalad.view.Coordinate;
import oogasalad.view.tiles.ViewTile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomTile extends Group implements ViewTile {
    private static final Logger LOGGER = LogManager.getLogger(CustomTile.class);
    private Color color;
    private Map<String, CustomElement> customElementMap;

    private String name;

    private final Tile modelTile;
    @Inject
    public CustomTile(@Assisted Tile BTile, SchemaDatabase database) {
        this.modelTile = BTile;
        modelTile.addSchema("customTile");

        StringAttribute jsonFileAttribute = StringAttribute.from(
            modelTile.getAttribute("customJson").get());
        String jsonFile = jsonFileAttribute.getValue();

        /*
        If jsonFile is empty this is coming from the builder
         */
        if (jsonFile.isEmpty()) {
            jsonFile = chooseJsonFile().toString();
            jsonFileAttribute.setValue(jsonFile);
            ObjectSchema newSchema = loadJsonForBuilder(jsonFile);
            database.addCustomSchema(newSchema);
            ArrayList<String> names = new ArrayList<>(modelTile.getSchemaNames());
            names.add(newSchema.getName());
            modelTile.setSchemaNames(names);
//            for (String name : names){
//                modelTile.addSchema(name);
//            }
            for (String name : names){
                System.out.println("name = " + name);;
            }
            for (Attribute a :  modelTile.getAllAttributes()){
                System.out.println(a.getKey() + " " +  a.toString());
            }
            bindListeners(newSchema.getAllMetadata().stream().map(Metadata::getKey).toList());

        }
        else{
            try {
                loadForGamePlay(jsonFile);
            } catch (IOException e) {
                throw new RuntimeException(e); //F-off
            }
        }
        this.setSize(modelTile.getWidth(), modelTile.getHeight());
    }

    private void bindListeners(List<String> names) {
        for (String name : names) {
            StringAttribute attribute = StringAttribute.from(modelTile.getAttribute(name).get());
            CustomElement node = customElementMap.get(name);
            attribute.valueProperty().addListener((observable, oldValue, newValue) -> {
                node.setValue(newValue);
            });
        }
    }

    private void loadForGamePlay(String jsonFile) throws IOException {
        StackPane s = new StackPane();
        File selectedFile = new File(jsonFile);
        JsonObject jsonObject = readJsonFromFile(selectedFile);
        s.setPrefHeight(jsonObject.get("height").getAsDouble());
        s.setPrefWidth(jsonObject.get("width").getAsDouble());
        //Something to preserve aspect ratio
        name = jsonObject.get("name").getAsString();
        customElementMap = new HashMap<>();
        JsonArray customObjects = jsonObject.getAsJsonArray("customObjects");
        for (JsonElement jsonElement : customObjects) {
            JsonObject customObject = jsonElement.getAsJsonObject();
            CustomElement loadedObject = CustomElement.load(customObject);
            String loadedValue = StringAttribute.from(modelTile.getAttribute(loadedObject.getName()).get()).getValue();
            if (!loadedValue.isEmpty()) {
                loadedObject.setValue(loadedValue);
            }
            if (loadedObject.getIndex() != -1){
                s.getChildren().add(loadedObject.getIndex(), (Node) loadedObject);
            }
            else{
                s.getChildren().add((Node) loadedObject);
            }
            loadedObject.setLocation();
            customElementMap.put(loadedObject.getName(),loadedObject);
            this.getChildren().add(s);
        }
    }

    ObjectSchema loadJsonForBuilder(String file) {
        this.getChildren().clear();

        StackPane s = new StackPane();

        File selectedFile = new File(file);

        if (selectedFile != null) {
            try {
                JsonObject jsonObject = readJsonFromFile(selectedFile);
                s.setPrefHeight(jsonObject.get("height").getAsDouble());
                s.setPrefWidth(jsonObject.get("width").getAsDouble());
                //Something to preserve aspect ratio
                name = jsonObject.get("name").getAsString();
                JsonArray customObjects = jsonObject.getAsJsonArray("customObjects");
                List<Metadata> metadataList = new ArrayList<>();
                customElementMap = new HashMap<>();
                for (JsonElement jsonElement : customObjects) {
                    JsonObject customObject = jsonElement.getAsJsonObject();
                    CustomElement loadedObject = CustomElement.load(customObject);
                    if (loadedObject.getIndex() != -1){
                        s.getChildren().add(loadedObject.getIndex(), (Node) loadedObject);
                    }
                    else{
                        s.getChildren().add((Node) loadedObject);
                    }
                    loadedObject.setLocation();
                    Metadata elementMetaData = loadedObject.getMetaData();
                    metadataList.add(elementMetaData);
                    customElementMap.put(loadedObject.getName(),loadedObject);
                }
                this.getChildren().add(s);
                LOGGER.info("creating schema {}", metadataList);
                return new SimpleObjectSchema(UUID.randomUUID().toString(), metadataList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new SimpleObjectSchema(UUID.randomUUID().toString(), List.of());
    }

//    private Metadata makeNameMetadata() {
//    }

    private JsonObject readJsonFromFile(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        }
    }

    private File chooseJsonFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Json File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files", "*.json"));
        return fileChooser.showOpenDialog(null);
    }


    public Coordinate getPosition() {
        return modelTile.getCoordinate();
    }

    public void setPosition(Coordinate coord) {
        this.setLayoutX(coord.getXCoor());
        this.setLayoutY(coord.getYCoor());
        this.getTransforms().add(new Rotate(coord.getAngle(), Rotate.Z_AXIS));
    }

    //could connect to color box element
    public Paint getColor() {
        return color;
    }

    public void setColor(Color color) {
        //this.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
        this.color = color;
    }


    @Override
    public String getTileId() {
        return this.modelTile.getId();
    }

    @Override
    public Tile getTile() {
        return this.modelTile;
    }


    @Override
    public void setSize(double width, double height) {
        Bounds bounds = this.getBoundsInLocal();
        double scaleX = width / bounds.getWidth();
        double scaleY = height / bounds.getHeight();
        this.setScaleX(scaleX);
        this.setScaleY(scaleY);
    }


    @Override
    public Node asNode() {
        return this;
    }
}
