package oogasalad.controller.builderevents;

import com.sun.jdi.InconsistentDebugInfoException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import oogasalad.view.Coordinate;

public class TrailMaker implements TrailMakerAPI {

  private static final double INVISIBLE = 0.0;
  private static final double VISIBLE = 1.0;
  private final Map<String, Line> myMap;
  private final Pane myPane;
  private BooleanProperty myEnabledLines;
  private double myCurrentOpacity;

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
//    Line newLine = new Line(0, 0, 25, 25);
    Line newLine = new Line(startPoint.getXCoor(), startPoint.getYCoor(),
        endPoint.getXCoor(), endPoint.getYCoor());
    newLine.setOpacity(myCurrentOpacity);
    newLine.setFill(Color.BLACK);
    myMap.put(trailID, newLine);
    myPane.getChildren().add(newLine);

    System.out.println("made trail");
  }

  @Override
  public void removeTrail(String trailID) {
    myPane.getChildren().remove(myMap.get(trailID));
    myMap.remove(trailID);
  }

  @Override
  public void setOpacity(String trailID, double opacity) {
    myMap.get(trailID).setOpacity(opacity);
  }

  @Override
  public double getOpacity(String trailID) {
    return myMap.get(trailID).getOpacity();
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
    myPane.getChildren().add(myMap.get(trailID));
  }

  private void setStartingPoint(String trailID, Coordinate desiredCoordinate) {
    myMap.get(trailID).setStartX(desiredCoordinate.getXCoor());
    myMap.get(trailID).setStartY(desiredCoordinate.getYCoor());
  }

  private void setEndingPoint(String trailID, Coordinate desiredCoordinate) {
    myMap.get(trailID).setEndX(desiredCoordinate.getXCoor());
    myMap.get(trailID).setEndY(desiredCoordinate.getYCoor());
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

  private void changeOpacityOfAllLines(double opacity) {
    for (String id : myMap.keySet()) {
      myMap.get(id).setOpacity(opacity);
    }
  }
  private void updateOpacity(double opacity){
    System.out.println("Making Opacity " + opacity);
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
