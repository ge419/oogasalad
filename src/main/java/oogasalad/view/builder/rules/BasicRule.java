package oogasalad.view.builder.rules;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import oogasalad.model.engine.rules.Rule;

/**
 *
 */
public class BasicRule extends BorderPane implements ViewableRule{

  private Rule myRule;
  private Node myWindow;
  private Text myDescription;

  public BasicRule(Rule rule){
    myRule = rule;
  }

  @Override
  public Node asNode() {
    return this;
  }

  @Override
  public void addWindow(String item) {

  }

  @Override
  public void addDescription(String description) {

  }

  @Override
  public Rule getRule() {
    return null;
  }
}
