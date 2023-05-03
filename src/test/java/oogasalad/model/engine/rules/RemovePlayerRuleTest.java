package oogasalad.model.engine.rules;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.inject.Guice;
import com.google.inject.Injector;
import oogasalad.model.attribute.AttributeModule;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.engine.ActionQueue;
import oogasalad.model.engine.EventHandler;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.Priority;
import oogasalad.model.engine.SimpleActionQueue;
import oogasalad.model.engine.actions.ActionFactory;
import oogasalad.model.engine.actions.removal.CheckAndRemovePlayerAction;
import oogasalad.model.engine.actions.removal.LowScoreRemovalStrategy;
import oogasalad.model.engine.actions.removal.PlayerRemovalStrategy;
import oogasalad.model.engine.actions.removal.RemovedPlayerTileResetStrategy;
import oogasalad.model.engine.actions.removal.TileResetStrategy;
import oogasalad.model.engine.events.StartTurnEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class RemovePlayerRuleTest {

  private static final int TEST_MIN = 0;
  private ActionFactory mockActionFactory;
  private CheckAndRemovePlayerAction mockedAction;
  private ActionQueue mockedQueue;
  private EventHandlerParams<StartTurnEvent> eventHandlerParams;
  private RemovePlayerRule rule;
  private PlayerRemovalStrategy mockedRemovalStrategy;
  private TileResetStrategy tileResetStrategy;

  @BeforeEach
  public void setUp() {
    mockActionFactory = mock(ActionFactory.class);
    mockedAction = mock(CheckAndRemovePlayerAction.class);
    mockedQueue = mock(SimpleActionQueue.class);
    mockedRemovalStrategy = mock(PlayerRemovalStrategy.class);
    tileResetStrategy = mock(TileResetStrategy.class);
    when(mockActionFactory.makeCheckAndRemovePlayerAction(anyInt(), any(PlayerRemovalStrategy.class),
        any(TileResetStrategy.class))).thenReturn(mockedAction);

    Injector injector = Guice.createInjector(new AttributeModule());
    SchemaDatabase db = injector.getInstance(SchemaDatabase.class);

    eventHandlerParams = new EventHandlerParams<>(
        new StartTurnEvent(), mockedQueue
    );
    rule = new RemovePlayerRule(db, mockActionFactory);
  }

  @Test
  public void listensToStartTurnEvent() {
    EventRegistrar registrar = mock(EventRegistrar.class);
    rule.registerEventHandlers(registrar);

    ArgumentCaptor<EventHandler> handlerCaptor = ArgumentCaptor.forClass(EventHandler.class);
    verify(registrar).registerHandler(eq(StartTurnEvent.class), handlerCaptor.capture());
  }

  @Test
  public void makesRemovePlayersAction() {
    rule.removePlayers(eventHandlerParams);

    verify(mockActionFactory).makeCheckAndRemovePlayerAction(anyInt(), any(LowScoreRemovalStrategy.class),
        any(RemovedPlayerTileResetStrategy.class));
  }

  @Test
  public void addsGeneratedActionToQueue() {
    rule.removePlayers(eventHandlerParams);

    verify(mockedQueue).add(Priority.MOST_HIGH.getValue(), mockedAction);
  }

}