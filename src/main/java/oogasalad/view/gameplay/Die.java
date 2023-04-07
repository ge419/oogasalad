package oogasalad.view.gameplay;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import oogasalad.view.Renderable;
//import javafx.scene.text.Font;
//import javafx.scene.text.Text;

public class Die extends StackPane implements Renderable {

  private final Circle[] dotArray = new Circle[6];

  public Die() {
    final int DICE_SIZE = 50;
    Rectangle dieFace = new Rectangle(DICE_SIZE, DICE_SIZE);
    dieFace.setFill(Color.WHITE);
    dieFace.setStroke(Color.BLACK);
    getChildren().add(dieFace);

    int dotSize = DICE_SIZE / 8;
    for (int i=0; i<6; i++) {
      dotArray[i] = createDot(dotSize);
      addDot(dotArray[i]);
    }
    this.setLayoutX(700);
    this.setLayoutY(700);
  }

  private Circle createDot(int size) {
    Circle dot = new Circle(size / 2);
    dot.setFill(Color.BLACK);
    return dot;
  }

  public void rollDice (int value) throws IllegalArgumentException {
    if (value < 1 || value > 6) {
      throw new IllegalArgumentException("Invalid entry. Value must be between 1 and 6");
    }
    setDieFace(value);
  }

  private void setDieFace(int value) {
    removeAllDots();
    for (int i=0; i<value; i++) {
      addDot(dotArray[i]);
    }
  }

  private void addDot(Circle dot) {
    getChildren().add(dot);
    StackPane.setAlignment(dot, javafx.geometry.Pos.CENTER);
  }

  private void removeAllDots() {
    for (Circle dot: dotArray) {
      getChildren().remove(dot);
    }
  }

  @Override
  public void render(BorderPane pane) {
    Die die = new Die();
    pane.getChildren().add(die);
  }
}
