package oogasalad.model.engine.prompt;

import javafx.event.Event;
import javafx.event.EventType;
import oogasalad.view.gameplay.Die;

public class DieEvent extends Event {

  public static final EventType<DieEvent> ANY = new EventType<>(Event.ANY, "ANY");

  private Die die;

  public DieEvent(EventType<? extends Event> eventType, Die die) {
    super(eventType);
    this.die = die;
  }


}

