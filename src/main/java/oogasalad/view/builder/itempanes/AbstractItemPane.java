package oogasalad.view.builder.itempanes;

import java.util.ResourceBundle;
import javafx.scene.Node;
import oogasalad.view.builder.BuilderView;
import oogasalad.view.builder.ReflectiveMethodGetter;

/**
 * <p>AbstractItemPane lists methods related to adding and refreshing toggleable
 * items on some sort of UI element.</p>
 *
 * <p>ItemPanes were created to fix the issue of duplicated code between button
 * and menu item creation for the builder view. The code was duplicated because menus do not inherit
 * any JavaFX elements despite functioning as one, so more abstraction, such as this abstract class
 * and it's implementing subclasses, were required.</p>
 *
 * @author tmh85
 */
public abstract class AbstractItemPane implements ReflectiveMethodGetter {

  private static final String BASE_RESOURCE_PACKAGE = "view.builder.";

  private final BuilderView myBuilder;
  private ResourceBundle myLanguageResource;

  /**
   * @param languageResource ResourceBundle that contains the text that will be displayed on the
   *                         pane and each of its items.
   * @param id               ID that you wish to give the ItemPane
   * @param builder          Each ItemPane needs to be connected to a Builder so that items can
   *                         refer to its public methods.
   */
  public AbstractItemPane(ResourceBundle languageResource, String id, BuilderView builder) {
    myLanguageResource = languageResource;
    myBuilder = builder;
  }

  /**
   * <p>Add items to the itempane as dictated by the required resource file.</p>
   * <p>Note that the keys listed in the resource file need to exist in
   * the bars' language file and also have a valid method attached to it as a value.</p>
   *
   * @param functionFileName name of file that contains the function connected to an item
   */
  abstract public void addItems(String functionFileName);

  /**
   * <p>Replace the items on a given itempane with new items from a given resource file.</p>
   *
   * @param newFunctionFileName name of file that contains the function connected
   */
  abstract public void refreshItems(String newFunctionFileName);

  /**
   * <p>Update what text is displayed on the itempane.</p>
   * <p>Note that this links to some sort of file that contains what will be displayed
   * on the items, so make sure this item exists!</p>
   *
   * @param fileName name of the desired file as a string
   */
  abstract public void updateLanguage(String fileName);

  /**
   * Returns the node component of the itempane.
   *
   * @return node
   */
  abstract public Node asNode();

  /**
   * <p>Basic getter for the ResourceBundle that contains the language</p>
   *
   * @return ResourceBundle currently used for the language of the itempane
   */
  protected ResourceBundle getLanguage() {
    return myLanguageResource;
  }

  /**
   * <p>Set the resource file used for the items.</p>
   *
   * @param resourceFile desired resource file
   */
  protected void setLanguage(ResourceBundle resourceFile) {
    myLanguageResource = resourceFile;
  }

  /**
   * <p>Basic getter for the Builder connected to this itempane.</p>
   *
   * @return Builder
   */
  protected BuilderView getBuilder() {
    return myBuilder;
  }

  /**
   * <p>Converts a string to a ResourceBundle assuming that the bundle is in
   * the Properties.view.builder folder.</p>
   *
   * @param resourceName name of the resource file you want to load
   * @return desired ResourceBundle
   */
  protected ResourceBundle getResource(String resourceName) {
    return ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + resourceName);
  }
}
