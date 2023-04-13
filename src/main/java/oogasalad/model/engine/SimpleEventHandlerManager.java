package oogasalad.model.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oogasalad.model.engine.events.AttributeEvent;

/**
 * A simple {@link HashMap} based event handler implementation. Handlers are called in the order
 * they are registered for the given event.
 *
 * @author Dominic Martinez
 */
public class SimpleEventHandlerManager implements EventHandlerManager {

  private final Map<String, List<EventHandler<?>>> handlerMap;

  public SimpleEventHandlerManager() {
    handlerMap = new HashMap<>();
  }

  @Override
  public <T extends Event<T>> void registerHandler(Class<T> type, EventHandler<T> handler) {
    registerAnyHandler(type.getName(), handler);
  }

  @Override
  public void registerHandler(String type, EventHandler<AttributeEvent> handler) {
    registerAnyHandler(type, handler);
  }

  private void registerAnyHandler(String type, EventHandler<?> handler) {
    if (!handlerMap.containsKey(type)) {
      handlerMap.put(type, new ArrayList<>());
    }

    List<EventHandler<?>> handlers = handlerMap.get(type);
    handlers.add(handler);
  }

  @Override
  public void triggerEvent(EventHandlerParams<?> params) {
    String type = params.event().type();

    if (!handlerMap.containsKey(type)) {
      return;
    }

    // This is safe because only handlers who registered with the generic registerHandler
    // method are called here.
    for (EventHandler handler : handlerMap.get(type)) {
      handler.handleEvent(params);
    }
  }
}
