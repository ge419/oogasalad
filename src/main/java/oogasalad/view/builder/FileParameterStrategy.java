package oogasalad.view.builder;

import java.io.File;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.text.Text;

class FileParameterStrategy implements ParameterStrategy, BuilderUtility {

  private Optional<File> file;

  public FileParameterStrategy() {
  }

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
}