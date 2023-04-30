package oogasalad.model.engine.rules;

import com.fasterxml.jackson.annotation.JacksonInject;
import java.util.List;
import javax.inject.Inject;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.AbstractGameConstruct;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Player;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.Priority;
import oogasalad.model.engine.actions.ActionFactory;
import oogasalad.model.engine.actions.EventAction;
import oogasalad.model.engine.events.PlayerCreationEvent;
import oogasalad.model.engine.events.StartTurnEvent;

public class TurnRule extends AbstractGameConstruct implements EditableRule {

  public static final String SCHEMA_NAME = "turnRule";
  public static final int ABSENT = -1;
  public static final int TURN_INCREMENT = 1;
  private final ActionFactory actionFactory;
  private final GameHolder game;

  @Inject
  public TurnRule(
      @JacksonInject SchemaDatabase database,
      @JacksonInject ActionFactory actionFactory,
      @JacksonInject GameHolder game) {
    super(SCHEMA_NAME, database);
    this.actionFactory = actionFactory;
    this.game = game;
  }

  @Override
  public void registerEventHandlers(EventRegistrar registrar) {
    registrar.registerHandler(PlayerCreationEvent.class, this::newTurn);
    registrar.registerHandler(StartTurnEvent.class, this::newTurn);
  }

  private void newTurn(EventHandlerParams<?> eventHandlerParams) {
    Player nextPlayer = getNextPlayer();

    eventHandlerParams.actionQueue().add(Priority.MEDIUM.getValue(), actionFactory.makeSetCurrentPlayerAction(nextPlayer));
    eventHandlerParams.actionQueue().add(Priority.MEDIUM.getValue(), new EventAction(new StartTurnEvent()));
  }

  private Player getNextPlayer() {
    List<Player> players = game.getPlayers().getList();
    Player currentPlayer = game.getCurrentPlayer();
    int currentPlayerIndex = currentPlayer == null ? ABSENT : players.indexOf(currentPlayer);

    if (currentPlayerIndex == ABSENT) {
      return players.get(0);
    }

    return players.get((currentPlayerIndex + TURN_INCREMENT) % players.size());
  }
}
