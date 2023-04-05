package oogasalad.model.engine.event_loop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oogasalad.model.engine.event_types.EventType;

public class SimpleEventHandlerManager implements EventHandlerManager {

  private final Map<EventType, List<EventHandler>> handlerMap;

  public SimpleEventHandlerManager() {
    handlerMap = new HashMap<>();
  }

  @Override
  public void registerHandler(EventType type, EventHandler handler) {
    List<EventHandler> handlers = handlerMap.getOrDefault(type, new ArrayList<>());
    handlers.add(handler);
    handlerMap.put(type, handlers);
  }

  @Override
  public void triggerEvent(EventHandlerParams params) {
    for (EventHandler handler : handlerMap.get(params.event().type())) {
      handler.handleEvent(params);
    }
  }
}
