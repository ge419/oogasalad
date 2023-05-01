package oogasalad.model.engine.actions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import java.util.List;
import java.util.ResourceBundle;
import oogasalad.model.attribute.AttributeModule;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.BBoard;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Piece;
import oogasalad.model.constructable.Player;
import oogasalad.model.constructable.Players;
import oogasalad.model.constructable.Tile;
import oogasalad.model.engine.EventEmitter;
import oogasalad.model.engine.prompt.TestPrompterNegative;
import oogasalad.model.engine.prompt.TestPrompterPositive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreatePlayersActionTest {

  private GameHolder gameHolder;
  private Provider playerProvider;
  private Provider pieceProvider;
  private EventEmitter emitter;
  private ResourceBundle bundle;
  private CreatePlayersAction action;
  private ActionParams actionParams;


  @BeforeEach
  public void setUp() {
    Injector injector = Guice.createInjector(new AttributeModule());
    SchemaDatabase db = injector.getInstance(SchemaDatabase.class);

    BBoard board = new BBoard();
    board.setTiles(List.of(new Tile(db)));

    gameHolder = new GameHolder();
    gameHolder.setBoard(board);

    playerProvider = mock(Provider.class);
    pieceProvider = mock(Provider.class);

    when(playerProvider.get()).thenReturn(new Player(db));
    when(pieceProvider.get()).thenReturn(new Piece(db));

    emitter = mock(EventEmitter.class);
    bundle = mock(ResourceBundle.class);
    when(bundle.getString(any(String.class))).thenReturn("");

    action = new CreatePlayersAction(playerProvider, pieceProvider, gameHolder, 1, 2, bundle);
  }

  @Test
  public void correctNumberOfPlayersCreated() {
    actionParams = new ActionParams(emitter, new TestPrompterPositive());
    action.runAction(actionParams);

    verify(playerProvider, times(1)).get();
    assertEquals(1, gameHolder.getPlayers().getList().size());
  }

  @Test
  public void correctNumberOfPiecesSet() {
    actionParams = new ActionParams(emitter, new TestPrompterPositive());
    action.runAction(actionParams);

    assertEquals(1, gameHolder.getPlayers().getList().get(0).getPieces().size());
  }

}

