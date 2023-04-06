package oogasalad.gameplay_frontend;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javax.swing.text.html.ImageView;

public class Board extends Canvas {
  //TODO: refactor to read from property file
  private static final int BOARD_SIZE = 500;

  public Board(String imgPath) {
    super(BOARD_SIZE, BOARD_SIZE);
    File imageFile = new File("data/example/monopoly_board.jpg");
    try {
      Image image = new Image(new FileInputStream(imageFile));
      int imageWidth = (int) image.getWidth();
      int imageHeight = (int) image.getHeight();

      GraphicsContext gc = this.getGraphicsContext2D();

      double scaleX = (double) BOARD_SIZE/ imageWidth;
      double scaleY = (double) BOARD_SIZE / imageHeight;
      double scale = Math.min(scaleX, scaleY);

      double scaledWidth = imageWidth * scale;
      double scaledHeight = imageHeight * scale;
      double x = (BOARD_SIZE - scaledWidth) / 2;
      double y = (BOARD_SIZE - scaledHeight) / 2;

      gc.drawImage(image, x, y, scaledWidth, scaledHeight);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

//  private Image scaleImage(Image) {
//
//  }
}

