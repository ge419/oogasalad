package oogasalad.view.tiles;

import java.util.List;
import javafx.scene.layout.BorderPane;
import oogasalad.model.constructable.Tile;
import oogasalad.view.gameplay.pieces.ImageCard;
import oogasalad.view.gameplay.pieces.StreetCard;
import oogasalad.view.gameplay.popup.HandDisplayPopup;

public class RenderStrategy {

  protected void renderTile(Tile BTile, BorderPane pane, List<ViewTile> tileList) {
    switch (BTile.getViewType()) {
      case "street" -> {
        StreetTile tile = new StreetTile(BTile);
        pane.getChildren().add(tile);
        tileList.add(tile);
      }
      case "image" -> {
        ImageTile tile =  new ImageTile(BTile);
        pane.getChildren().add(tile);
        tileList.add(tile);
      }
      default -> {
        BasicTile tile = new BasicTile(BTile);
        pane.getChildren().add(tile);
        tileList.add(tile);
      }
    }
  }


  public void renderCard(Tile BTile, BorderPane pane, List<ViewTile> cardList) {
    BTile.setAngle(0);
    switch (BTile.getViewType()) {
      case "street" -> {
        StreetCard card = new StreetCard(BTile);
//        pane.getChildren().addAll(card);
        cardList.add(card);
      }
      case "image" -> {
        ImageCard card = new ImageCard(BTile);
//        pane.getChildren().addAll(card);
        cardList.add(card);
      }
//      default -> {
//        BasicTile tile = new BasicTile(BTile);
//        pane.getChildren().add(tile);
//        cardList.add(card);
//      }
    }
  }
}
