package oogasalad.model.builder;

import java.io.IOException;

public interface BBuilderAPI {

  void save() throws IOException;

  void load();

  void serialize(Object o) throws IOException;

  void deserialize(Object o) throws IOException;
}
