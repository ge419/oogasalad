package oogasalad.model.engine.event_loop;

import oogasalad.model.engine.event_loop.ActionQueue;
import oogasalad.model.engine.event_loop.Event;

public record EventHandlerParams(Event event, ActionQueue actionQueue) {

}