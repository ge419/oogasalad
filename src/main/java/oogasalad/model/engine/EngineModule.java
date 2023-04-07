package oogasalad.model.engine;

import com.google.inject.AbstractModule;
import oogasalad.model.engine.event_loop.ActionQueue;
import oogasalad.model.engine.event_loop.EventHandlerManager;
import oogasalad.model.engine.event_loop.SimpleActionQueue;
import oogasalad.model.engine.event_loop.SimpleEventHandlerManager;

public class EngineModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ActionQueue.class).to(SimpleActionQueue.class);
    bind(Engine.class).to(SimpleEngine.class);
    bind(EventHandlerManager.class).to(SimpleEventHandlerManager.class);
  }
}
