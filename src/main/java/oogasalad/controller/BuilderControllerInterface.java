package oogasalad.controller;

import java.io.IOException;
import oogasalad.view.builder.gameholder.ImmutableGameHolder;

public interface BuilderControllerInterface {

  void save(ImmutableGameHolder holder) throws IOException;
  void load(ImmutableGameHolder holder);

}
