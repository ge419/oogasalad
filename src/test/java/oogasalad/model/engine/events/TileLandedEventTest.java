package oogasalad.model.engine.events;

import oogasalad.model.constructable.Piece;
import oogasalad.model.constructable.Tile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TileLandedEventTest {

  @Test
  void testEventClass() {
    TileLandedEvent event = Mockito.mock(TileLandedEvent.class, Mockito.CALLS_REAL_METHODS);
    Class<TileLandedEvent> expected = TileLandedEvent.class;
    Class<TileLandedEvent> actual = event.eventClass();
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void testGetPiece() {
    Piece piece = Mockito.mock(Piece.class);
    TileLandedEvent event = new TileLandedEvent(piece, Mockito.mock(Tile.class));
    Assertions.assertEquals(piece, event.piece());
  }

  @Test
  void testGetLandedTile() {
    Tile tile = Mockito.mock(Tile.class);
    TileLandedEvent event = new TileLandedEvent(Mockito.mock(Piece.class), tile);
    Assertions.assertEquals(tile, event.landedTile());
  }
}
