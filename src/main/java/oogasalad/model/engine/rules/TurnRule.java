package oogasalad.model.engine.rules;

import java.util.List;
import javax.inject.Inject;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.AbstractGameConstruct;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.actions.EventAction;
import oogasalad.model.engine.events.StartGameEvent;
import oogasalad.model.engine.events.StartTurnEvent;

public class TurnRule extends AbstractGameConstruct implements EditableRule {

  public static final String SCHEMA_NAME = "turnRule";

  @Inject
  public TurnRule(SchemaDatabase database) {
    super(List.of(SCHEMA_NAME), database);
  }

  @Override
  public void registerEventHandlers(EventRegistrar registrar) {
    registrar.registerHandler(StartGameEvent.class, this::newTurn);
    registrar.registerHandler(StartTurnEvent.class, this::newTurn);
  }

  private void newTurn(EventHandlerParams<?> eventHandlerParams) {
    eventHandlerParams.actionQueue().add(10, new EventAction(new StartTurnEvent()));
  }
}
