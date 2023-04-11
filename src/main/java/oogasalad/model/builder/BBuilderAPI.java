package oogasalad.model.builder;

import java.io.IOException;
import oogasalad.view.builder.gameholder.ImmutableGameHolder;

public interface BBuilderAPI {

  void save(ImmutableGameHolder holder) throws IOException;

  void load();

  void serialize(Object o) throws IOException;

  void deserialize(Object o) throws IOException;
}
