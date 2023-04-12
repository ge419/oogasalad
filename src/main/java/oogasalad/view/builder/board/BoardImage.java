package oogasalad.view.builder.board;

import java.awt.Dimension;
import oogasalad.view.Coordinate;

public record BoardImage(String imagePath, Coordinate location, Dimension size) {
  //just record stuff, nothing else
}
