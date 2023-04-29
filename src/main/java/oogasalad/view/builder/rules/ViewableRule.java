package oogasalad.view.builder.rules;

import oogasalad.model.engine.rules.Rule;
import oogasalad.view.Nodeable;

/**
 * <p>ViewableRule contains the basic methods required to display a rule on the Builder.</p>
 *
 * @author tmh85
 */
public interface ViewableRule extends Nodeable {

  /**
   * <p>Adds an item to the window of a pane.</p>
   *
   * @param item what you are adding to the window
   */
  void addWindow(String item);

  /**
   * <p>Adds a description to the viewable window.</p>
   * @param description description to add
   */
  void addDescription(String description);

  /**
   * <p>Returns the backend rule encompassing the frontend rules.</p>
   * @return backend rule
   */
  Rule getRule();
}
