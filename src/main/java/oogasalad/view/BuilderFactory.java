package oogasalad.view;

import oogasalad.controller.BuilderController;
import oogasalad.view.builder.BuilderView;

public interface BuilderFactory {

  BuilderView makeBuilder(String language, BuilderController controller);
}
