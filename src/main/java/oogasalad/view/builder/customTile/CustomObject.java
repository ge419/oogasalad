package oogasalad.view.builder.customTile;

import com.google.gson.*;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import oogasalad.model.attribute.*;

import java.io.*;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class CustomObject extends StackPane {
    private final Path RESOURCE_PATH = Paths.get("data/customObjects");
    private final Runnable swapCurrentClicked;
    private String name;
    VBox currentClickedInfo;
    VBox newCurrentClickedInfo;


    public CustomObject(Runnable swapCurrentClicked){
        this.swapCurrentClicked = swapCurrentClicked;
    }

    void placeElm(CustomElement newObject) {
        Node newNode = (Node) newObject;
        newNode.setOnMouseClicked(event -> {
            newCurrentClickedInfo = newObject.getInfo();
            if (newCurrentClickedInfo != currentClickedInfo) {
                swapCurrentClicked.run();
                currentClickedInfo = newCurrentClickedInfo;
            }
        });
        makeDraggable(newNode);
        int index = newObject.getIndex();
        if (index != -1){
            this.getChildren().add(index, newNode);
        }
        else{
            this.getChildren().add(newNode);
        }
    }

    private class Delta {
        double x, y;
    }
    void makeDraggable(Node node) {
        final Delta dragDelta = new Delta();
        node.setOnMousePressed(mouseEvent -> {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = node.getTranslateX() - mouseEvent.getSceneX();
            dragDelta.y = node.getTranslateY() - mouseEvent.getSceneY();
        });
        node.setOnMouseDragged(mouseEvent -> {
            node.setTranslateX(mouseEvent.getSceneX() + dragDelta.x);
            node.setTranslateY(mouseEvent.getSceneY() + dragDelta.y);
        });
    }

    void nameObject() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Name CustomTile");
        dialog.setHeaderText("Enter a new name for the CustomTile:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(n -> {
            Stage stage = (Stage) getScene().getWindow();
            stage.setTitle(n);
            name = n;
        });
    }

    void saveLauncher(){
        if (name == null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter a file name before saving.");
            alert.showAndWait();
            return;
        }
        try{
            save();
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void save() throws IOException {
        Path customObjectDirectory = RESOURCE_PATH.resolve(this.name);
        JsonObject customTileObject = createCustomElement(customObjectDirectory);
        SimpleObjectSchema schema = makeSchema();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if (!Files.exists(customObjectDirectory)) {
            Files.createDirectories(customObjectDirectory);
        }

        Path customObjectPath = customObjectDirectory.resolve(this.name + ".json");
        if (!Files.exists(customObjectPath)) {
            Files.createFile(customObjectPath);
        }

        try (FileWriter writer = new FileWriter(customObjectPath.toFile())) {
            writer.write(gson.toJson(customTileObject));
        }

        Path schemaPath = customObjectDirectory.resolve(this.name + "_schema.json");
        if (!Files.exists(schemaPath)) {
            Files.createFile(schemaPath);
        }

//        try (FileWriter writer = new FileWriter(schemaPath.toFile())) {
//            writer.write(gson.toJson(schema));
//        }
    }




    private JsonObject createCustomElement(Path directory) throws IOException {
        JsonObject customTileObject = new JsonObject();
        customTileObject.addProperty("name", this.name);
        customTileObject.addProperty("width", this.getWidth());
        customTileObject.addProperty("height", this.getHeight());

        JsonArray customObjectsArray = new JsonArray();
        for (Node node : this.getChildren()) {
            CustomElement customObject = (CustomElement) node;
            JsonObject objectJson = customObject.save(directory);
            customObjectsArray.add(objectJson);
        }

        customTileObject.add("customObjects", customObjectsArray);
        return customTileObject;
    }

    void loadJson() {
        this.getChildren().clear();

        File selectedFile = chooseJsonFile();
        if (selectedFile != null) {
            try {
                JsonObject jsonObject = readJsonFromFile(selectedFile);
                resizeWindow(jsonObject.get("height").getAsDouble(), jsonObject.get("width").getAsDouble());
                this.name = jsonObject.get("name").getAsString();
                Stage stage = (Stage) this.getScene().getWindow();
                stage.setTitle(this.name);
                JsonArray customObjects = jsonObject.getAsJsonArray("customObjects");
                for (JsonElement jsonElement : customObjects) {
                    JsonObject customObject = jsonElement.getAsJsonObject();
                    CustomElement loadedObject = CustomElement.load(customObject);
                    this.placeElm(loadedObject);
                    loadedObject.setLocation();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private File chooseJsonFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Json File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files", "*.json"));
        return fileChooser.showOpenDialog(this.getScene().getWindow());
    }

    private JsonObject readJsonFromFile(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        }
    }

    private SimpleObjectSchema makeSchema(){
        List<Metadata> metadataList = new ArrayList<>();
        metadataList.addAll(getDefaultTileMetadata());

        for (Node node : this.getChildren()) {
            CustomElement customElm = (CustomElement) node;
            Metadata elementMetaData = customElm.getMetaData();
            metadataList.add(elementMetaData);
        }

        return new SimpleObjectSchema(this.name, metadataList);
    }

    private Collection<Metadata> getDefaultTileMetadata() {
        List<Metadata> metadataList = new ArrayList<>();
        StringMetadata type = new StringMetadata("customTile");
        type.setViewable(false);
        metadataList.add(type);
        metadataList.add(new PositionMetadata("position"));
        BooleanMetadata owned = new BooleanMetadata("owned");
        owned.setViewable(false);
        metadataList.add(new BooleanMetadata("owned"));
        DoubleMetadata widthMetaData = new DoubleMetadata("width");
        DoubleMetadata heightMetaData = new DoubleMetadata("height");
        widthMetaData.setDefaultValue(50);
        heightMetaData.setDefaultValue(50);
        metadataList.add(widthMetaData);
        metadataList.add(heightMetaData);
        metadataList.add(new TileListMetadata("next"));
        return metadataList;
    }

    private void resizeWindow(Double height, Double rightPaneWidth) {
        Stage stage = (Stage) this.getScene().getWindow();

        stage.setWidth(CustomObjectBuilderWindow.getLeftPaneWidth() + rightPaneWidth);
        stage.setHeight(height);
    }

    private Node getAsNode() {
        return (Node) this;
    }



}
