package oogasalad.model.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oogasalad.model.engine.events.EventType;

/**
 * A simple {@link HashMap} based event handler implementation. Handlers are called in the order
 * they are registered for the given event.
 *
 * @author Dominic Martinez
 */
public class SimpleEventHandlerManager implements EventHandlerManager {

  private final Map<String, List<EventHandler>> handlerMap;

  public SimpleEventHandlerManager() {
    handlerMap = new HashMap<>();
  }

  @Override
  public void registerHandler(EventType type, EventHandler handler) {
    List<EventHandler> handlers = handlerMap.getOrDefault(type.type(), new ArrayList<>());
    handlers.add(handler);
    handlerMap.put(type.type(), handlers);
  }

  @Override
  public void triggerEvent(EventHandlerParams params) {
    String type = params.event().type().type();

    if (!handlerMap.containsKey(type)) {
      return;
    }

    for (EventHandler handler : handlerMap.get(type)) {
      handler.handleEvent(params);
    }
  }
}
