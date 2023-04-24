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

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CustomTileMaker extends Application {

    private String defaultTitle = "CustomTileMaker";

    private Stage stage;
    private LeftPane leftPane;
    private StackPane rightPane;

    private final int LEFT_PANE_WIDTH = 250;
    private int RIGHT_PANE_STARTING_WIDTH = 500;
    private int STARTING_HEIGHT = 500;

    private Path RESOURCE_PATH = Paths.get("data/customObjects");

    private String name;



    @Override
    public void start(Stage PrimaryStage) {
        // Create StackPane for the right pane
        rightPane = new StackPane();

        stage = PrimaryStage;
        // Create UI elements for the left and right panes

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            try {
                this.save(RESOURCE_PATH);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        Button loadButton = new Button("Load Custom Object");
        loadButton.setOnAction(e -> loadJson());

        // Create leftPane
        leftPane = new LeftPane(this, rightPane);
        leftPane.getChildren().addAll(saveButton, loadButton);
        leftPane.setMinWidth(LEFT_PANE_WIDTH);
        leftPane.setMaxWidth(LEFT_PANE_WIDTH);

        // Create SplitPane and add left and right panes
        SplitPane splitPane = new SplitPane();
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

        // Create scene and add SplitPane
        Scene scene = new Scene(splitPane, LEFT_PANE_WIDTH+RIGHT_PANE_STARTING_WIDTH, STARTING_HEIGHT);

        // Set the scene and show the stage
        stage.setScene(scene);
        stage.show();

        // Set listener for changes to window size
        stage.widthProperty().addListener((obs, oldVal, newVal) -> maintainSideBar());
        stage.titleProperty().addListener((obs, oldVal, newVal) -> name = newVal);
    }

    private void loadJson() {
        this.rightPane.getChildren().clear();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

                resizeWindow(jsonObject.get("height").getAsDouble(),jsonObject.get("width").getAsDouble());
                stage.setTitle(jsonObject.get("name").getAsString());

                JsonArray customObjects = jsonObject.getAsJsonArray("customObjects");

                for (JsonElement jsonElement : customObjects) {
                    JsonObject customObject = jsonElement.getAsJsonObject();
                    CustomObject loadedObject = CustomObject.load(customObject);
                    this.leftPane.placeInPane(loadedObject);
                    loadedObject.setLocation();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public StackPane loadJsonToTile(JsonObject jsonObject){
        StackPane tile = new StackPane();
        tile.setLayoutY(jsonObject.get("height").getAsDouble());
        tile.setLayoutX(jsonObject.get("width").getAsDouble());

        JsonArray customObjects = jsonObject.getAsJsonArray("customObjects");
        for (JsonElement jsonElement : customObjects) {
            JsonObject customObject = jsonElement.getAsJsonObject();
            CustomObject loadedObject = CustomObject.load(customObject);
            tile.getChildren().add(customObject.get("index").getAsInt(), (Node) loadedObject);
            loadedObject.setLocation();
        }
        return tile;
    }


    private void resizeWindow(Double height, Double rightPaneWidth) {
        stage.setWidth(LEFT_PANE_WIDTH + rightPaneWidth);
        stage.setHeight(height);
    }

    private void maintainSideBar() {
        double height = stage.getHeight();
        double width = stage.getWidth() - LEFT_PANE_WIDTH;
    }

    public void save(Path path) throws IOException {
        System.out.println("path = " + path);
        System.out.println("name = " + name);
        Path directory = path.resolve(this.name);

        // Create a JSON object to represent the custom tile
        JsonObject customTileObject = new JsonObject();
        customTileObject.addProperty("name", this.name);
        customTileObject.addProperty("width", this.rightPane.getWidth());
        customTileObject.addProperty("height", this.rightPane.getHeight());


        // Create a JSON array to store the custom objects
        JsonArray customObjectsArray = new JsonArray();

        // Loop through each object in the right pane and add its data to the array
        for (Node node : rightPane.getChildren()) {
            CustomObject customObject = (CustomObject) node;
            JsonObject objectJson = customObject.save(directory);
            customObjectsArray.add(objectJson);
        }

        customTileObject.add("customObjects", customObjectsArray);

        // Create a Gson object to convert the JSON to a string
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if (!Files.exists(directory)) {
            Files.createDirectories(directory); // create the directory if it does not exist
        }

        Path file = directory.resolve(this.name + ".json");
        if (!Files.exists(file)) {
            Files.createFile(file);
        }
        // Write the JSON to the specified file
        try (FileWriter writer = new FileWriter(file.toFile())) {
            writer.write(gson.toJson(customTileObject));
        }
    }



    public void launch() {
        launch(new String[]{});
    }

    public static void main(String[] args) {
        launch(args);
    }

}

