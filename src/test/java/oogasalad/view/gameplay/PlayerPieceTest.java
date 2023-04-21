//package oogasalad.view.gameplay;
//
//import static org.testfx.api.FxAssert.verifyThat;
//import static org.testfx.matcher.base.NodeMatchers.isVisible;
//
//import javafx.scene.Scene;
//import javafx.scene.layout.BorderPane;
//import javafx.stage.Stage;
//import oogasalad.view.gameplay.pieces.PlayerPiece;
//import org.junit.jupiter.api.Test;
//import org.testfx.framework.junit5.ApplicationTest;
//
//public class PlayerPieceTest extends ApplicationTest {
//
//  @Override
//  public void start(Stage stage) {
//    BorderPane pane = new BorderPane();
//    PlayerPiece playerPiece = new PlayerPiece("data/example/piece_1.png", "playerName");
//    playerPiece.setId("player-piece");
//    pane.getChildren().add(playerPiece);
//    Scene scene = new Scene(pane, 800, 800);
//    stage.setScene(scene);
//    stage.show();
//  }
//
//
//  @Test
//  public void testPieceVisibility() {
//    verifyThat("#player-piece", isVisible());
//  }
//
//}
