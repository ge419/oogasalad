package oogasalad.controller;

import oogasalad.view.builder.gameholder.ImmutableGameHolder;

public interface BuilderControllerInterface {

  void save(ImmutableGameHolder holder);
  void load(ImmutableGameHolder holder);

}
