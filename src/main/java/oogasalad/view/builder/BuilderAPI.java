package oogasalad.view.builder;

import oogasalad.view.builder.gameholder.ImmutableGameHolder;

public interface BuilderAPI {

  ImmutableGameHolder saveFile();

  void loadFile();

}
