//package oogasalad.model.engine.actions;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.ResourceBundle;
//import oogasalad.model.constructable.GameHolder;
//import oogasalad.model.engine.EventEmitter;
//import oogasalad.model.engine.events.GameEndEvent;
//import oogasalad.model.engine.prompt.AIPrompter;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//
//class CheckTileWinActionTest {
//
//  private GameHolder gameHolder;
//  private EventEmitter emitter;
//  private ResourceBundle bundle;
//  private ActionParams actionParams;
//  private CheckTileWinAction action;
//  private static final String LANDED_ID = "195";
//  private List<String> winningTileIds;
//
//  @BeforeEach
//  public void setUp() {
//    gameHolder = mock(GameHolder.class);
//    emitter = mock(EventEmitter.class);
//    bundle = mock(ResourceBundle.class);
//    when(bundle.getString(any(String.class))).thenReturn("");
//
//    actionParams = new ActionParams(emitter, new AIPrompter());
//    winningTileIds = new ArrayList<>(List.of("195", "200"));
//    action = new CheckTileWinAction(LANDED_ID, winningTileIds, gameHolder, bundle);
//  }
//
//  @Test
//  public void emitsGameEndEventWhenLandedOnValidTile() {
//
//    action.runAction(actionParams);
//    ArgumentCaptor<GameEndEvent> argument = ArgumentCaptor.forClass(GameEndEvent.class);
//
//    verify(emitter).emit(argument.capture());
//    assertEquals(GameEndEvent.class, argument.getValue().getClass());
//  }
//
//  @Test
//  public void callsGameHolderNotifyWhenLandedOnValidTile() {
//    action.runAction(actionParams);
//
//    verify(gameHolder).notifyGameEnd();
//  }
//
//  @Test
//  public void notEmitGameEndEventWhenNotWinningTile() {
//    winningTileIds = new ArrayList<>(List.of("0", "100"));
//    action = new CheckTileWinAction(LANDED_ID, winningTileIds, gameHolder, bundle);
//
//    action.runAction(actionParams);
//    ArgumentCaptor<GameEndEvent> argument = ArgumentCaptor.forClass(GameEndEvent.class);
//
//    verify(emitter, never()).emit(argument.capture());
//  }
//
//  @Test
//  public void notCallGameHolderNotifyWhenNotWinningTile() {
//    winningTileIds = new ArrayList<>(List.of("0", "100"));
//    action = new CheckTileWinAction(LANDED_ID, winningTileIds, gameHolder, bundle);
//
//    action.runAction(actionParams);
//
//    verify(gameHolder, never()).notifyGameEnd();
//  }
//
//}