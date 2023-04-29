package oogasalad.view;

import oogasalad.controller.GameController;
import oogasalad.view.gameplay.Gameview;

public interface ViewFactory {

  Gameview makeGameview(GameController controller);
}
