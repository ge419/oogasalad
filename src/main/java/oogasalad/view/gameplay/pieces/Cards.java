package oogasalad.view.gameplay.pieces;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import oogasalad.view.Backgroundable;
import oogasalad.view.Coordinate;
import oogasalad.view.Imageable;
import oogasalad.view.Textable;

public class Cards extends GamePiece implements Backgroundable, Textable, Imageable {


  public Cards (String imageURL) {
    super(imageURL);
//    StackPane cardBack = new StackPane();
//    cardBack.setCenter(this);
    //TODO: take this next part out
    imageURL = "view.gameplay/go.jpg";
    ImageView cardImage = createImage(50, imageURL);
    Rectangle background = tileBackground(50,70);
    VBox content = new VBox(70/10, cardImage, createTextBox("Test Text", 70, 50));
    content.setAlignment(Pos.CENTER);
    getChildren().addAll(background, content);
  }


  @Override
  public void move(Coordinate[] coorArray) {

  }

  public void setSelected(boolean selected) {
    if (selected) {
      setTranslateY(getTranslateY() - 10);
    } else {
      setTranslateY(getTranslateY() + 10);
    }
  }

  @Override
  public VBox createTextBox(String info, double height, double width) {
    VBox textBox = new VBox();
    String[] infoList = info.split(",");
    for (int i = 0; i < infoList.length; i++) {
      Text text = new Text(infoList[i]);
      resizeText(text, height, 8, width);
      textBox.getChildren().add(text);
    }
    textBox.setAlignment(Pos.CENTER);
    return textBox;
  }
}
