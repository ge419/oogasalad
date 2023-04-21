package oogasalad.view.gameplay;

import javafx.event.Event;
import javafx.event.EventType;

public class DieClickedEvent extends Event {

  public static final EventType<DieClickedEvent> DIE_CLICKED = new EventType<>(Event.ANY, "ANY");

  private Die die;

  public DieClickedEvent(EventType<? extends Event> eventType, Die die) {
    super(eventType);
    this.die = die;
  }


}

