package oogasalad.view.builder.customTile;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import oogasalad.model.attribute.*;
import oogasalad.model.constructable.Tile;
import oogasalad.view.Coordinate;
import oogasalad.view.tiles.ViewTile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CustomTileFrontEnd extends Group implements ViewTile {
    private static final Logger LOGGER = LogManager.getLogger(CustomTileFrontEnd.class);

    private double SCALE_DOWN_FACTOR = .4;
    private Color color;

    private String name;

    private final Tile modelTile;
    @Inject
    public CustomTileFrontEnd(@Assisted Tile BTile, SchemaDatabase database) {
        this.modelTile = BTile;
        String jsonFile = "";
                //StringAttribute.from(modelTile.getAttribute("customJson").get()).getValue();

        /*
        If jsonFile is empty this is coming from the builder
         */
        if (jsonFile.isEmpty()) {
            jsonFile = chooseJsonFile().toString();
            ObjectSchema schema = loadJsonForBuilder(jsonFile);
            database.addCustomSchema(schema);
            ArrayList<String> names = new ArrayList<>(modelTile.getSchemaNames());
            names.add(schema.getName());
            modelTile.setJsonSchemaNames(names);
        }
        else{
            try {
                loadForGamePlay(jsonFile);
            } catch (IOException e) {
                throw new RuntimeException(e); //F-off
            }
        }
    }

    private void loadForGamePlay(String jsonFile) throws IOException {
        StackPane s = new StackPane();
        File selectedFile = new File(jsonFile);
        JsonObject jsonObject = readJsonFromFile(selectedFile);
        s.setPrefHeight(jsonObject.get("height").getAsDouble()*SCALE_DOWN_FACTOR);
        s.setPrefWidth(jsonObject.get("width").getAsDouble()*SCALE_DOWN_FACTOR);
        //Something to preserve aspect ratio
        name = jsonObject.get("name").getAsString();
        JsonArray customObjects = jsonObject.getAsJsonArray("customObjects");
        for (JsonElement jsonElement : customObjects) {
            JsonObject customObject = jsonElement.getAsJsonObject();
            CustomElement loadedObject = CustomElement.load(customObject);
            String loadedValue = StringAttribute.from(modelTile.getAttribute(loadedObject.getName()).get()).getValue();
            if (!loadedValue.isEmpty()) {
                //loadedObject.setValue(loadedValue);
            }
            if (loadedObject.getIndex() != -1){
                s.getChildren().add(loadedObject.getIndex(), (Node) loadedObject);
            }
            else{
                s.getChildren().add((Node) loadedObject);
            }
            loadedObject.setLocation();
        }
    }

    ObjectSchema loadJsonForBuilder(String file) {
        this.getChildren().clear();

        StackPane s = new StackPane();

        File selectedFile = new File(file);
        if (selectedFile != null) {
            try {
                JsonObject jsonObject = readJsonFromFile(selectedFile);
                s.setPrefHeight(jsonObject.get("height").getAsDouble()*SCALE_DOWN_FACTOR);
                s.setPrefWidth(jsonObject.get("width").getAsDouble()*SCALE_DOWN_FACTOR);
                //Something to preserve aspect ratio
                name = jsonObject.get("name").getAsString();
                JsonArray customObjects = jsonObject.getAsJsonArray("customObjects");
                List<Metadata> metadataList = new ArrayList<>();
                //metadataList.add(makeNameMetadata());
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

//    @Override
//    public void setSize(double width, double height) {
//        double scaleX = width / this.getWidth();
//        double scaleY = height / this.getHeight();
//
//        this.setScaleX(scaleX);
//        this.setScaleY(scaleY);
//
//        for (Node child : this.getChildren()) {
//            if (child instanceof Region) {
//                Region region = (Region) child;
//                region.setPrefWidth(width);
//                region.setPrefHeight(height);
//            }
//            else if (child instanceof Text) {
//                Text text = (Text) child;
//                text.setFont(Font.font(text.getFont().getFamily(), text.getFont().getSize() * scaleX));
//            } else if (child instanceof ImageView) {
//                ImageView imageView = (ImageView) child;
//                imageView.setFitWidth(width);
//                imageView.setFitHeight(height);
//            } else {
//                child.setScaleX(scaleX);
//                child.setScaleY(scaleY);
//            }
//        }
//    }

    @Override
    public void setSize(double width, double height) {

        Bounds bounds = this.getBoundsInLocal();
        double scaleX = width / bounds.getWidth();
        double scaleY = height / bounds.getHeight();


        System.out.println("scaleY = " + scaleX);


        System.out.println("scaleY = " + scaleY);


        this.setScaleX(scaleX);
        this.setScaleY(scaleY);
    }


    @Override
    public Node asNode() {
        return this;
    }
}
