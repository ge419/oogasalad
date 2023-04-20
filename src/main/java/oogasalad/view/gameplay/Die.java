package oogasalad.view.gameplay;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import oogasalad.view.Renderable;

public class Die extends StackPane implements Renderable {

  private final Rectangle dieFace;
  private final Circle[] dotArray = new Circle[6];

  public Die() {
    final int DICE_SIZE = 50;
    dieFace = new Rectangle(DICE_SIZE, DICE_SIZE);
    dieFace.setFill(Color.WHITE);
    dieFace.setStroke(Color.BLACK);
    getChildren().add(dieFace);

    int dotSize = DICE_SIZE / 8;
    for (int i = 0; i < 6; i++) {
      dotArray[i] = createDot(dotSize);
      addDot(dotArray[i]);
    }

    this.setLayoutX(1200);
    this.setLayoutY(700);
  }

  public void setCallback(Runnable runnable) {
    setOnMouseClicked(event -> {
      runnable.run();
    });
  }

  protected Circle createDot(int size) {
    Circle dot = new Circle(size / 2);
    dot.setFill(Color.BLACK);
    return dot;
  }

  public void rollDice(int value) throws IllegalArgumentException {
    if (value < 1 || value > 6) {
      throw new IllegalArgumentException("Invalid entry. Value must be between 1 and 6");
    }
    setDieFace(value);
  }


  protected void setDieFace(int value) {
    removeAllDots();
    double dotSize = dieFace.getWidth() / 8;
    double xCenter = dieFace.getWidth() / 2;
    double yCenter = dieFace.getHeight() / 2;
    double yOffset = -25;
    double xOffset = -25;
    for (int i = 0; i < Math.min(value, 6); i++) {
      Circle dot = dotArray[i];
      double x = xCenter + ((i % 2 == 0 ? -1 : 1) * dotSize * 2) + xOffset;
      double y = yCenter + ((i < 2 ? -1 : (i < 4 ? 0 : 1)) * dotSize * 2) + yOffset;
      dot.setTranslateX(x);
      dot.setTranslateY(y);
      addDot(dot);
    }
  }

  private void addDot(Circle dot) {
    getChildren().add(dot);
    StackPane.setAlignment(dot, javafx.geometry.Pos.CENTER);
  }

  private void removeAllDots() {
    for (Circle dot : dotArray) {
      getChildren().remove(dot);
    }
  }

  protected Circle[] getDotArray() {
    return dotArray;
  }

  @Override
  public void render(BorderPane pane) {
    setId("Die");
    pane.getChildren().add(this);
  }
}
