package oogasalad.view.builder;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.ResourceBundle;

class ImageParameterStrategy implements ParameterStrategy, BuilderUtility {
    public ImageParameterStrategy(){}
    private Image image;
    @Override
    public Node renderInput(String name, ResourceBundle resourceBundle) {
        Node textLabel = new Text(name);
        Node element = makeButton("UploadFileTitle", resourceBundle, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Optional<File> file = loadFile(resourceBundle, "UploadFileTitle");
                try {
                    image = new Image(new FileInputStream(file.get().getPath()));
                } catch (FileNotFoundException e) {
                    System.out.println("File for image not found");
                }
            }
        });
        return makeHBox(String.format("%sImageInput", name), textLabel, element);
    }
    @Override
    public Image getValue() {
        return image;
    }
}
