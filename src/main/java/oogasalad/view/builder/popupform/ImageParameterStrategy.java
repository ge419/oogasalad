package oogasalad.view.builder.popupform;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.Metadata;
import oogasalad.view.builder.BuilderUtility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.ResourceBundle;

class ImageParameterStrategy implements ParameterStrategy, BuilderUtility {
    private Image image;
    public ImageParameterStrategy(){}
    @Override
    public Node renderInput(String name, ResourceBundle resourceBundle) {
        Node textLabel = new Text(name);
        Node element = makeButton("UploadFileTitle", resourceBundle, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Optional<File> file = fileLoad(resourceBundle, "UploadFileTitle");
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

    @Override
    public boolean validateInput(Metadata metadata) {
        return getValue().getClass().equals(Image.class);
    }

    @Override
    public void setValue(Attribute attribute) {
        //ImageAttribute.from(attribute).setValue(getValue());
    }
}
