package oogasalad.model.constructable;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.guice.ObjectMapperModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.xml.validation.Schema;
import oogasalad.controller.GameInfo;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.attribute.SimpleSchemaDatabase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameHolderTest {

  GameHolder gameHolder;
  ObjectMapper mapper;
  Injector injector;
  SchemaDatabase db;

  @BeforeEach
  void setup() {
    db = new SimpleSchemaDatabase();
    mapper =  new ObjectMapper();
    gameHolder = new GameHolder();
    injector = Guice.createInjector(new ObjectMapperModule(),
        binder -> binder.bind(SchemaDatabase.class).toInstance(db));
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

  @Test
  void testPlayers() {
    List<Player> playerList = new ArrayList<>();
    Player player = new Player(db);
    playerList.add(player);
    Players players = new Players();
    players.setPlayers(playerList);
    gameHolder.setPlayers(players);
    assertEquals(player, gameHolder.getPlayers().getList().get(0));

  }

  @Test
  void testCurrentPlayer() {
    List<Player> playerList = new ArrayList<>();
    Player player1 = new Player(db);
    Player player2 = new Player(db);
    playerList.add(player1);
    playerList.add(player2);
    Players players = new Players(playerList);
    gameHolder.setPlayers(players);
    gameHolder.setCurrentPlayer(player2);
    assertEquals(player2, gameHolder.getCurrentPlayer());

    gameHolder.setCurrentPlayer(player1);
    assertEquals(player1, gameHolder.getCurrentPlayer());
  }

  @Test
  void testRemovePlayers() {
    List<Player> playerList = new ArrayList<>();
    Player player1 = new Player(db);
    Player player2 = new Player(db);
    playerList.add(player1);
    playerList.add(player2);
    Players players = new Players(playerList);
    gameHolder.setPlayers(players);
    gameHolder.removePlayers(playerList);
    assertTrue(gameHolder.getPlayers().getList().isEmpty());
  }

  @Test
  void testPieces() {
    Piece piece = new Piece(db);
    List<Piece> pieceList = new ArrayList<>();
    pieceList.add(piece);

    gameHolder.setPieces(pieceList);
    assertEquals(piece, gameHolder.getPieces().get(0));
  }

  @Test
  void testTiles() {
    Tile tile1 = new Tile(db);
    Tile tile2 = new Tile(db);
    Tile tile3 = new Tile(db);
    BBoard board = new BBoard();
    board.addTile(tile1);
    board.addTile(tile2);
    board.addTile(tile3);
    gameHolder.setBoard(board);
    String id1 = gameHolder.getBoard().getTiles().get(0).getId();
    assertEquals(gameHolder.getBoard().getTileCount(), 3);
    assertTrue(gameHolder.getTileById(id1).isPresent());
  }
}
