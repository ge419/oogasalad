package oogasalad.view.builder.customTile;

import com.google.gson.*;
import javafx.application.Application;
import javafx.geometry.Orientation;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import oogasalad.model.attribute.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class CustomObjectMaker extends Application {

    private String defaultTitle = "CustomTileMaker";

    private Stage stage;
    private LeftPane leftPane;
    private StackPane rightPane;

    private final int LEFT_PANE_WIDTH = 250;
    private int RIGHT_PANE_STARTING_WIDTH = 500;
    private int STARTING_HEIGHT = 500;

    private final Path RESOURCE_PATH = Paths.get("data/customObjects");

    private String name;
    private Node currentClickedInfo;


    @Override
    public void start(Stage PrimaryStage) {
        // Create StackPane for the right pane
        stage = PrimaryStage;
        rightPane = new CustomTileFrontEnd();
        leftPane = new LeftPane();

        // Create SplitPane and add left and right panes
        SplitPane splitPane = new SplitPane();
        formatBuilderSplitPane(splitPane);

        // Create scene and add SplitPane
        Scene scene = new Scene(splitPane, LEFT_PANE_WIDTH+RIGHT_PANE_STARTING_WIDTH, STARTING_HEIGHT);

        // Set the scene and show the stage
        stage.setScene(scene);
        stage.show();

        // Set listener for changes to window size
        stage.widthProperty().addListener((obs, oldVal, newVal) -> maintainSideBar());

    }

    private void formatBuilderSplitPane(SplitPane splitPane) {
        splitPane.setOrientation(Orientation.HORIZONTAL);
        splitPane.getItems().addAll(leftPane, rightPane);

        // Set initial position of the divider
        splitPane.setDividerPositions(0.3);

        // Set listener for changes to divider position
        splitPane.getDividers().get(0).positionProperty().addListener((obs, oldVal, newVal) -> {
            leftPane.setPrefWidth(LEFT_PANE_WIDTH);
            double rightWidth = splitPane.getWidth() - LEFT_PANE_WIDTH;
            rightPane.setPrefWidth(rightWidth);
        });
    }


    private void resizeWindow(Double height, Double rightPaneWidth) {
        stage.setWidth(LEFT_PANE_WIDTH + rightPaneWidth);
        stage.setHeight(height);
    }

    private void maintainSideBar() {
        double height = stage.getHeight();
        double width = stage.getWidth() - LEFT_PANE_WIDTH;
    }

    private void placeInLeftPane(CustomElement newObject) {
        Node newNode = (Node) newObject;
        newNode.setOnMouseClicked(event -> {
            VBox info = newObject.getInfo();
            if (info != currentClickedInfo) {
                leftPane.getChildren().remove(currentClickedInfo);
                leftPane.getChildren().add(info);
                currentClickedInfo = info;
            }
        });
        makeDraggable(newNode);
        int index = newObject.getIndex();
        if (index != -1){
            rightPane.getChildren().add(index, newNode);
        }
        else{
            rightPane.getChildren().add(newNode);
        }
    }

    private class Delta {
        double x, y;
    }
    private void makeDraggable(Node node) {
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

    public StackPane loadJsonToTile(JsonObject jsonObject){
        StackPane tile = new StackPane();
        tile.setLayoutY(jsonObject.get("height").getAsDouble());
        tile.setLayoutX(jsonObject.get("width").getAsDouble());

        JsonArray customObjects = jsonObject.getAsJsonArray("customObjects");
        for (JsonElement jsonElement : customObjects) {
            JsonObject customObject = jsonElement.getAsJsonObject();
            CustomElement loadedObject = CustomElement.load(customObject);
            tile.getChildren().add(customObject.get("index").getAsInt(), (Node) loadedObject);
            loadedObject.setLocation();
        }
        return tile;
    }


    public void save() throws IOException {
        Path customObjectDirectory = RESOURCE_PATH.resolve(this.name);
        JsonObject customTileObject = createCustomTileObject(customObjectDirectory);
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

        try (FileWriter writer = new FileWriter(schemaPath.toFile())) {
            writer.write(gson.toJson(schema));
        }
    }




    private JsonObject createCustomTileObject(Path directory) throws IOException {
        JsonObject customTileObject = new JsonObject();
        customTileObject.addProperty("name", this.name);
        customTileObject.addProperty("width", this.rightPane.getWidth());
        customTileObject.addProperty("height", this.rightPane.getHeight());

        JsonArray customObjectsArray = new JsonArray();
        for (Node node : rightPane.getChildren()) {
            CustomElement customObject = (CustomElement) node;
            JsonObject objectJson = customObject.save(directory);
            customObjectsArray.add(objectJson);
        }

        customTileObject.add("customObjects", customObjectsArray);
        return customTileObject;
    }

    private void loadJson() {
        this.rightPane.getChildren().clear();

        File selectedFile = chooseJsonFile();
        if (selectedFile != null) {
            try {
                JsonObject jsonObject = readJsonFromFile(selectedFile);
                resizeWindow(jsonObject.get("height").getAsDouble(), jsonObject.get("width").getAsDouble());
                this.name = jsonObject.get("name").getAsString();
                stage.setTitle(this.name);
                JsonArray customObjects = jsonObject.getAsJsonArray("customObjects");
                for (JsonElement jsonElement : customObjects) {
                    JsonObject customObject = jsonElement.getAsJsonObject();
                    CustomElement loadedObject = CustomElement.load(customObject);
                    this.placeInLeftPane(loadedObject);
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
        return fileChooser.showOpenDialog(stage);
    }

    private JsonObject readJsonFromFile(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        }
    }

    private SimpleObjectSchema makeSchema(){
        List<Metadata> metadataList = new ArrayList<>();
        metadataList.addAll(getDefaultTileMetadata());

//        for (Node node : rightPane.getChildren()) {
//            CustomElement customElm = (CustomElement) node;
//            Metadata elementMetaData = customElm.getMetaData();
//            metadataList.add(elementMetaData);
//        }

        return new SimpleObjectSchema(this.name, metadataList);
    }

    private Collection<? extends Metadata> getDefaultTileMetadata() {
        List<Metadata> metadataList = new ArrayList<>();
        //metadataList.add(new StringMetadata("customTile")) //Should there be some sort of type Metadata
        metadataList.add(new PositionMetadata("position"));
        metadataList.add(new BooleanMetadata("owned")); //Is there a way to set viewable/editable?
        DoubleMetadata widthMetaData = new DoubleMetadata("width");
        DoubleMetadata heightMetaData = new DoubleMetadata("height");
        widthMetaData.setDefaultValue(50);
        heightMetaData.setDefaultValue(50);
        metadataList.add(widthMetaData);
        metadataList.add(heightMetaData);
        metadataList.add(new TileListMetadata("next"));
        return metadataList;
    }


    public void launch() {
        launch(new String[]{});
    }

    public static void main(String[] args) {
        launch(args);
    }


    //remove inner class?
    private class LeftPane extends VBox {
        private LeftPane() {
            this.setMinWidth(LEFT_PANE_WIDTH);
            this.setMaxWidth(LEFT_PANE_WIDTH);

            AddButtons();

            // Listen for image selection changes
            rightPane.setOnMouseClicked(event -> {
                if (event.getTarget() == rightPane) {
                    getChildren().removeAll(currentClickedInfo);
                }
            });
        }

        private void AddButtons() {
            Button renameButton = new Button("Name This Custom Object");
            renameButton.setOnAction(e -> nameObject());

            Button saveButton = new Button("Save Custom Object");
            saveButton.setOnAction(e -> saveCustomObject());

            Button loadButton = new Button("Load Custom Object");
            loadButton.setOnAction(e -> loadJson());

            Button addImageButton = new Button("Add Image");
            addImageButton.setOnAction(e -> addImage());

            Button addTextButton = new Button("Add Text");
            addTextButton.setOnAction(e -> addText());

            // Add image property fields to VBox
            this.getChildren().addAll(renameButton, saveButton, loadButton, addTextButton, addImageButton);
        }

        private void nameObject() {
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


        private void addImage() {
            CustomImage newImage = getImageFromFile();
            newImage.fitImage(rightPane.getWidth(), rightPane.getHeight());
            placeInLeftPane(newImage);
        }

        private CustomImage getImageFromFile() {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Image File");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
            File selectedFile = fileChooser.showOpenDialog(getScene().getWindow());

            // Create an ImageView from the image file
            CustomImage newImage = new CustomImage(selectedFile);
            return newImage;
        }

        private void addText() {
            CustomText newText = new CustomText("Default Text", "Enter Text Here", 14, false);
            makeDraggable(newText);
            placeInLeftPane(newText);
        }


        private void saveCustomObject(){
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
    }
}

