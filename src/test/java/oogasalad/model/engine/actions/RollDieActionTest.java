package oogasalad.model.engine.actions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import oogasalad.model.engine.EventEmitter;
import oogasalad.model.engine.actions.moves.RollDieAction;
import oogasalad.model.engine.events.DieRolledEvent;
import oogasalad.model.engine.prompt.TestPrompterPositive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class RollDieActionTest {

  private EventEmitter emitter;
  private ActionParams actionParams;
  private RollDieAction action;
  private int[] dieResults;


  @BeforeEach
  public void setUp() {
    emitter = mock(EventEmitter.class);
    dieResults = new int[]{4, 4};

    actionParams = new ActionParams(emitter, new TestPrompterPositive());
    action = new RollDieAction(dieResults);
  }

  @Test
  public void emitDieRolledEvent() {
    action.runAction(actionParams);
    ArgumentCaptor<DieRolledEvent> argument = ArgumentCaptor.forClass(DieRolledEvent.class);
    verify(emitter).emit(argument.capture());

    assertEquals(DieRolledEvent.class, argument.getValue().getClass());
  }

}