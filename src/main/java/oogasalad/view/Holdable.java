package oogasalad.view;

import javafx.geometry.Point2D;
import javafx.scene.Node;

public interface Holdable {

  void showHand(Node anchor, Point2D offset);

  void hideHand();


}
