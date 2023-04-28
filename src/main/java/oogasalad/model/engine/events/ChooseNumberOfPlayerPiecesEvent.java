package oogasalad.model.engine.events;

import oogasalad.model.engine.Event;
public class ChooseNumberOfPlayerPiecesEvent implements Event<ChooseNumberOfPlayerPiecesEvent> {

  @Override
  public Class<ChooseNumberOfPlayerPiecesEvent> eventClass() {
    return ChooseNumberOfPlayerPiecesEvent.class;
  }
}
