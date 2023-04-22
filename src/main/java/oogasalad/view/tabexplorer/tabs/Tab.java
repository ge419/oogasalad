package oogasalad.view.tabexplorer.tabs;

/**
 * This interface represents a tab in the application, which can be displayed in a tabbed interface.
 * Any class that implements this interface must implement the {@code renderTabContent()} method,
 * which will be called to render the content of the tab.
 *
 * @author Chika Dueke-Eze
 */
public interface Tab {

  /**
   * This method is called to render the content of the tab. Any implementing class should use this
   * method to create and add the necessary UI components to display the content of the tab.
   */
  void renderTabContent();
}