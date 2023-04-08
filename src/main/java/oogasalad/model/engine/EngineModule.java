package oogasalad.model.engine;

import com.google.inject.AbstractModule;

/**
 * Guice module for the {@link Engine}.
 *
 * @author Dominic Martinez
 */
public class EngineModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ActionQueue.class).to(SimpleActionQueue.class);
    bind(Engine.class).to(SimpleEngine.class);
    bind(EventHandlerManager.class).to(SimpleEventHandlerManager.class);
  }
}
