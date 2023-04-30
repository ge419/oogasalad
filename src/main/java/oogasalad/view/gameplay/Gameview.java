package oogasalad.view.gameplay;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.assistedinject.Assisted;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import oogasalad.controller.GameController;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Piece;
import oogasalad.model.constructable.Player;
import oogasalad.model.constructable.Players;
import oogasalad.model.observers.GameObserver;
import oogasalad.view.Renderable;
import oogasalad.view.ViewFactory;
import oogasalad.view.gameplay.Players.ViewPlayers;
import oogasalad.view.gameplay.pieces.Cards;
import oogasalad.view.gameplay.pieces.PlayerPiece;
import oogasalad.view.gameplay.pieces.ViewPieces;
import oogasalad.view.gameplay.popup.HandDisplayPopup;
import oogasalad.view.tiles.Tiles;
import oogasalad.view.tiles.ViewTile;

public class Gameview implements GameObserver {

  /**
   * <p> The "Main" class for the gameplay frontend that renders all frontend components and
   * displays in window
   *
   * <p>Assumptions: Any additional frontend components must implement the Renderable interface and be
   * added to the renderGameview method
   *
   * <p>Dependencies: GameHolder, Tiles, Die, ViewPlayers, ViewPieces
   *
   * @author Woonggyu wj61
   */

  //TODO: refactor to read from JSON file
  private final int VIEW_WIDTH = 1500;
  private final int VIEW_HEIGHT = 1000;
  private final GameHolder game;
  private final Provider<Piece> pieceProvider;
  private final ViewFactory viewFactory;
  private List<ObjectProperty<Player>> playerObjectProperty;
  private List<ObjectProperty<PlayerPiece>> playerPieceObjectProperty;
  private Tiles tiles;
  private Die die;
  private final GameController gc;
  private Scene scene;
  private BorderPane UIroot;
  private ViewPlayers viewPlayers = new ViewPlayers(null);
  private Stage myStage;
  private ViewPieces viewPieces;

  @Inject
  public Gameview(
      @Assisted GameController gc,
      GameHolder game,
      Provider<Player> playerProvider,
      Provider<Piece> pieceProvider,
      ViewFactory viewFactory) {
    this.gc = gc;
    this.game = game;
    this.pieceProvider = pieceProvider;
    this.playerObjectProperty = new ArrayList<>();
    this.playerPieceObjectProperty = new ArrayList<>();
    this.viewFactory = viewFactory;
    this.game.register(this);
  }

  /**
   * Receive the frontend player component that is associated with the backend player with specified ID
   *
   * <p>Assumptions: ID passed is valid.
   *
   * <p>Parameters:
   * @param primaryStage is the stage that all frontend components are to be added to
   *
   * <p>Exceptions:
   * @throws IOException if any frontend components' render method is faulty
   *
   */
  public void renderGameview(Stage primaryStage) throws IOException {
    myStage = primaryStage;
    UIroot = new BorderPane();

    Renderable board = new Board();
    board.render(UIroot);

    // TODO: use either Tiles or BBoard, not both!
    tiles = viewFactory.makeTiles(game.getBoard().getTiles());
    tiles.render(UIroot);

    die = new Die();
    die.render(UIroot);

    //TODO: retrieve number of players and piece per player from launcher/builder
    // TODO: Dynamically watch players/pieces

    //TODO: take this out when cards are implemented
    Button button = new Button("Show Card Popup");
    button.setOnAction(event -> {
      Cards cards = viewFactory.makeCards(game.getBoard().getTiles());
      HandDisplayPopup popup = new HandDisplayPopup();
      cards.render(popup);
      List<ViewTile> cardList = cards.getCardList();
      popup.addCards(cardList);
      Point2D offset = new Point2D(UIroot.getLayoutX(), UIroot.getLayoutY());
      popup.showHand(UIroot, offset);
    });


    HBox hbox = new HBox();
    hbox.getChildren().addAll(button);

    button.setId("Button");
    UIroot.setTop(hbox);


    scene = new Scene(UIroot);


    //TODO: refactor to read from property file
    myStage.setTitle("Monopoly");
    myStage.setScene(scene);
    myStage.setHeight(VIEW_HEIGHT);
    myStage.setWidth(VIEW_WIDTH);
    myStage.show();
  }

  public <T extends Event> void addEventHandler(EventType<T> type, EventHandler<T> action) {
    scene.addEventHandler(type, action);
  }

  public Die getDie() {
    return this.die;
  }


  @Override
  public void updateOnPlayers(Players players) {
    viewPlayers = new ViewPlayers(game.getPlayers());
    viewPlayers.render(UIroot);
  }

  @Override
  public void updateOnPieces(List<Piece> pieces) {
    viewPieces = new ViewPieces(game.getPieces());
    viewPieces.render(UIroot);
  }

  @Override
  public void updateOnPlayerRemoval(List<Player> players) {
    //TODO: make the player pieces be removed
    List<String> ids = new ArrayList<>();
    List<Piece> pieces = new ArrayList<>();
    for (Player p : players) {
      ids.add(p.getId());
      pieces.addAll(p.getPieces());
    }
    for (Piece piece : pieces) {
      UIroot.getChildren().remove(viewPieces.getViewPieceByBPiece(piece));
    }
    for (String id : ids) {
      UIroot.getChildren().remove(viewPlayers.getPlayer(id));
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Information Dialog");
      alert.setHeaderText("Game Change!");
      alert.setContentText(String.format("Player %s Gets Removed!", id));
      alert.showAndWait();
    }
  }

  @Override
  public void updateOnGameEnd() {
    myStage.close();
  }
}
