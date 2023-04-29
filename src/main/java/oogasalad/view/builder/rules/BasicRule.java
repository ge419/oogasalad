package oogasalad.view.builder.rules;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import oogasalad.model.engine.rules.Rule;

/**
 * <p>Basic implementation of the ViewableRule interface.</p>
 * <p>This creates a pane with an element on the left (window)
 * and an element on the right (description).</p>
 *
 * @author tmh85
 */
public class BasicRule extends BorderPane implements ViewableRule{

  private Rule myRule;
  private Pane myWindowPane;
  private Pane myDescriptionWindow;
  private BorderPane myPane;

  public BasicRule(Rule rule){
    myRule = rule;
    myPane = new BorderPane();
    myWindowPane = new Pane();
    myDescriptionWindow = new Pane();
    myPane.setLeft(myWindowPane);
    myPane.setCenter(myDescriptionWindow);
  }

  @Override
  public Node asNode() {
    return this;
  }

  @Override
  public void addWindow(String item) {
    myWindowPane.getChildren().clear();
    myWindowPane.getChildren().add(new Text(item));
  }

  @Override
  public void addDescription(String description) {
    myDescriptionWindow.getChildren().clear();
    myDescriptionWindow.getChildren().add(new Text(description));
  }

  @Override
  public Rule getRule() {
    return myRule;
  }
}
