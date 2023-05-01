package oogasalad.model.engine.actions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import oogasalad.model.constructable.Piece;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.EventEmitter;
import oogasalad.model.engine.actions.moves.MoveAction;
import oogasalad.model.engine.events.MoveEvent;
import oogasalad.model.engine.events.TileLandedEvent;
import oogasalad.model.engine.prompt.AIPrompter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class MoveActionTest {

  private Piece piece;
  private Tile tile1;
  private Tile tile2;
  private List<Tile> moveList;
  private EventEmitter emitter;
  private ActionParams actionParams;
  private MoveAction action;

  @BeforeEach
  public void setUp() {
    piece = mock(Piece.class);
    emitter = mock(EventEmitter.class);
    tile1 = mock(Tile.class);
    tile2 = mock(Tile.class);

    moveList = new ArrayList<>(List.of(tile1, tile2));
    actionParams = new ActionParams(emitter, new AIPrompter());
    action = new MoveAction(piece, moveList);
  }

  @Test
  public void validMoveEventEmitted() {
    action.runAction(actionParams);
    ArgumentCaptor<MoveEvent> argument = ArgumentCaptor.forClass(MoveEvent.class);

    verify(emitter).emit(argument.capture());
    assertEquals(MoveEvent.class, argument.getValue().getClass());
  }

  @Test
  public void validTileLandedEventEmitted() {
    action.runAction(actionParams);
    ArgumentCaptor<TileLandedEvent> argument = ArgumentCaptor.forClass(TileLandedEvent.class);

    verify(emitter).emit(argument.capture());
    assertEquals(TileLandedEvent.class, argument.getValue().getClass());
  }

  @Test
  public void landedOnCorrectTile() {
    action.runAction(actionParams);
    ArgumentCaptor<TileLandedEvent> argument = ArgumentCaptor.forClass(TileLandedEvent.class);
    verify(emitter).emit(argument.capture());

    Tile landedTile = argument.getValue().landedTile();
    assertEquals(tile2, landedTile);
  }

  @Test
  public void notLandOnPassedTiles() {
    action.runAction(actionParams);
    ArgumentCaptor<TileLandedEvent> argument = ArgumentCaptor.forClass(TileLandedEvent.class);
    verify(emitter).emit(argument.capture());

    Tile landedTile = argument.getValue().landedTile();
    assertNotEquals(tile1, landedTile);
  }

  @Test
  public void setsPieceTileToNewTile() {
    action.runAction(actionParams);

    verify(piece).setTile(tile2);
  }

}