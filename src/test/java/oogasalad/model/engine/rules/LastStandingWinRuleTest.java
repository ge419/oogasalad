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
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.engine.ActionQueue;
import oogasalad.model.engine.EventHandler;
import oogasalad.model.engine.EventHandlerParams;
import oogasalad.model.engine.EventRegistrar;
import oogasalad.model.engine.Priority;
import oogasalad.model.engine.SimpleActionQueue;
import oogasalad.model.engine.actions.ActionFactory;
import oogasalad.model.engine.actions.wins.CheckWinAndEndAction;
import oogasalad.model.engine.actions.wins.StandingWinningStrategy;
import oogasalad.model.engine.actions.wins.WinningConditionStrategy;
import oogasalad.model.engine.events.PlayerRemovalEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class LastStandingWinRuleTest {

  public static final int TEST_N = 1;
  private ActionFactory mockActionFactory;
  private EventHandlerParams<PlayerRemovalEvent> eventEventHandlerParams;
  private LastStandingWinRule rule;
  private CheckWinAndEndAction mockedAction;
  private ActionQueue mockedQueue;
  private GameHolder gameHolder;
  private WinningConditionStrategy winningConditionStrategy;

  @Before
  public void setUp() {
    mockActionFactory = mock(ActionFactory.class);
    mockedAction = mock(CheckWinAndEndAction.class);
    mockedQueue = mock(ActionQueue.class);
    gameHolder = mock(GameHolder.class);
    winningConditionStrategy = new StandingWinningStrategy(gameHolder, TEST_N);
    when(mockActionFactory.makeCheckWinStateAction(any(StandingWinningStrategy.class))).thenReturn(
        mockedAction);

    Injector injector = Guice.createInjector(new AttributeModule());
    SchemaDatabase db = injector.getInstance(SchemaDatabase.class);

    eventEventHandlerParams = new EventHandlerParams<>(
        new PlayerRemovalEvent(), mockedQueue
    );
    rule = new LastStandingWinRule(db, gameHolder, mockActionFactory);
  }

  @Test
  public void listensToPlayerRemovalEvent() {
    EventRegistrar registrar = mock(EventRegistrar.class);
    rule.registerEventHandlers(registrar);

    ArgumentCaptor<EventHandler> handlerCaptor = ArgumentCaptor.forClass(EventHandler.class);
    verify(registrar).registerHandler(eq(PlayerRemovalEvent.class), handlerCaptor.capture());
  }

  @Test
  public void makesCheckWinStateAction() {
    rule.checkWinState(eventEventHandlerParams);
    verify(mockActionFactory).makeCheckWinStateAction(any(WinningConditionStrategy.class));
  }

  @Test
  public void addsGeneratedActionToQueue() {
    rule.checkWinState(eventEventHandlerParams);
    verify(mockedQueue).add(anyInt(), any(CheckWinAndEndAction.class));
  }

}