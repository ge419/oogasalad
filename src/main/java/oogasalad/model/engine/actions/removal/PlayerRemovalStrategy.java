package oogasalad.model.engine.actions.removal;

import java.util.List;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Player;

public interface PlayerRemovalStrategy {
  List<Player> removePlayers(GameHolder gameHolder, double scoreMinBound);
}
