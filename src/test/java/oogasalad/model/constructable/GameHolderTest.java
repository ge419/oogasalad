package oogasalad.model.constructable;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.attribute.SimpleSchemaDatabase;
import oogasalad.model.observers.GameObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GameHolderTest {
  GameHolder testGameHolder;
  Player mockPlayer;
  Piece mockPiece;
  ObjectMapper testObjectMapper;
  SchemaDatabase testSchemaDB;


  @BeforeEach
  void setup(){
    testObjectMapper = new ObjectMapper();
    testSchemaDB =new SimpleSchemaDatabase(testObjectMapper);
    testGameHolder = new GameHolder();
    mockPlayer = Mockito.mock(Player.class);
    mockPiece = Mockito.mock(Piece.class);
    testGameHolder.setPlayers(new Players());
    testGameHolder.setCurrentPlayer(mockPlayer);
    testGameHolder.setPieces(List.of(mockPiece));
  }

  @Test
  void testGetPieceAndPlayerByIdReturnEmptyOptionalSinceTileIsMocked(){
    GameObserver mockGameObserver = Mockito.mock(GameObserver.class);
    testGameHolder.register(mockGameObserver);
    testGameHolder.notifyGameEnd();
    assertTrue(testGameHolder.getPieceById("").isEmpty());
    assertTrue(testGameHolder.getPlayerById("").isEmpty());
    assertTrue(testGameHolder.getTileById("").isEmpty());
  }



}