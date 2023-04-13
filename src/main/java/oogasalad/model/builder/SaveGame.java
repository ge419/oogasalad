package oogasalad.model.builder;

import java.io.IOException;

public interface SaveGame {

  void save(Object o) throws IOException;
  //void save(File file);
}
