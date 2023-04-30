package oogasalad.model.engine.rules;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.google.inject.Inject;
import java.util.ResourceBundle;
import oogasalad.model.attribute.IntAttribute;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.AbstractGameConstruct;
import oogasalad.model.engine.EngineResourceBundle;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.actions.ActionFactory;
import oogasalad.model.engine.events.PlayerRemovalEvent;

public class LastStandingWinRule extends AbstractGameConstruct implements EditableRule {

  public static final String SCHEMA_NAME = "lastStandingRule";
  private final ActionFactory actionFactory;

  @Inject
  protected LastStandingWinRule(
      @JacksonInject SchemaDatabase database,
      @JacksonInject ActionFactory actionFactory,
      @JacksonInject @EngineResourceBundle ResourceBundle bundle
  ) {
    super(SCHEMA_NAME, database);
    this.actionFactory = actionFactory;
  }

  @Override
  public void registerEventHandlers(EventRegistrar registrar) {
    registrar.registerHandler(PlayerRemovalEvent.class, this::checkWinState);
  }

  private void checkWinState(EventHandlerParams<PlayerRemovalEvent> eventEventHandlerParams) {
    int lastN = IntAttribute.from(this.getAttribute("numWinPlayer").get()).getValue();
    eventEventHandlerParams.actionQueue().add(0, actionFactory.makeCheckWinStateAction(lastN));
  }
}
