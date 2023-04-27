package oogasalad.view.builder.bars;

import java.util.ResourceBundle;
import oogasalad.view.builder.BuilderView;
import oogasalad.view.builder.ResourceIterator;

/**
 * <p>Bar interface lists methods related to adding and refreshing toggleable
 * items on some sort of UI element.</p>
 *
 * <p>This interface (and the bars in general) were created to fix the issue
 * of duplicated code between button creation and menu item creation for the builder view. The code
 * was duplicated because menu item does not inherit any JavaFX elements despite functioning as one,
 * so more abstraction, such as this interface and it's implementing classes, were required.</p>
 *
 * @author tmh85
 */
public abstract class AbstractBar implements ResourceIterator {

  private BuilderView myBuilder;
  public AbstractBar(String fileName, String id, BuilderView builder){
    //todo: Figure out how to set the resourcebundle
    //setLanguage(fileName);
    myBuilder = builder;
  }

  /**
   * <p>Add items to the bar as dictated by the required resource file.</p>
   * <p>Note that the keys listed in the resource file need to exist in
   * the bars' language file and also have a valid method attached to it as a value.</p>
   *
   * @param functionFileName name of file that contains the function connected to an item
   */
  abstract public void addItems(String functionFileName);

  /**
   * <p>Replace the items on a given bar with new items from a given resource file.</p>
   *
   * @param newFunctionFileName name of file that contains the function connected
   */
  abstract public void refreshItems(String newFunctionFileName);

  /**
   * <p>Update what text is displayed on the bar.</p>
   * <p>Note that this links to some sort of file that contains what will be displayed
   * on the items, so make sure this item exists!</p>
   *
   * @param fileName name of the desired file as a string
   */
  abstract public void updateLanguage(String fileName);
}
