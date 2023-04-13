package oogasalad.model.builder;

import java.io.IOException;
import oogasalad.model.constructable.Board;
import oogasalad.view.builder.gameholder.ImmutableGameHolder;

public interface BBuilderAPI {

  void save(ImmutableGameHolder holder, Board board) throws IOException;

  void load();
}
