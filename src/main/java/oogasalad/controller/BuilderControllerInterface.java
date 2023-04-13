package oogasalad.controller;

import java.io.IOException;
import oogasalad.model.constructable.Board;
import oogasalad.view.builder.gameholder.ImmutableGameHolder;

public interface BuilderControllerInterface {

  void save(ImmutableGameHolder holder, Board board) throws IOException;
  void load();

}
