package oogasalad.gameplay_frontend;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import oogasalad.gameplay_frontend.Tiles.BasicTile;

public class Board{
  //TODO: refactor to read from property file
  private final int BOARD_SIZE = 700;

  Canvas boardCanvas;

  public Board() {
    this.boardCanvas= new Canvas(BOARD_SIZE, BOARD_SIZE);
  }

  public Canvas render() {
    GraphicsContext gc = boardCanvas.getGraphicsContext2D();
    gc.setFill(Color.BLUE);
    gc.fillRect(0, 0, BOARD_SIZE, BOARD_SIZE);
    return boardCanvas;
  }
}

