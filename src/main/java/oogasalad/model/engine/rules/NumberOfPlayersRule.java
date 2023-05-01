package oogasalad.model.engine.rules;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.google.inject.Inject;
import oogasalad.model.attribute.IntAttribute;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.AbstractGameConstruct;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.Priority;
import oogasalad.model.engine.actions.ActionFactory;
import oogasalad.model.engine.events.StartGameEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Rule that outlines choosing the number of players at the start of game.
 *
 * @Author Jay Yoon
 */
public class NumberOfPlayersRule extends AbstractGameConstruct implements EditableRule {

  public static final String SCHEMA_NAME = "numberOfPlayersRule";
  public static final String PIECE_PER_PLAYER = "piecePerPlayer";
  private static final Logger LOGGER = LogManager.getLogger(NumberOfPlayersRule.class);
  private static final String MIN_PLAYER = "minPlayer";
  private static final String MAX_PLAYER = "maxPlayer";
  private final ActionFactory actionFactory;

  @Inject
  protected NumberOfPlayersRule(
      @JacksonInject SchemaDatabase database,
      @JacksonInject ActionFactory actionFactory) {
    super(SCHEMA_NAME, database);
    this.actionFactory = actionFactory;
  }

  /**
   * Listens for a {@link StartGameEvent} to run
   * {@link #generatePlayersOnSelection(EventHandlerParams)}
   *
   * <p>
   * retrieves the number of minimum, maximum players to play game and number of pieces per player
   * adds {@link oogasalad.model.engine.actions.creation.CreatePlayersAction} to action queue for
   * player creation
   * </p>
   *
   * @param registrar provides event registration methods
   */
  @Override
  public void registerEventHandlers(EventRegistrar registrar) {
    registrar.registerHandler(StartGameEvent.class, this::generatePlayersOnSelection);
  }

  protected void generatePlayersOnSelection(EventHandlerParams<StartGameEvent> eventHandlerParams) {
    int min = IntAttribute.from(this.getAttribute(MIN_PLAYER).get()).getValue();
    int max = IntAttribute.from(this.getAttribute(MAX_PLAYER).get()).getValue();
    int piecePerPlayer = IntAttribute.from(this.getAttribute(PIECE_PER_PLAYER).get()).getValue();
    LOGGER.info("Add Create Players Action to ActionQueue");
    eventHandlerParams.actionQueue().add(Priority.MOST_HIGH.getValue(),
        actionFactory.makeCreatePlayersAction(min, max, piecePerPlayer));
  }
}
