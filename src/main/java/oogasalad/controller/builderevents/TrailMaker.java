package oogasalad.controller.builderevents;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.transform.Transform;
import oogasalad.view.Coordinate;

public class TrailMaker implements TrailMakerAPI {

  private static final double INVISIBLE = 0.0;
  private static final double VISIBLE = 1.0;
  private final Map<String, LineHolder> myMap;
  private final Pane myPane;
  private BooleanProperty myEnabledLines;
  private double myCurrentOpacity;
  private ReadOnlyObjectProperty<Bounds> myTest;

  public TrailMaker(Pane parentPane, boolean setEnable) {
    myMap = new HashMap<>();
    myPane = parentPane;
    myCurrentOpacity = setEnable ? 1.0 : 0.0;
    initializeEnabler();
    myEnabledLines.set(setEnable);
  }

  @Override
  public void createTrailBetween(Node entry1, Node entry2, String trailID) {
    Coordinate startPoint = getCenterPoint(entry1);
    Coordinate endPoint = getCenterPoint(entry2);
    Line newLine = new Line(startPoint.getXCoor(), startPoint.getYCoor(),
        endPoint.getXCoor(), endPoint.getYCoor());
    LineHolder trail = new LineHolder(newLine, entry1, entry2);
    //initializeFirstNodeListener(trail);
    //initializeSecondNodeListener(trail);
    newLine.setOpacity(myCurrentOpacity);
    newLine.setFill(Color.BLACK);
    myMap.put(trailID, trail);
    myPane.getChildren().add(newLine);

    System.out.println("made trail");
  }

  @Override
  public void removeTrail(String trailID) {
    myPane.getChildren().remove(myMap.get(trailID).line());
    myMap.remove(trailID);
  }

  @Override
  public void setOpacity(String trailID, double opacity) {
    myMap.get(trailID).line().setOpacity(opacity);
  }

  @Override
  public double getOpacity(String trailID) {
    return myMap.get(trailID).line().getOpacity();
  }

  @Override
  public void toggleEnable() {
    System.out.println("Lines currently visible: " + this.myEnabledLines.get());
    this.myEnabledLines.set(!this.myEnabledLines.get());
  }

  private Coordinate getCenterPoint(Node entry) {
    return new Coordinate(
        (entry.getBoundsInParent().getMinX() + entry.getBoundsInParent().getMaxX()) * 0.5,
        (entry.getBoundsInParent().getMinY() + entry.getBoundsInParent().getMaxY()) * 0.5
    );
  }

  private void updateTrailLocation(String trailID, Node updatedNode,
      BiFunction<String, Coordinate, Integer> pointMethod) {
    myPane.getChildren().remove(myMap.get(trailID));
    Coordinate newCenter = getCenterPoint(updatedNode);
    pointMethod.apply(trailID, newCenter);
    myPane.getChildren().add(myMap.get(trailID).line());
  }

  private void setStartingPoint(Line line, double newX, double newY) {
    line.setStartX(newX);
    line.setStartY(newY);
  }

  private void setEndingPoint(Line line, double newX, double newY) {
    line.setEndX(newX);
    line.setEndY(newY);
  }

  private void initializeEnabler() {
    myEnabledLines = new SimpleBooleanProperty();
    myEnabledLines.addListener(((observable, oldValue, newValue) -> {
      if (newValue) {
        updateOpacity(VISIBLE);
      } else {
        updateOpacity(INVISIBLE);
      }
    }));
  }

  private void initializeFirstNodeListener(LineHolder trail){
    myTest = trail.startNode().layoutBoundsProperty();
    myTest.addListener( ((observable, oldValue, newValue) -> {
      if (newValue.getMinX() != oldValue.getMinX() || newValue.getMinY() != oldValue.getMinY()){
        System.out.println("DOING THE MOVE");
        setStartingPoint(
            trail.line(),
            trail.startNode().getBoundsInParent().getMinX(),
            trail.startNode().getBoundsInParent().getMinY()
        );
      }
    }));
  }

  private void changeOpacityOfAllLines(double opacity) {
    for (String id : myMap.keySet()) {
      myMap.get(id).line().setOpacity(opacity);
    }
  }
  private void updateOpacity(double opacity){
//    System.out.println("Making Opacity " + opacity);
    myCurrentOpacity = opacity;
    changeOpacityOfAllLines(opacity);
  }

//  private void createListenerOnBounds(Node entry, BiFunction<String, Coordinate, Integer> pointMethod){
//    entry.localToScene(entry.getBoundsInLocal());
//    entry.localToSceneTransformProperty().addListener(((observable, oldValue, newValue) -> {
//      if (newValue){
//
//      }
//      else{
//
//      }
//    }));
//  }
}

/**
 * LineHolder record will be used to link together a line and it's corresponding nodes.
 * @param line line that is the trail between the two nodes
 * @param startNode the first node clicked-- the starting point
 * @param endNode the second node clicked-- the ending point.
 */
record LineHolder(Line line, Node startNode, Node endNode){ };
