package oogasalad.model.engine;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import oogasalad.model.engine.actions.ActionFactory;

/**
 * Guice module for the {@link Engine}.
 *
 * @author Dominic Martinez
 */
public class EngineModule extends AbstractModule {

  @Override
  protected void configure() {
    install(new FactoryModuleBuilder().build(ActionFactory.class));
    bind(ActionQueue.class).to(SimpleActionQueue.class);
    bind(Engine.class).to(SimpleEngine.class);
    bind(EventHandlerManager.class).to(SimpleEventHandlerManager.class);
  }
}
