package oogasalad.view.builder;

public interface BuilderAPI {

//  @Deprecated
//  Optional<ImmutableGameHolder> saveFile();

  void saveFile();

  void toggleTileDeletion();

  void toggleTileCreation();

  void toggleGuidelines();

  void toggleDraggables();

  void updateInfoText(String key);

  void cancelAction();

}
