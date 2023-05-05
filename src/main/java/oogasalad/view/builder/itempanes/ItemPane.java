package oogasalad.view.builder.itempanes;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import oogasalad.model.exception.ResourceReadException;
import oogasalad.view.builder.BuilderUtility;
import oogasalad.view.builder.BuilderView;
import oogasalad.view.builder.customTile.CustomTileBuilder;
import oogasalad.view.builder.exceptions.MethodReflectionException;

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
public class ItemPane extends AbstractItemPane implements BuilderUtility {

  private final Pane myPane;
  private String myCurrentBundleName;

  /**
   * @see AbstractItemPane#AbstractItemPane(ResourceBundle, String, BuilderView)
   */
  public ItemPane(ResourceBundle languageResource, String id, BuilderView builder) {
    // create pane
    super(languageResource, id, builder);
//    ScrollPane scrollablePane = new ScrollPane();
    myPane = (Pane) makeVBox(id);
//    scrollablePane.setContent(myPane);
  }

  @Override
  public void addItems(String functionFileName) throws MethodReflectionException {
    myCurrentBundleName = functionFileName;
    ResourceBundle bundle = getResource(functionFileName);
    try {
      forEachResourceKey(bundle,
          key -> createButton(key, bundle.getString(key)));
    } catch (Exception e) {
      throw new ResourceReadException(displayMessageWithArguments(
          getLanguage(),
          "ResourceReadError",
          bundle.getBaseBundleName()
      ));
    }
  }

  @Override
  public void refreshItems(String newFunctionFileName)
      throws MethodReflectionException, ResourceReadException {
    myPane.getChildren().clear();
    addItems(newFunctionFileName);
  }

  @Override
  public void updateLanguage(String fileName)
      throws MethodReflectionException, ResourceReadException {
    setLanguage(getResource(fileName));
    refreshItems(myCurrentBundleName);
  }

  @Override
  public Node asNode() {
    return myPane;
  }

  /**
   * <p>Reflectively create a button and add it to a pane</p>
   *
   * @param key                   resource file key
   * @param buttonClickMethodName method name as a string
   */
  private void createButton(String key, String buttonClickMethodName) {
    try {
      Node button = makeButton(key, getLanguage(),
          e -> runMethodFromString(buttonClickMethodName));
      myPane.getChildren().add(button);
    } catch (RuntimeException e) {
      throw new MethodReflectionException(displayMessageWithArguments(
          getLanguage(),
          "ReflectionMethodError",
          buttonClickMethodName
      ));
    }
  }

  private void test() {
    // nothing
    System.out.println("why that button sure did do nothing!");
  }

  private void openTileMenu() {
    refreshItems("TileSideMenu");
  }

  private void backToSidebarMenu() {
    refreshItems("SideBar1");
  }

  private void backFromRules() {
    backToSidebarMenu();
    getBuilder().switchToBoard();
  }

  private void rulesMenu() {
    refreshItems("RulesSideMenu");
    getBuilder().switchToRules();
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

  private void displayGameInfoForm() {
    myPane.getChildren().clear();
    getBuilder().displayGameInfoForm(myPane);
    addItems("GameInfoMenu");
  }

  private void saveGameInfo() {
    getBuilder().saveGameInfo();
  }

  private void uploadThumbnailImage() {
    getBuilder().uploadThumbnailImage();
  }

  private void createCustomTile() {
    getBuilder().cancelAction();
    new CustomTileBuilder().start(null);
  }

  private void toggleNextRemoval() {
    getBuilder().toggleNextRemoval();
  }

  private void uploadImage() throws IOException {
    getBuilder().uploadImage();
  }
}
