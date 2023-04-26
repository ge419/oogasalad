package oogasalad.model.engine.events;

import oogasalad.model.engine.Event;
public class ChooseNumberOfPlayerPieces implements Event<ChooseNumberOfPlayerPieces> {

  @Override
  public Class<ChooseNumberOfPlayerPieces> eventClass() {
    return ChooseNumberOfPlayerPieces.class;
  }
}
