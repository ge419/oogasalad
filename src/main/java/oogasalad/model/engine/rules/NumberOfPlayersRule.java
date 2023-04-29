package oogasalad.model.engine.rules;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.google.inject.Inject;
import java.util.List;
import javafx.beans.property.ReadOnlyListProperty;
import oogasalad.model.attribute.IntAttribute;
import oogasalad.model.attribute.SchemaBinding;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.AbstractGameConstruct;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.actions.ActionFactory;
import oogasalad.model.engine.events.StartGameEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NumberOfPlayersRule extends AbstractGameConstruct implements EditableRule{

  public static final String SCHEMA_NAME = "numberOfPlayersRule";
  private static final Logger LOGGER = LogManager.getLogger(NumberOfPlayersRule.class);
  private final GameHolder gameHolder;
  private final ActionFactory actionFactory;

  @Inject
  protected NumberOfPlayersRule(
      @JacksonInject SchemaDatabase database,
      @JacksonInject GameHolder gameHolder,
      @JacksonInject ActionFactory actionFactory) {
    super(SCHEMA_NAME, database);
    this.gameHolder = gameHolder;
    this.actionFactory = actionFactory;
  }

  @Override
  public void registerEventHandlers(EventRegistrar registrar) {
    registrar.registerHandler(StartGameEvent.class, this::generatePlayersOnSelection);
  }

  private void generatePlayersOnSelection(EventHandlerParams<StartGameEvent> eventHandlerParams){
    int min = IntAttribute.from(this.getAttribute("minPlayer").get()).getValue();
    int max = IntAttribute.from(this.getAttribute("maxPlayer").get()).getValue();

    eventHandlerParams.actionQueue().add(0, actionFactory.makeCreatePlayersAction(min, max));
  }
}
