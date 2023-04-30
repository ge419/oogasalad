package oogasalad.model.engine.rules;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.google.inject.Inject;
import oogasalad.model.attribute.IntAttribute;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.AbstractGameConstruct;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.actions.ActionFactory;
import oogasalad.model.engine.events.StartTurnEvent;

public class RemovePlayerRule extends AbstractGameConstruct implements EditableRule {

  public static final String SCHEMA_NAME = "removePlayerRule";
  private final GameHolder gameHolder;
  private final ActionFactory actionFactory;

  @Inject
  protected RemovePlayerRule(
      @JacksonInject SchemaDatabase database,
      @JacksonInject GameHolder gameholder,
      @JacksonInject ActionFactory actionFactory
  ) {
    super(SCHEMA_NAME, database);
    this.gameHolder = gameholder;
    this.actionFactory = actionFactory;
  }

  @Override
  public void registerEventHandlers(EventRegistrar registrar) {
    registrar.registerHandler(StartTurnEvent.class, this::removePlayers);
  }

  private void removePlayers(EventHandlerParams<StartTurnEvent> eventEventHandlerParams) {
    int scoreMinBound = IntAttribute.from(this.getAttribute("scoreMinBound").get()).getValue();
    eventEventHandlerParams.actionQueue().add(0, actionFactory.makeCheckAndRemovePlayerAction(scoreMinBound));
  }
}
