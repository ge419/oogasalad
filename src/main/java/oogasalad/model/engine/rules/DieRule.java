package oogasalad.model.engine.rules;

import javax.inject.Inject;
import javax.inject.Provider;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.AbstractGameConstruct;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.actions.RollDieAndMoveAction;
import oogasalad.model.engine.events.StartTurnEvent;

public class DieRule extends AbstractGameConstruct implements EditableRule {

  public static final String SCHEMA_NAME = "dieRule";
  private final Provider<RollDieAndMoveAction> dieActionProvider;

  @Inject
  public DieRule(SchemaDatabase db, Provider<RollDieAndMoveAction> dieActionProvider) {
    super(SCHEMA_NAME, db);
    this.dieActionProvider = dieActionProvider;
  }

  @Override
  public void registerEventHandlers(EventRegistrar registrar) {
    registrar.registerHandler(StartTurnEvent.class, this::rollDie);
  }

  private void rollDie(EventHandlerParams<StartTurnEvent> eventHandlerParams) {
    eventHandlerParams.actionQueue().add(1, dieActionProvider.get());
  }
}
