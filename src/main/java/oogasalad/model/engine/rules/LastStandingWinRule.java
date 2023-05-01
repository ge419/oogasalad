package oogasalad.model.engine.rules;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.google.inject.Inject;
import oogasalad.model.attribute.IntAttribute;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.AbstractGameConstruct;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.Priority;
import oogasalad.model.engine.actions.ActionFactory;
import oogasalad.model.engine.actions.wins.StandingWinningStrategy;
import oogasalad.model.engine.actions.wins.WinningConditionStrategy;
import oogasalad.model.engine.events.PlayerRemovalEvent;

public class LastStandingWinRule extends AbstractGameConstruct implements EditableRule {

  private static final String SCHEMA_NAME = "lastStandingRule";
  private static final String NUM_WIN_PLAYER = "numWinPlayer";
  private final ActionFactory actionFactory;
  private final GameHolder gameHolder;

  @Inject
  protected LastStandingWinRule(
      @JacksonInject SchemaDatabase database,
      @JacksonInject GameHolder gameHolder,
      @JacksonInject ActionFactory actionFactory
  ) {
    super(SCHEMA_NAME, database);
    this.actionFactory = actionFactory;
    this.gameHolder = gameHolder;
  }

  @Override
  public void registerEventHandlers(EventRegistrar registrar) {
    registrar.registerHandler(PlayerRemovalEvent.class, this::checkWinState);
  }

  protected void checkWinState(EventHandlerParams<PlayerRemovalEvent> eventEventHandlerParams) {
    int lastN = IntAttribute.from(this.getAttribute(NUM_WIN_PLAYER).get()).getValue();
    WinningConditionStrategy winningCondition = new StandingWinningStrategy(gameHolder, lastN);
    eventEventHandlerParams.actionQueue().add(Priority.MOST_HIGH.getValue(), actionFactory.makeCheckWinStateAction(winningCondition));
  }
}
