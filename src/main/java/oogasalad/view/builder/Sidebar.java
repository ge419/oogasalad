package oogasalad.view.builder;

import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public class Sidebar implements Bar, BuilderUtility, ResourceIterator{

  private static final String BASE_RESOURCE_PACKAGE = "view.builder.";

  private ResourceBundle myLanguageResource;
  private Pane myPane;

  public Sidebar(ResourceBundle languageResource, String id){
    // create pane
    myLanguageResource = languageResource;
    ScrollPane scrollablePane = new ScrollPane();
    myPane = (Pane) makeVBox(id);
    scrollablePane.setContent(myPane);
    // load initial buttons based on what is given
  }

  @Override
  public void addItems(ResourceBundle resourceFunctionHolder){
    forEachResourceKey(resourceFunctionHolder, key -> createButton(key, resourceFunctionHolder.getString(key)));
  }

  @Override
  public void refreshItems(ResourceBundle newResourceFunctionHolder){
    myPane.getChildren().clear();
    addItems(newResourceFunctionHolder);
  }

  private void createButton(String key, String buttonClickMethodName){
    Node button = makeButton(key, myLanguageResource, e -> reflectivelyRunMethod(buttonClickMethodName));
    myPane.getChildren().add(button);
  }

  private void reflectivelyRunMethod(String method){
    try{
      Sidebar.this.getClass().getDeclaredMethod(method)
          .invoke(Sidebar.this);
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new RuntimeException(ex);
    }
  }

  // loadButtons(ResourceBundle bundle)

  // asNode();
}
