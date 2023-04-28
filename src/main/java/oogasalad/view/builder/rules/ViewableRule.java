package oogasalad.view.builder.rules;

import oogasalad.model.engine.rules.Rule;
import oogasalad.view.Nodeable;

public interface ViewableRule extends Nodeable {
  void addWindow(String info);
  void addDescription(String description);
  Rule getRule();
}
