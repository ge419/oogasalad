package oogasalad.model.constructable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.guice.ObjectMapperModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.xml.validation.Schema;
import oogasalad.controller.GameInfo;
import oogasalad.model.attribute.AttributeModule;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.attribute.SimpleSchemaDatabase;
import oogasalad.model.engine.rules.BuyTileRule;
import oogasalad.model.engine.rules.EditableRule;
import oogasalad.model.engine.rules.Rule;
import oogasalad.model.observers.GameObserver;
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
    assertTrue(gameHolder.getTileById(id1).isPresent());
  }

//  @Test
//  void testResetOwners() {
//    Injector injector = Guice.createInjector(new AttributeModule());
//    db = injector.getInstance(SchemaDatabase.class);
//
//    Tile tile1 = new Tile(db);
//    tile1.setOwnerId("a");
//    String id1 = tile1.getOwnerId();
//
//    Tile tile2 = new Tile(db);
//    tile2.setOwnerId("b");
//    String id2 = tile2.getOwnerId();
//
//    BBoard board = new BBoard();
//    board.addTile(tile1);
//    board.addTile(tile2);
//
//    gameHolder.setBoard(board);
//    gameHolder.resetOwners("a");
//
//    assertEquals(tile1.getOwnerId(), "a");
//    assertEquals(tile2.getOwnerId(), "a");
//  }

  @Test
  void testRules() {
    Injector injector = Guice.createInjector(new AttributeModule());
    db = injector.getInstance(SchemaDatabase.class);

    ResourceBundle bundle = mock(ResourceBundle.class);
    when(bundle.getString(any(String.class))).thenReturn("");

    Player player = new Player(db);
    gameHolder = new GameHolder();
    gameHolder.setPlayers(new Players(List.of(player)));
    gameHolder.setCurrentPlayer(player);

    EditableRule buyRule = new BuyTileRule(db, gameHolder, bundle);
    List<Rule> rules = new ArrayList<>();
    rules.add(buyRule);
    gameHolder.setRules(rules);
    assertEquals(buyRule, gameHolder.getRules().get(0));
    assertEquals(rules, gameHolder.rulesProperty());
  }

//  @Testq
//  void testObservers() {
//    GameObserver gameObserver = new GameObserver() {
//      @Override
//      public void updateOnGameEnd() {
//
//      }
//    }
//  }
}
