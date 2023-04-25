package oogasalad.model.engine.rules;

import com.fasterxml.jackson.annotation.JacksonInject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.inject.Inject;
import oogasalad.model.attribute.IntAttribute;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.AbstractGameConstruct;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Piece;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.actions.ActionFactory;
import oogasalad.model.engine.events.StartTurnEvent;

public class DieMoveRule extends AbstractGameConstruct implements EditableRule {

  public static final String SCHEMA_NAME = "dieRule";
  public static final String NUM_DICE_ATTRIBUTE = "numberOfDice";
  public static final int MIN_ROLL = 1;
  public static final int MAX_ROLL = 6;
  private final ActionFactory actionFactory;
  private final Random random;
  private final GameHolder gameHolder;

  @Inject
  public DieMoveRule(
      @JacksonInject SchemaDatabase database,
      @JacksonInject ActionFactory actionFactory,
      @JacksonInject GameHolder gameHolder,
      @JacksonInject Random random
  ) {
    super(SCHEMA_NAME, database);
    this.actionFactory = actionFactory;
    this.gameHolder = gameHolder;
    this.random = random;
  }

  @Override
  public void registerEventHandlers(EventRegistrar registrar) {
    registrar.registerHandler(StartTurnEvent.class, this::rollDie);
  }

  private void rollDie(EventHandlerParams<StartTurnEvent> eventHandlerParams) {
    int numDieToRoll = getNumberOfDice();

    int total = 0;
    int[] rolls = new int[numDieToRoll];
    for (int i = 0; i < numDieToRoll; i++) {
      rolls[i] = random.nextInt(MIN_ROLL, MAX_ROLL + 1);
      total += rolls[i];
    }

    // TODO: Multiple pieces?
    Piece piece = gameHolder.getCurrentPlayer().getPieces().get(0);
    List<Tile> moves = makeMoveSequence(piece, total);

    eventHandlerParams.actionQueue().add(1, actionFactory.makeRollDieAction(rolls));
    eventHandlerParams.actionQueue().add(1, actionFactory.makeMoveAction(piece, moves));
  }

  private List<Tile> makeMoveSequence(Piece piece, int amountMoved) {
    Tile currentTile = gameHolder.getTileById(piece.getTileId().get()).get();
    List<Tile> moves = new ArrayList<>(amountMoved);
    for (int i = 0; i < amountMoved; i++) {
      // TODO: Multiple paths?
      currentTile = gameHolder.getTileById(currentTile.getNextTileIds().get(0)).get();
      moves.add(currentTile);
    }

    return moves;
  }

  private int getNumberOfDice() {
    return IntAttribute.from(getAttribute(NUM_DICE_ATTRIBUTE).get()).getValue();
  }
}
