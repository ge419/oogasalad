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
import oogasalad.model.engine.events.StartTurnEvent;

public class RemovePlayerRule extends AbstractGameConstruct implements EditableRule {

  public static final String SCHEMA_NAME = "removePlayerRule";
  public static final String SCORE_MIN_BOUND = "scoreMinBound";
  private final ActionFactory actionFactory;

  @Inject
  protected RemovePlayerRule(
      @JacksonInject SchemaDatabase database,
      @JacksonInject ActionFactory actionFactory
  ) {
    super(SCHEMA_NAME, database);
    this.actionFactory = actionFactory;
  }

  @Override
  public void registerEventHandlers(EventRegistrar registrar) {
    registrar.registerHandler(StartTurnEvent.class, this::removePlayers);
  }

  protected void removePlayers(EventHandlerParams<StartTurnEvent> eventEventHandlerParams) {
    int scoreMinBound = IntAttribute.from(this.getAttribute(SCORE_MIN_BOUND).get()).getValue();
    eventEventHandlerParams.actionQueue().add(Priority.MOST_HIGH.getValue(), actionFactory.makeCheckAndRemovePlayerAction(scoreMinBound));
  }
}
