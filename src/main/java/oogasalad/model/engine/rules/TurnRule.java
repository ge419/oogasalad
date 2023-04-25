package oogasalad.model.engine.rules;

import java.util.List;
import javax.inject.Inject;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.AbstractGameConstruct;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Player;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.actions.ActionFactory;
import oogasalad.model.engine.actions.EventAction;
import oogasalad.model.engine.events.StartGameEvent;
import oogasalad.model.engine.events.StartTurnEvent;

public class TurnRule extends AbstractGameConstruct implements EditableRule {

  public static final String SCHEMA_NAME = "turnRule";
  private final ActionFactory actionFactory;
  private final GameHolder game;

  @Inject
  public TurnRule(SchemaDatabase database, ActionFactory actionFactory, GameHolder game) {
    super(List.of(SCHEMA_NAME), database);
    this.actionFactory = actionFactory;
    this.game = game;
  }

  @Override
  public void registerEventHandlers(EventRegistrar registrar) {
    registrar.registerHandler(StartGameEvent.class, this::newTurn);
    registrar.registerHandler(StartTurnEvent.class, this::newTurn);
  }

  private void newTurn(EventHandlerParams<?> eventHandlerParams) {
    Player nextPlayer = getNextPlayer();

    eventHandlerParams.actionQueue().add(10, actionFactory.makeSetCurrentPlayerAction(nextPlayer));
    eventHandlerParams.actionQueue().add(10, new EventAction(new StartTurnEvent()));
  }

  private Player getNextPlayer() {
    List<Player> players = game.getPlayers().getPlayers();
    Player currentPlayer = game.getCurrentPlayer();
    int currentPlayerIndex = currentPlayer == null ? -1 : players.indexOf(currentPlayer);

    if (currentPlayerIndex == -1) {
      return players.get(0);
    }

    return players.get((currentPlayerIndex + 1) % players.size());
  }
}
