package oogasalad.model.engine.rules;

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
import oogasalad.model.engine.actions.creation.CreatePlayersAction;
import oogasalad.model.engine.events.StartGameEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class NumberOfPlayersRuleTest {

  private static final int TEST_MIN = 1;
  private static final int TEST_MAX = 4;
  private static final int TEST_PIECE = 1;
  private ActionFactory mockActionFactory = mock(ActionFactory.class);
  private CreatePlayersAction mockedAction = mock(CreatePlayersAction.class);
  private ActionQueue mockedQueue;
  private EventHandlerParams<StartGameEvent> eventHandlerParams;
  private NumberOfPlayersRule rule;

  @BeforeEach
  public void setUp() {
    mockActionFactory = mock(ActionFactory.class);
    mockedAction = mock(CreatePlayersAction.class);
    mockedQueue = mock(SimpleActionQueue.class);
    when(mockActionFactory.makeCreatePlayersAction(TEST_MIN, TEST_MAX, TEST_PIECE)).thenReturn(
        mockedAction);

    Injector injector = Guice.createInjector(new AttributeModule());
    SchemaDatabase db = injector.getInstance(SchemaDatabase.class);

    eventHandlerParams = new EventHandlerParams<>(
        new StartGameEvent(), mockedQueue
    );
    rule = new NumberOfPlayersRule(db, mockActionFactory);
  }

  @Test
  public void listensToGameStartEvent() {
    EventRegistrar registrar = mock(EventRegistrar.class);
    rule.registerEventHandlers(registrar);

    ArgumentCaptor<EventHandler> handlerCaptor = ArgumentCaptor.forClass(EventHandler.class);
    verify(registrar).registerHandler(eq(StartGameEvent.class), handlerCaptor.capture());
  }

  @Test
  public void makesCreatePlayerAction() {
    rule.generatePlayersOnSelection(eventHandlerParams);

    verify(mockActionFactory).makeCreatePlayersAction(TEST_MIN, TEST_MAX, TEST_PIECE);
  }

  @Test
  public void addsGeneratedActionToQueue() {
    rule.generatePlayersOnSelection(eventHandlerParams);

    verify(mockedQueue).add(Priority.MOST_HIGH.getValue(), mockedAction);
  }
}