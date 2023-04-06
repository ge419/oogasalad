package oogasalad.model.engine;

import com.google.inject.Inject;
import java.util.List;
import java.util.Optional;
import oogasalad.model.engine.actions.Action;
import oogasalad.model.engine.actions.ActionParams;
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
  private final EventHandlerManager manager;
  private boolean gameIsRunning;

  @Inject
  public SimpleEngine(ActionQueue actionQueue, EventHandlerManager manager) {
    this.actionQueue = actionQueue;
    this.manager = manager;
    this.gameIsRunning = false;
  }

  @Override
  public void run(List<Rule> rules) {
    log.debug("starting game with ruleset {}", rules);
    gameIsRunning = true;

    manager.registerHandler(EngineEvent.END_GAME, eventHandlerParams -> gameIsRunning = false);
    for (Rule rule : rules) {
      rule.registerEventHandlers(manager);
    }

    // TODO: Factory pattern for new Event?
    manager.triggerEvent(new EventHandlerParams(new Event(EngineEvent.START_GAME), actionQueue));

    // TODO: Support subgames
    // Waiting on attributes API to get attributes from event.
    while (gameIsRunning) {
      Optional<Action> optAction = actionQueue.poll();
      if (optAction.isEmpty()) break;
      Action action = optAction.get();

      log.trace("running action {}", action);
      action.runAction(new ActionParams(
          event -> manager.triggerEvent(new EventHandlerParams(event, actionQueue))));
    }

    if (gameIsRunning) {
      log.warn("game ran out of actions");
      gameIsRunning = false;
      throw new MissingActionsException();
    }
  }

}