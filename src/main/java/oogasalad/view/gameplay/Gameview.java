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
import oogasalad.model.attribute.StringAttribute;
import oogasalad.model.constructable.GameHolder;
import oogasalad.model.constructable.Piece;
import oogasalad.model.constructable.Player;
import oogasalad.model.constructable.Players;
import oogasalad.model.constructable.Tile;
import oogasalad.model.observers.GameObserver;
import oogasalad.view.Renderable;
import oogasalad.view.gameplay.Players.ViewPlayers;
import oogasalad.view.gameplay.pieces.ImageCard;
import oogasalad.view.gameplay.pieces.ViewPieces;
import oogasalad.view.gameplay.pieces.Cards;
import oogasalad.view.gameplay.pieces.PlayerPiece;
import oogasalad.view.gameplay.popup.CardDisplayPopup;
import oogasalad.view.gameplay.popup.HandDisplayPopup;
import oogasalad.view.tiles.Tiles;
import oogasalad.view.tiles.ViewTile;

public class Gameview implements GameObserver {

  //TODO: refactor to read from JSON file
  private final int VIEW_WIDTH = 1500;
  private final int VIEW_HEIGHT = 1000;
  private final GameHolder game;
  private final Provider<Piece> pieceProvider;
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
  private HandDisplayPopup popup;

  @Inject
  public Gameview(
      @Assisted GameController gc,
      GameHolder game,
      Provider<Player> playerProvider,
      Provider<Piece> pieceProvider) {
    this.gc = gc;
    this.game = game;
    this.pieceProvider = pieceProvider;
    this.playerObjectProperty = new ArrayList<>();
    this.playerPieceObjectProperty = new ArrayList<>();
    this.game.register(this);
  }

  public void renderGameview(Stage primaryStage) throws IOException {
    myStage = primaryStage;
    UIroot = new BorderPane();

    Renderable board = new Board();
    board.render(UIroot);

    // TODO: use either Tiles or BBoard, not both!
    tiles = new Tiles(game.getBoard().getTiles());
    tiles.render(UIroot);

    die = new Die();
    die.render(UIroot);

    //TODO: retrieve number of players and piece per player from launcher/builder
    // TODO: Dynamically watch players/pieces


    //TODO: use button creator to make button
    Button button = new Button("Show Card Popup");

    button.setOnAction(event -> {
      if (popup != null) {popup.hideHand();}
      popup = new HandDisplayPopup();
      //TODO: only pass in Player cards
      Cards cards = new Cards(game.getBoard().getTiles());
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
