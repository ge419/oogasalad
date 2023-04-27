package oogasalad.view.builder;

import java.util.Optional;
import oogasalad.view.builder.gameholder.ImmutableGameHolder;

public interface BuilderAPI {

  @Deprecated
  Optional<ImmutableGameHolder> saveFile();
  void loadFile();
  void toggleTileDeletion();
  void toggleTileCreation();
  void toggleDraggables();
  void updateInfoText(String key);
  void cancelAction();


}
