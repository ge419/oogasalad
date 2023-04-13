package oogasalad.model.builder;

import java.io.IOException;
import oogasalad.model.constructable.BBoard;
import oogasalad.view.builder.gameholder.ImmutableGameHolder;


public interface BBuilderAPI {

  void save(ImmutableGameHolder holder, BBoard board) throws IOException;

  void load();
}
