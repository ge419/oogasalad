package oogasalad.model.engine.events;

import java.util.Arrays;
import oogasalad.model.constructable.Tile;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

public class MoveEventTest {

  @Test
  public void testEventClass() {
    MoveEvent moveEvent = new MoveEvent(new ArrayList<Tile>());
    assertEquals(MoveEvent.class, moveEvent.eventClass());
  }

  @Test
  public void testGetMoveSequence() {
    Tile tile1 = mock(Tile.class);
    Tile tile2 = mock(Tile.class);
    Tile tile3 = mock(Tile.class);

    List<Tile> moveSequence = Arrays.asList(tile1, tile2, tile3);
    MoveEvent event = new MoveEvent(moveSequence);

    assertEquals(moveSequence, event.getMoveSequence());
  }
}
