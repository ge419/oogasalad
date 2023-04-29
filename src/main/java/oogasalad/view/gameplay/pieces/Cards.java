package oogasalad.view.gameplay.pieces;

import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import oogasalad.view.Backgroundable;
import oogasalad.view.Coordinate;
import oogasalad.view.Imageable;
import oogasalad.view.Textable;
import oogasalad.view.gameplay.popup.CardDisplayPopup;

public class Cards extends GamePiece implements Backgroundable, Textable, Imageable {

  private final String imageURL;

  public Cards (String imageURL) {
    super(imageURL);
    this.imageURL = imageURL;
    //TODO: take this next part out
    imageURL = "view.gameplay/go.jpg";
    ImageView cardImage = createImage(150, imageURL, 1);
    Rectangle background = createBackground(150,190, Color.WHITE, Color.BLACK);
    VBox content = new VBox(70/10, cardImage, createTextBox(List.of("Test Text"), 70, 50));
    content.setAlignment(Pos.CENTER);
    getChildren().addAll(background, content);
//    setOnMouseClicked(this::showPopup);
  }

  private void showPopup(MouseEvent event) {
    CardDisplayPopup popup = new CardDisplayPopup(this);
    popup.showHand(this, new javafx.geometry.Point2D(this.getScene().getX(), this.getScene().getY()));
  }


  @Override
  public void move(Coordinate[] coorArray) {

  }

  public String getImageURL() {
    return imageURL;
  }

  public void setSelected(boolean selected) {
    if (selected) {
      setTranslateY(getTranslateY() - 10);
    } else {
      setTranslateY(getTranslateY() + 10);
    }
  }


  @Override
  public VBox createTextBox(List info, double height, double width) {
    VBox textBox = new VBox();
    for (int i = 0; i < info.size(); i++) {
      Text text = new Text((String)info.get(i));
      resizeText(text, height, 8, width);
      textBox.getChildren().add(text);
    }
    textBox.setAlignment(Pos.CENTER);
    return textBox;
  }
}
