package oogasalad.model.engine.rules;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.inject.Guice;
import com.google.inject.Injector;
import oogasalad.model.attribute.AttributeModule;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.engine.ActionQueue;
import oogasalad.model.engine.EventHandler;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.Priority;
import oogasalad.model.engine.SimpleActionQueue;
import oogasalad.model.engine.actions.ActionFactory;
import oogasalad.model.engine.actions.CheckStandingWinAction;
import oogasalad.model.engine.actions.CreatePlayerPieceAction;
import oogasalad.model.engine.events.PieceChosenEvent;
import oogasalad.model.engine.events.PlayerRemovalEvent;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class NumberOfPieceRuleTest {

  private ActionFactory mockActionFactory;
  private CreatePlayerPieceAction mockedAction;
  private ActionQueue mockedQueue;
  private EventHandlerParams<PieceChosenEvent> eventHandlerParams;
  private NumberOfPieceRule rule;
  private GameHolder holder;

  @BeforeEach
  public void setUp() {
    holder = mock(GameHolder.class);
    mockActionFactory = mock(ActionFactory.class);
    mockedAction = mock(CreatePlayerPieceAction.class);
    mockedQueue = mock(SimpleActionQueue.class);
    when(mockActionFactory.makeCreatePlayerPieceAction()).thenReturn(mockedAction);

    Injector injector = Guice.createInjector(new AttributeModule());
    SchemaDatabase db = injector.getInstance(SchemaDatabase.class);

    eventHandlerParams = new EventHandlerParams<>(
        new PieceChosenEvent(3), mockedQueue
    );
    rule = new NumberOfPieceRule(db, holder, mockActionFactory);
  }

  @Test
  public void listensToPlayerPieceChosenEvent() {
    EventRegistrar registrar = mock(EventRegistrar.class);
    rule.registerEventHandlers(registrar);

    ArgumentCaptor<EventHandler> handlerCaptor = ArgumentCaptor.forClass(EventHandler.class);
    verify(registrar).registerHandler(eq(PieceChosenEvent.class), handlerCaptor.capture());
  }

  @Test
  public void makesCreatePieceAction() {
    rule.setPlayerPieces(eventHandlerParams);

    verify(mockActionFactory).makeCreatePlayerPieceAction();
  }

  @Test
  public void addsGeneratedActionToQueue() {
    rule.setPlayerPieces(eventHandlerParams);

    verify(mockedQueue).add(Priority.MOST_HIGH.getValue(), mockedAction);
  }

}