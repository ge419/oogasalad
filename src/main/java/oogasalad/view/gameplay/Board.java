package oogasalad.view.gameplay;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import oogasalad.view.Renderable;

/**
 * <p> Frontend rendering of the board that the tiles go around
 *
 * <p>Assumptions: None
 *
 * <p>Dependencies: Renderable interface
 *
 * @author Woonggyu wj61
 */

public class Board extends Canvas implements Renderable {

  //TODO: refactor to read from property file
  private static final int BOARD_SIZE = 500;

  public Board() {
    super(BOARD_SIZE, BOARD_SIZE);

    File imageFile = new File("data/example/monopoly_board.jpg");
    try {
      Image image = new Image(new FileInputStream(imageFile));
      int imageWidth = (int) image.getWidth();
      int imageHeight = (int) image.getHeight();

      GraphicsContext gc = this.getGraphicsContext2D();

      double scaleX = (double) BOARD_SIZE / imageWidth;
      double scaleY = (double) BOARD_SIZE / imageHeight;
      double scale = Math.min(scaleX, scaleY);

      double scaledWidth = imageWidth * scale;
      double scaledHeight = imageHeight * scale;
      double x = (BOARD_SIZE - scaledWidth) / 2;
      double y = (BOARD_SIZE - scaledHeight) / 2;

      gc.drawImage(image, x, y, scaledWidth, scaledHeight);

    } catch (FileNotFoundException e) {
      System.out.println("No such file!");
      e.printStackTrace();
    }
  }

  /**
   * Render the board onto the window
   *
   * <p>Assumptions: Board is at the center of the window
   *
   * <p>Parameters:
   * @param pane the BorderPane that the board is to be added to
   *
   * <p>Exceptions: none
   *
   * <p>Other details: None
   */
  @Override
  public void render(BorderPane pane) {
    this.setId("Board");
    pane.setCenter(this);
  }

}

