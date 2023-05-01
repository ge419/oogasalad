package oogasalad.view.builder.customTile;

import com.google.gson.*;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class CustomTileBuilderView extends StackPane {
    private final Path RESOURCE_PATH = Paths.get("data/customObjects");
    private final Runnable swapCurrentClicked;
    private final ResourceBundle languageBundle;
    private String name;
    VBox currentClickedInfo;
    VBox newCurrentClickedInfo;

    public CustomTileBuilderView(Runnable swapCurrentClicked, ResourceBundle languageBundle){
        this.swapCurrentClicked = swapCurrentClicked;
        this.languageBundle = languageBundle;
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
        dialog.setTitle(languageBundle.getString("nametitle"));
        dialog.setHeaderText(languageBundle.getString("nametext"));
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newName -> {
            ((Stage) getScene().getWindow()).setTitle(newName);
            name = newName;
        });
    }

    void saveLauncher(){
        if (name == null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "noNameWarning");
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
                    loadedObject.getMetaData();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private File chooseJsonFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(languageBundle.getString("jsonFileChooser"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        return fileChooser.showOpenDialog(null);
    }


    private JsonObject readJsonFromFile(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        }
    }

    private void resizeWindow(Double height, Double rightPaneWidth) {
        Stage stage = (Stage) this.getScene().getWindow();

        stage.setWidth(CustomTileBuilder.getLeftPaneWidth() + rightPaneWidth);
        stage.setHeight(height);
    }

    //PROTECTED METHODS FOR TESTING
    protected String getName(){
        return this.name;
    }

//    protected String getStageName(){
//        return this.getScene().getWindow().getTitle();
//    }

}
