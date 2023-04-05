package oogasalad.gameplay_frontend;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Board{

  //Read in from property file
  private final int BOARD_SIZE = 700;
  public Board() {
  }

  public Canvas render() {
    Canvas boardCanvas = new Canvas(BOARD_SIZE, BOARD_SIZE);
    GraphicsContext gc = boardCanvas.getGraphicsContext2D();
    gc.setFill(Color.BLUE);
    gc.fillRect(0, 0, BOARD_SIZE, BOARD_SIZE);
    return boardCanvas;
  }
}
