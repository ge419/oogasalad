package oogasalad.view.builder.itempanes;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oogasalad.model.exception.ResourceReadException;
import oogasalad.view.builder.BuilderUtility;
import oogasalad.view.builder.BuilderView;
import oogasalad.view.builder.exceptions.MethodReflectionException;

/**
 * <p>Implementation of Abstract Bar for a menubar specifically.</p>
 * <p>Once created, this class will automatically create menus and their
 * items based off of a default menubar resource file titled MenubarOptions.</p>
 * <p>For any menu added to this pane, there <em>must</em> be an accompanying
 * properties file that dictates what methods go to each of it's button options.</p>
 *
 * @author tmh85
 */
public class MenuItemPane extends AbstractItemPane implements BuilderUtility {

  private static final String RESOURCE_FILE_WITH_MENUBAR_OPTIONS = "MenubarOptions";
  private static final String IMAGE_PATH = "boi.jpg";
  private static final double DEFAULT_IMAGE_SIZE = 200;
  private final MenuBar myMenuBar;
  private final ResourceBundle myMenuOptionsResource;
  private final Map<String, Menu> myAddedMenus;

  /**
   * @see AbstractItemPane#AbstractItemPane(ResourceBundle, String, BuilderView)
   */
  public MenuItemPane(ResourceBundle languageResource, String id,
      BuilderView builder) throws ResourceReadException{
    super(languageResource, id, builder);
    myMenuOptionsResource = getResource(RESOURCE_FILE_WITH_MENUBAR_OPTIONS);
    myAddedMenus = new HashMap<>();

    myMenuBar = new MenuBar();
    myMenuBar.setId(id);
    createDefaultMenuOptions();
  }

  @Override
  public void addItems(String functionFileName) throws ResourceReadException{
    ResourceBundle bundle = getResource(functionFileName);
    Menu newMenu = createMenu(functionFileName);
    myAddedMenus.put(functionFileName, newMenu);
  }

  /**
   * @see AbstractItemPane#addItems(String)
   */
  public void addItems(Menu item) {
    myMenuBar.getMenus().add(item);
    myAddedMenus.put(item.getId(), item);
  }

  @Override
  public void refreshItems(String newFunctionFileName) throws ResourceReadException {
    //Menu getMenu = myMenuBar.lookup(newFunctionFileName);
    Menu getMenu = myAddedMenus.get(newFunctionFileName);
    myMenuBar.getMenus().remove(getMenu);
    boolean isAdded = false;
    if (myAddedMenus.containsKey(newFunctionFileName)) {
      myAddedMenus.remove(newFunctionFileName);
      isAdded = true;
    }
    Menu newMenu = createMenu(newFunctionFileName);
    if (isAdded) {
      myAddedMenus.put(newFunctionFileName, newMenu);
    }
  }

  /**
   * <p>Refreshes all current items in the menu.</p>
   * @see MenuItemPane#refreshItems(String)
   */
  public void refreshItems() throws ResourceReadException {
    myMenuBar.getMenus().clear();
    createDefaultMenuOptions();
    for (String itemID : myAddedMenus.keySet()) {
      Menu item = myAddedMenus.get(itemID);
      myAddedMenus.remove(itemID);
      addItems(item);
    }
  }

  @Override
  public void updateLanguage(String fileName) throws ResourceReadException {
    setLanguage(getResource(fileName));
    refreshItems();
  }

  @Override
  public Node asNode() {
    return myMenuBar;
  }

  /**
   * <p>Creates a menu object and any sub menuitems that compose it.</p>
   * @param optionName name of the menu you want to create
   * @return created menu
   */
  private Menu createMenu(String optionName) throws ResourceReadException{
    Menu newOption = new Menu(getLanguage().getString(optionName));
    newOption.setId(optionName);
    createItemsInMenu(newOption);
    myMenuBar.getMenus().add(newOption);

    return newOption;
  }

  /**
   * <p>Creates the MenuItems for a desired menu.</p>
   * @param desiredMenu
   */
  private void createItemsInMenu(Menu desiredMenu) {
    ResourceBundle menuBundle = getResource(desiredMenu.getId());
    try {
      forEachResourceKey(menuBundle,
          key -> addNewMenuItemToMenu(key, menuBundle.getString(key), desiredMenu));
    }
    catch (RuntimeException e){
      throw new ResourceReadException(displayMessageWithArguments(
          getLanguage(),
          "ResourceReadException",
          menuBundle.getBaseBundleName()
      ));
    }

  }

  /**
   * <p>Uses reflection to create a menu item from a string and adds it to the
   * parent.</p>
   * @param key
   * @param buttonClickMethodName
   * @param parentMenu
   */
  private void addNewMenuItemToMenu(String key, String buttonClickMethodName, Menu parentMenu) {
    try {
      MenuItem item = makeMenuItem(key, getLanguage(),
          e -> runMethodFromString(buttonClickMethodName));
      parentMenu.getItems().add(item);
    }
    catch(RuntimeException e){
      throw new MethodReflectionException(displayMessageWithArguments(
          getLanguage(),
          "ReflectionMethodError",
          buttonClickMethodName
      ));
    }
  }

  /**
   * <p>Creates all the menus and their items as listed in the
   * default properties file.</p>
   */
  private void createDefaultMenuOptions(){
    try {
      forEachResourceKey(myMenuOptionsResource, key -> createMenu(key));
    }
    catch(Exception e){
      throw new ResourceReadException(displayMessageWithArguments(
          getLanguage(),
          "ResourceReadError",
          myMenuOptionsResource.getBaseBundleName()
      ));
    }
  }

  //////////////////////////////////////////////////////////////////////////////

  private void saveFile() {
    getBuilder().saveFile();
  }

  private void toggleGuidelines() {
    getBuilder().toggleGuidelines();
  }

  private void toggleDraggables() {
    getBuilder().toggleDraggables();
  }
  private void test() {
  }

  private void aboutWindow(){
    getBuilder().displayAboutWindow();
  }
}
