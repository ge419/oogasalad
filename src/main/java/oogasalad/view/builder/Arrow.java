package oogasalad.view.builder;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

/**
 * <p>A basic arrow shape.</p>
 * <p>Obtained from the provided StackOverflow link-- thank you Fabian! </p>
 *
 * @see <a href="https://stackoverflow.com/a/41353991">Original Arrow Code by Fabian</a>
 */
public class Arrow extends Group {

  private final Line line;
  private final Line myArrow1;
  private final Line myArrow2;

  public Arrow() {
    this(new Line(), new Line(), new Line());
  }

  private static final double arrowLength = 20;
  private static final double arrowWidth = 7;

  private Arrow(Line line, Line arrow1, Line arrow2) {
    super(line, arrow1, arrow2);
    this.line = line;
    this.myArrow1 = arrow1;
    this.myArrow2 = arrow2;
    InvalidationListener updater = o -> {
      double ex = getEndX();
      double ey = getEndY();
      double sx = getStartX();
      double sy = getStartY();

      arrow1.setEndX(ex);
      arrow1.setEndY(ey);
      arrow2.setEndX(ex);
      arrow2.setEndY(ey);

      if (ex == sx && ey == sy) {
        // arrow parts of length 0
        arrow1.setStartX(ex);
        arrow1.setStartY(ey);
        arrow2.setStartX(ex);
        arrow2.setStartY(ey);
      } else {
        double factor = arrowLength / Math.hypot(sx - ex, sy - ey);
        double factorO = arrowWidth / Math.hypot(sx - ex, sy - ey);

        // part in direction of main line
        double dx = (sx - ex) * factor;
        double dy = (sy - ey) * factor;

        // part ortogonal to main line
        double ox = (sx - ex) * factorO;
        double oy = (sy - ey) * factorO;

        arrow1.setStartX(ex + dx - oy);
        arrow1.setStartY(ey + dy + ox);
        arrow2.setStartX(ex + dx + oy);
        arrow2.setStartY(ey + dy - ox);
      }
    };

    // add updater to properties
    startXProperty().addListener(updater);
    startYProperty().addListener(updater);
    endXProperty().addListener(updater);
    endYProperty().addListener(updater);
    updater.invalidated(null);
  }

  // start/end properties

  public final void setStartX(double value) {
    line.setStartX(value);
  }

  public final double getStartX() {
    return line.getStartX();
  }

  public final DoubleProperty startXProperty() {
    return line.startXProperty();
  }

  public final void setStartY(double value) {
    line.setStartY(value);
  }

  public final double getStartY() {
    return line.getStartY();
  }

  public final DoubleProperty startYProperty() {
    return line.startYProperty();
  }

  public final void setEndX(double value) {
    line.setEndX(value);
  }

  public final double getEndX() {
    return line.getEndX();
  }

  public final DoubleProperty endXProperty() {
    return line.endXProperty();
  }

  public final void setEndY(double value) {
    line.setEndY(value);
  }

  public final double getEndY() {
    return line.getEndY();
  }

  public final DoubleProperty endYProperty() {
    return line.endYProperty();
  }

  public void setFill(Color color) {
    line.setFill(color);
    myArrow1.setFill(color);
    myArrow2.setFill(color);
  }

  public Paint getFill() {
    return line.getFill();
  }

}
