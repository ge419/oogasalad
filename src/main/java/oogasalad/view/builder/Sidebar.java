package oogasalad.view.builder;

import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

/**
 * <p>Sidebar will implement and manage a pane of buttons for any UI.</p>
 *
 * <p>This class will create it's buttons based off of property files that
 * dictate what each button's functionality is by listing it's method.</p>
 *
 * <p>The actual name of each button displayed is controlled by the languageResource,
 * which is a required constructor argument.</p>
 *
 * @author tmh85
 */
public class Sidebar implements Bar, BuilderUtility, ResourceIterator {

  private static final String BASE_RESOURCE_PACKAGE = "view.builder.";

  private ResourceBundle myLanguageResource;
  private Pane myPane;
  private String myCurrentBundleName;
  private BuilderView myBuilder;

  public Sidebar(ResourceBundle languageResource, String id, BuilderView builder) {
    // create pane
    myLanguageResource = languageResource;
    myBuilder = builder;
    ScrollPane scrollablePane = new ScrollPane();
    myPane = (Pane) makeVBox(id);
    scrollablePane.setContent(myPane);
    // load initial buttons based on what is given
  }

  @Override
  public void addItems(String functionFileName) {
    ResourceBundle bundle = getResource(functionFileName);
    forEachResourceKey(bundle,
        key -> createButton(key, bundle.getString(key)));
  }

  @Override
  public void refreshItems(String newFunctionFileName) {
    myPane.getChildren().clear();
    addItems(newFunctionFileName);
  }

  @Override
  public void updateLanguage(String fileName) {
    myLanguageResource = getResource(fileName);
    refreshItems(myCurrentBundleName);
  }

  public Node asNode() {
    return myPane;
  }

  private void createButton(String key, String buttonClickMethodName) {
    Node button = makeButton(key, myLanguageResource,
        e -> runMethodFromString(buttonClickMethodName));
    myPane.getChildren().add(button);
  }

  private void runMethodFromString(String method) {
    try {
      Sidebar.this.getClass().getDeclaredMethod(method)
          .invoke(Sidebar.this);
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new RuntimeException(ex);
    }
  }

  private void test() {
    // nothing
    System.out.println("why that button sure did do nothing!");
  }

  private void openTileMenu() {
    refreshItems("TileMenu");
  }

  private void backToSidebarMenu() {
    refreshItems("SideBar1");
  }

  private void toggleTileCreation() {
    myBuilder.toggleTileCreation();
  }

  private void deleteToggle() {
    myBuilder.toggleTileDeletion();
  }

  private ResourceBundle getResource(String resourceName) {
    return ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + resourceName);
  }
}
