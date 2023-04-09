package oogasalad.model.engine;

import com.google.inject.AbstractModule;
import oogasalad.model.engine.actions.RollDieAndMoveAction;
import oogasalad.model.engine.rules.DieRule;
import oogasalad.model.engine.rules.TurnRule;

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
