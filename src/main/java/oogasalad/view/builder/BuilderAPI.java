package oogasalad.view.builder;

import java.util.Optional;
import oogasalad.view.builder.gameholder.ImmutableGameHolder;

public interface BuilderAPI {

  Optional<ImmutableGameHolder> saveFile();

  void loadFile();

}
