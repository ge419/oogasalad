package oogasalad.model.constructable;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GameHolderTest {
  GameHolder testGameHolder;
  Player mockPlayer;
  Piece mockPiece;

  @BeforeEach
  void setup(){
    testGameHolder = new GameHolder();
    mockPlayer = Mockito.mock(Player.class);
    mockPiece = Mockito.mock(Piece.class);
    testGameHolder.setPlayers(new Players());
    testGameHolder.setCurrentPlayer(mockPlayer);
    testGameHolder.setPieces(List.of(mockPiece));
  }

  @Test
  void testGetPieceAndPlayerByIdReturnEmptyOptionalSinceTileIsMocked(){
    assertTrue(testGameHolder.getPieceById("").isEmpty());
    assertTrue(testGameHolder.getPlayerById("").isEmpty());
    assertTrue(testGameHolder.getTileById("").isEmpty());
  }

}