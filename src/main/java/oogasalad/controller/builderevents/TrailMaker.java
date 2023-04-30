package oogasalad.controller.builderevents;

import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import oogasalad.view.Coordinate;
import oogasalad.view.builder.Arrow;
import oogasalad.view.builder.BuilderView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p>TrailMaker creates trails between two selected nodes by placing an arrow between them.</p>
 * @see TrailMakerAPI
 *
 * @author tmh85
 * @author dcm67
 */
public class TrailMaker implements TrailMakerAPI {

  private static final Logger LOG = LogManager.getLogger(TrailMaker.class);
  private static final double INVISIBLE = 0.0;
  private static final double VISIBLE = 1.0;
  private final Map<String, LineHolder> myMap;
  private final Pane myPane;
  private BooleanProperty myEnabledLines;
  private double myCurrentOpacity;

  public TrailMaker(Pane parentPane, boolean setEnable) {
    myMap = new HashMap<>();
    myPane = parentPane;
    myCurrentOpacity = setEnable ? VISIBLE : INVISIBLE;
    initializeEnabler();
    myEnabledLines.set(setEnable);
  }

  @Override
  public void createTrailBetween(Node entry1, Node entry2, String trailID) {
    Arrow newLine = createTrail(entry1, entry2);
    LineHolder trail = new LineHolder(newLine, entry1, entry2);
    newLine.setOpacity(myCurrentOpacity);
    newLine.setFill(Color.BLACK);
    myMap.put(trailID, trail);
    myPane.getChildren().add(newLine);
    LOG.info("Created a new trail of ID: " + trailID);
  }

  @Override
  public void removeTrail(String trailID) {
    System.out.println(myMap.keySet());
    System.out.println(trailID);
    myPane.getChildren().remove(myMap.get(trailID).line());
    myMap.remove(trailID);

    LOG.info("Removed trail of ID: " + trailID);
  }

  @Override
  public void removeTrail(Node node){
    for (LineHolder trail : myMap.values()){
      if (trail.startNode().equals(node) || trail.endNode().equals(node)){
        removeTrail(getTrailID(trail.startNode(), trail.endNode()));
      }
    }
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
    myEnabledLines.set(!myEnabledLines.get());
    LOG.info("Toggled guidelines enable to: " + myEnabledLines.get());
  }

  @Override
  public String getTrailID(Node entry1, Node entry2) {
    for (String ID : myMap.keySet()) {
      if (myMap.get(ID).startNode().equals(entry2)) {
        if (myMap.get(ID).endNode().equals(entry1)) {
          return ID;
        }
      }
    }
    LOG.warn("Could not find trail ID for start node ID " + entry1.getId() + " and end node ID " + entry2.getId());
    return null;
  }

  @Override
  public void setColor(String trailID, Color color) {
    myMap.get(trailID).line().setFill(color);
  }

  @Override
  public void setAllColors(Color color) {
    for (String ID : myMap.keySet()) {
      setColor(ID, color);
    }
  }

  @Override
  public Paint getColor(String trailID) {
    return myMap.get(trailID).line().getFill();
  }

  private Coordinate getCenterPoint(Bounds bounds) {
    return new Coordinate(
        (bounds.getMinX() + bounds.getMaxX()) * 0.5,
        (bounds.getMinY() + bounds.getMaxY()) * 0.5,
        0
    );
  }

  private void bindLine(Node entry, DoubleProperty x, DoubleProperty y) {
    setPoints(entry.getBoundsInParent(), x, y);

    entry.boundsInParentProperty().addListener(((observable, oldValue, newValue) -> {
      setPoints(newValue, x, y);
    }));
  }

  private void setPoints(Bounds bounds, DoubleProperty x, DoubleProperty y) {
    Coordinate point = getCenterPoint(bounds);
    x.set(point.getXCoor());
    y.set(point.getYCoor());
  }

  private Arrow createTrail(Node entry1, Node entry2) {
    Arrow newLine = new Arrow();
    bindLine(entry2, newLine.startXProperty(), newLine.startYProperty());
    bindLine(entry1, newLine.endXProperty(), newLine.endYProperty());
    return newLine;
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

  private void changeOpacityOfAllTrails(double opacity) {
    for (String id : myMap.keySet()) {
      myMap.get(id).line().setOpacity(opacity);
    }
  }

  private void updateOpacity(double opacity) {
    myCurrentOpacity = opacity;
    changeOpacityOfAllTrails(opacity);
  }

}

/**
 * <p>LineHolder record will be used to link together a line and it's corresponding nodes.</p>
 *
 * @param line      line that is the trail between the two nodes
 * @param startNode the first node clicked-- the starting point
 * @param endNode   the second node clicked-- the ending point.
 */
record LineHolder(Arrow line, Node startNode, Node endNode) {

};
