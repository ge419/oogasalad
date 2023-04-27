package oogasalad.view.builder.bars;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import oogasalad.view.builder.BuilderUtility;
import oogasalad.view.builder.BuilderView;

/**
 * <p>Implementation of Abstract Bar for a menubar specifically.</p>
 * <p>Once created, this class will automatically create menus and their
 * items based off of a default menubar resource file titled MenubarOptions.</p>
 * <p>For any menu added to this pane, there <em>must</em> be an accompanying
 * properties file that dictates what methods go to each of it's button options.</p>
 *
 * @author tmh85
 */
public class BuilderMenubar extends AbstractBar implements BuilderUtility {

  private static final String RESOURCE_FILE_WITH_MENUBAR_OPTIONS = "MenubarOptions";
  private final MenuBar myMenuBar;
  private final ResourceBundle myMenuOptionsResource;
  private final Map<String, Menu> myAddedMenus;

  public BuilderMenubar(ResourceBundle languageResource, String id,
      BuilderView builder) {
    super(languageResource, id, builder);
    myMenuOptionsResource = getResource(RESOURCE_FILE_WITH_MENUBAR_OPTIONS);
    myAddedMenus = new HashMap<>();

    myMenuBar = new MenuBar();
    myMenuBar.setId(id);
    createDefaultMenuOptions();
  }

  @Override
  public void addItems(String functionFileName) {
    ResourceBundle bundle = getResource(functionFileName);
    Menu newMenu = createMenu(functionFileName);
    myAddedMenus.put(functionFileName, newMenu);
  }

  /**
   * @see AbstractBar#addItems(String)
   */
  public void addItems(Menu item) {
    myMenuBar.getMenus().add(item);
    myAddedMenus.put(item.getId(), item);
  }

  @Override
  public void refreshItems(String newFunctionFileName) {
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
   */
  public void refreshItems() {
    myMenuBar.getMenus().clear();
    createDefaultMenuOptions();
    for (String itemID : myAddedMenus.keySet()) {
      Menu item = myAddedMenus.get(itemID);
      myAddedMenus.remove(itemID);
      addItems(item);
    }
  }

  @Override
  public void updateLanguage(String fileName) {
    setLanguage(getResource(fileName));
    refreshItems();
  }

  @Override
  public Node asNode() {
    return myMenuBar;
  }

  private Menu createMenu(String optionName) {
    Menu newOption = new Menu(getLanguage().getString(optionName));
    newOption.setId(optionName);
    createItemsInMenu(newOption, optionName);
    myMenuBar.getMenus().add(newOption);

    return newOption;
  }

  private void createItemsInMenu(Menu desiredMenu, String menuName) {
    ResourceBundle menuBundle = getResource(menuName);
    forEachResourceKey(menuBundle,
        key -> addNewMenuItemToMenu(key, menuBundle.getString(key), desiredMenu));

  }

  private void addNewMenuItemToMenu(String key, String buttonClickMethodName, Menu parentMenu) {
    MenuItem item = makeMenuItem(key, getLanguage(),
        e -> runMethodFromString(buttonClickMethodName));
    parentMenu.getItems().add(item);
  }

  private void createDefaultMenuOptions() {
    forEachResourceKey(myMenuOptionsResource, key -> createMenu(key));
  }

  //////////////////////////////////////////////////////////////////////////////

  private void saveFile() {
    getBuilder().saveFile();
  }

  private void loadFile() {
    getBuilder().loadFile();
  }

  private void toggleGuidelines() {
    getBuilder().toggleGuidelines();
  }

  private void toggleDraggables() {
    getBuilder().toggleDraggables();
  }
}
