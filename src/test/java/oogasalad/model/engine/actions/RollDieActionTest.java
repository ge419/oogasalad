package oogasalad.model.engine.actions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import oogasalad.model.constructable.Piece;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.EventEmitter;
import oogasalad.model.engine.events.DieRolledEvent;
import oogasalad.model.engine.events.MoveEvent;
import oogasalad.model.engine.prompt.AIPrompter;
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

    actionParams = new ActionParams(emitter, new AIPrompter());
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