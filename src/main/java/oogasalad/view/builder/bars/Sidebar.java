package oogasalad.view.builder.bars;

import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import oogasalad.view.builder.BuilderUtility;
import oogasalad.view.builder.BuilderView;
import oogasalad.view.builder.ResourceIterator;

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
public class Sidebar extends AbstractBar implements BuilderUtility {

  private Pane myPane;
  private String myCurrentBundleName;

  public Sidebar(ResourceBundle languageResource, String id, BuilderView builder) {
    // create pane
    super(languageResource, id, builder);
    ScrollPane scrollablePane = new ScrollPane();
    myPane = (Pane) makeVBox(id);
    scrollablePane.setContent(myPane);
  }

  @Override
  public void addItems(String functionFileName) {
    myCurrentBundleName = functionFileName;
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
    setLanguage(getResource(fileName));
    refreshItems(myCurrentBundleName);
  }

  @Override
  public Node asNode() {
    return myPane;
  }

  private void createButton(String key, String buttonClickMethodName) {
    Node button = makeButton(key, getLanguage(),
        e -> runMethodFromString(buttonClickMethodName));
    myPane.getChildren().add(button);
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
    getBuilder().toggleTileCreation();
  }

  private void deleteToggle() {
    getBuilder().toggleTileDeletion();
  }

  private void cancelAction() {
    getBuilder().cancelAction();
  }
}
