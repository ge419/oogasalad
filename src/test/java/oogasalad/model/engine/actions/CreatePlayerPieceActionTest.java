package oogasalad.model.engine.actions;

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
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Piece;
import oogasalad.model.constructable.Player;
import oogasalad.model.constructable.Players;
import oogasalad.model.engine.EventEmitter;
import oogasalad.model.engine.actions.creation.CreatePlayerPieceAction;
import oogasalad.model.engine.prompt.TestPrompterPositive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreatePlayerPieceActionTest {

  private GameHolder gameHolder;
  private EventEmitter emitter;
  private ResourceBundle bundle;
  private ActionParams actionParams;
  private CreatePlayerPieceAction action;
  private Provider provider;
  private Player player1;
  private Player player2;


  @BeforeEach
  public void setUp() {
    Injector injector = Guice.createInjector(new AttributeModule());
    SchemaDatabase db = injector.getInstance(SchemaDatabase.class);

    gameHolder = new GameHolder();
    player1 = mock(Player.class);
    player2 = mock(Player.class);
    Players players = new Players(List.of(player1, player2));
    gameHolder.setPlayers(players);

    provider = mock(Provider.class);
    when(provider.get()).thenReturn(new Piece(db));

    emitter = mock(EventEmitter.class);
    bundle = mock(ResourceBundle.class);
    when(bundle.getString(any(String.class))).thenReturn("");

    action = new CreatePlayerPieceAction(provider, gameHolder, bundle);
  }

  @Test
  public void correctNumberOfPiecesCreated() {
    actionParams = new ActionParams(emitter, new TestPrompterPositive());
    action.runAction(actionParams);

    verify(provider, times(2)).get();
  }

}