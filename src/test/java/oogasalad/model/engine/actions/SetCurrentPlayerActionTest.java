package oogasalad.model.engine.actions;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Player;
import oogasalad.model.engine.EventEmitter;
import oogasalad.model.engine.actions.turns.SetCurrentPlayerAction;
import oogasalad.model.engine.prompt.AIPrompter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SetCurrentPlayerActionTest {

  private GameHolder mockHolder;
  private EventEmitter emitter;
  private ActionParams actionParams;
  private SetCurrentPlayerAction action;
  private Player mockPlayer;


  @BeforeEach
  public void setUp() {
    mockPlayer = mock(Player.class);
    mockHolder = mock(GameHolder.class);
    emitter = mock(EventEmitter.class);

    actionParams = new ActionParams(emitter, new AIPrompter());
    action = new SetCurrentPlayerAction(mockHolder, mockPlayer);
  }

  @Test
  public void setsGameHolderWithNextPlayer() {
    action.runAction(actionParams);

    verify(mockHolder).setCurrentPlayer(mockPlayer);
  }

}