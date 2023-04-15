package oogasalad.view.builder.popupform;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.text.Text;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.Metadata;
import oogasalad.view.builder.BuilderUtility;

import java.io.File;
import java.util.Optional;
import java.util.ResourceBundle;

class FileParameterStrategy implements ParameterStrategy, BuilderUtility {
    public FileParameterStrategy(){}
    private Optional<File> file;
    @Override
    public Node renderInput(String name, ResourceBundle resourceBundle) {
        Node textLabel = new Text(name);
        Node element = makeButton("UploadFileTitle", resourceBundle, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                file = fileLoad(resourceBundle, "UploadFileTitle");
            }
        });
        return makeHBox(String.format("%sFileInput", name), textLabel, element);
    }
    @Override
    public File getValue() {
        return file.get();
    }

    @Override
    public boolean validateInput(Metadata metadata) {
        return getValue().getClass().equals(File.class);
    }

    @Override
    public void setValue(Attribute attribute) {
        //FileAttribute.from(attribute).setValue(getValue());
    }
}