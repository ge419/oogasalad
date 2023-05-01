package oogasalad.model.engine.rules;

import static org.mockito.ArgumentMatchers.any;
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
import oogasalad.model.engine.actions.CheckStandingWinAction;
import oogasalad.model.engine.events.PlayerRemovalEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class LastStandingWinRuleTest {

  public static final int TEST_N = 1;
  private ActionFactory mockActionFactory;
  private EventHandlerParams<PlayerRemovalEvent> eventEventHandlerParams;
  private LastStandingWinRule rule;
  private CheckStandingWinAction mockedAction;
  private ActionQueue mockedQueue;

  @Before
  public void setUp() {
    mockActionFactory = mock(ActionFactory.class);
    mockedAction = mock(CheckStandingWinAction.class);
    mockedQueue = mock(SimpleActionQueue.class);
    when(mockActionFactory.makeCheckWinStateAction(TEST_N)).thenReturn(mockedAction);

    Injector injector = Guice.createInjector(new AttributeModule());
    SchemaDatabase db = injector.getInstance(SchemaDatabase.class);

    eventEventHandlerParams = new EventHandlerParams<>(
        new PlayerRemovalEvent(), mockedQueue
    );
    rule = new LastStandingWinRule(db, mockActionFactory);
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

    verify(mockActionFactory).makeCheckWinStateAction(TEST_N);
  }

  @Test
  public void addsGeneratedActionToQueue() {
    rule.checkWinState(eventEventHandlerParams);

    verify(mockedQueue).add(Priority.MOST_HIGH.getValue(), mockedAction);
  }

}