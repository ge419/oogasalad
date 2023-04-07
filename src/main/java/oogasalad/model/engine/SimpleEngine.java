package oogasalad.model.engine;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.util.List;
import java.util.Optional;
import oogasalad.model.engine.actions.Action;
import oogasalad.model.engine.actions.ActionParams;
import oogasalad.model.engine.actions.EventAction;
import oogasalad.model.engine.prompt.Prompter;
import oogasalad.model.engine.event_loop.ActionQueue;
import oogasalad.model.engine.event_loop.Event;
import oogasalad.model.engine.event_loop.EventHandlerManager;
import oogasalad.model.engine.event_loop.EventHandlerParams;
import oogasalad.model.engine.event_loop.MissingActionsException;
import oogasalad.model.engine.rules.Rule;
import oogasalad.model.engine.event_types.EngineEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/***
 * This class serves as the core logic for the game, by
 * implementing the game rules and mechanics.
 */
public class SimpleEngine implements Engine {
  private static final Logger log = LogManager.getLogger(SimpleEngine.class);
  private final ActionQueue actionQueue;
  private final Provider<EventHandlerManager> managerProvider;
  private EventHandlerManager currentManager;

  @Inject
  public SimpleEngine(ActionQueue actionQueue, Provider<EventHandlerManager> managerProvider) {
    this.actionQueue = actionQueue;
    this.managerProvider = managerProvider;

    EventAction startGameAction = new EventAction(EngineEvent.START_GAME);
    actionQueue.add(0, startGameAction);
  }

  @Override
  public void setRules(List<? extends Rule> rules) {
    currentManager = managerProvider.get();

    // TODO: Support subgames
    // Waiting on attributes API to get attributes from event.
    for (Rule rule : rules) {
      rule.registerEventHandlers(currentManager);
    }
  }

  @Override
  public void runNextAction(Prompter prompter) {
    Optional<Action> optAction = actionQueue.poll();

    if (optAction.isEmpty()) {
      log.warn("game ran out of actions");
      throw new MissingActionsException();
    }

    Action action = optAction.get();
    log.trace("running action {}", action);
    action.runAction(new ActionParams(this::triggerEvent, prompter));
  }

  private void triggerEvent(Event event) {
    log.trace("triggering event {}", event);
    currentManager.triggerEvent(new EventHandlerParams(event, actionQueue));
  }
}