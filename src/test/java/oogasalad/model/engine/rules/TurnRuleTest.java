package oogasalad.model.engine.rules;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.List;
import java.util.Optional;
import oogasalad.model.attribute.AttributeModule;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Player;
import oogasalad.model.constructable.Players;
import oogasalad.model.engine.ActionQueue;
import oogasalad.model.engine.EventHandler;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.Priority;
import oogasalad.model.engine.SimpleActionQueue;
import oogasalad.model.engine.actions.Action;
import oogasalad.model.engine.actions.ActionFactory;
import oogasalad.model.engine.actions.CreatePlayersAction;
import oogasalad.model.engine.actions.EventAction;
import oogasalad.model.engine.actions.SetCurrentPlayerAction;
import oogasalad.model.engine.events.PlayerCreationEvent;
import oogasalad.model.engine.events.StartGameEvent;
import oogasalad.model.engine.events.StartTurnEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class TurnRuleTest {

  private ActionFactory mockActionFactory;
  private SetCurrentPlayerAction mockedAction;
  private ActionQueue mockedQueue;
  private TurnRule rule;
  private Player mockedPlayer;
  private EventHandlerParams<StartTurnEvent> eventHandlerParams;

  @BeforeEach
  public void setUp() {
    mockActionFactory = mock(ActionFactory.class);
    mockedAction = mock(SetCurrentPlayerAction.class);
    mockedQueue = mock(SimpleActionQueue.class);
    when(mockActionFactory.makeSetCurrentPlayerAction(any(Player.class))).thenReturn(mockedAction);

    Injector injector = Guice.createInjector(new AttributeModule());
    SchemaDatabase db = injector.getInstance(SchemaDatabase.class);

    GameHolder gameHolder = new GameHolder();
    mockedPlayer = mock(Player.class);
    Players players = new Players(List.of(mockedPlayer));
    gameHolder.setPlayers(players);

    eventHandlerParams = new EventHandlerParams<>(
        new StartTurnEvent(), mockedQueue
    );

    rule = new TurnRule(db, mockActionFactory, gameHolder);
  }

  @Test
  public void listensToTurnStartEvent() {
    EventRegistrar registrar = mock(EventRegistrar.class);
    rule.registerEventHandlers(registrar);

    ArgumentCaptor<EventHandler> handlerCaptor = ArgumentCaptor.forClass(EventHandler.class);
    verify(registrar).registerHandler(eq(StartTurnEvent.class), handlerCaptor.capture());
  }

  @Test
  public void listensToPlayerCreationEvent() {
    EventRegistrar registrar = mock(EventRegistrar.class);
    rule.registerEventHandlers(registrar);

    ArgumentCaptor<EventHandler> handlerCaptor = ArgumentCaptor.forClass(EventHandler.class);
    verify(registrar).registerHandler(eq(PlayerCreationEvent.class), handlerCaptor.capture());
  }

  @Test
  public void makesSetCurrentPlayerAction() {
    rule.newTurn(eventHandlerParams);

    verify(mockActionFactory).makeSetCurrentPlayerAction(mockedPlayer);
  }

  @Test
  public void addsGeneratedSetCurrentPlayerActionToQueue() {
    rule.newTurn(eventHandlerParams);

    verify(mockedQueue).add(Priority.MEDIUM.getValue(), mockedAction);
  }

  @Test
  public void addsEventActionToQueue() {
    rule.newTurn(eventHandlerParams);

    ArgumentCaptor<Integer> priorityCaptor = ArgumentCaptor.forClass(Integer.class);
    ArgumentCaptor<Action> actionCaptor = ArgumentCaptor.forClass(EventAction.class);
    verify(mockedQueue).add(priorityCaptor.capture(), actionCaptor.capture());

    assertEquals(Priority.MEDIUM.getValue(), priorityCaptor.getValue());
    assertTrue(actionCaptor.getValue() instanceof EventAction);
  }

  @Test
  public void noOtherActionsAddedToQueue() {
    rule.newTurn(eventHandlerParams);

    ArgumentCaptor<Integer> priorityCaptor = ArgumentCaptor.forClass(Integer.class);
    ArgumentCaptor<Action> actionCaptor = ArgumentCaptor.forClass(EventAction.class);
    verify(mockedQueue).add(priorityCaptor.capture(), actionCaptor.capture());

    assertEquals(Priority.MEDIUM.getValue(), priorityCaptor.getValue());
    assertFalse(actionCaptor.getValue() instanceof CreatePlayersAction);
  }





}