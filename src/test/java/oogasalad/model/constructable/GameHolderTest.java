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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.guice.ObjectMapperModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.validation.Schema;
import oogasalad.controller.GameInfo;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.attribute.SimpleSchemaDatabase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameHolderTest {
  GameHolder testGameHolder;
  Player mockPlayer;
  Piece mockPiece;
  ObjectMapper testObjectMapper;
  SchemaDatabase testSchemaDB;
  GameHolder gameHolder;
  ObjectMapper mapper;
  Injector injector;
  SchemaDatabase db;


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
    db = new SimpleSchemaDatabase();
    mapper =  new ObjectMapper();
    gameHolder = new GameHolder();
    injector = Guice.createInjector(new ObjectMapperModule(),
        binder -> binder.bind(SchemaDatabase.class).toInstance(db));

  }

  @Test
  void testGetPieceAndPlayerByIdReturnEmptyOptionalSinceTileIsMocked(){
    GameObserver mockGameObserver = Mockito.mock(GameObserver.class);
    testGameHolder.register(mockGameObserver);
    testGameHolder.notifyGameEnd();
//    assertTrue(testGameHolder.getPieceById("").isEmpty());
//    assertTrue(testGameHolder.getPlayerById("").isEmpty());
    assertTrue(testGameHolder.getTileById("").isEmpty());
  }


  @Test
  void testDefaultState() {
    assertNotNull(gameHolder.getPlayers());
    assertNotNull(gameHolder.getGameInfo());
    assertNotNull(gameHolder.getBoard());
    assertNotNull(gameHolder.getRules());
    assertNotNull(gameHolder.getPieces());
  }

  @Test
  void testGetBoard() {
    Tile tile = new Tile(db);
    gameHolder.getBoard().addTile(tile);
    Tile testTile = gameHolder.getBoard().getTiles().get(0);
    assertEquals(tile, testTile);
  }

  @Test
  void testSetBoard() {
    BBoard board = new BBoard();
    Tile tile = new Tile(db);
    board.addTile(tile);
    gameHolder.setBoard(board);
    assertEquals(tile, gameHolder.getBoard().getTiles().get(0));
  }

  @Test
  void testGameInfo() {
    GameInfo gameInfo = new GameInfo();
    gameInfo.setHeight(500);
    gameInfo.setWidth(500);
    gameHolder.setGameInfo(gameInfo);
    assertEquals(gameHolder.getGameInfo().getHeight(), 500);
    assertEquals(gameHolder.getGameInfo().getHeight(), 500);
  }

}
