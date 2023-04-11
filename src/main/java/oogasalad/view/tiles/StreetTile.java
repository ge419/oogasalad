package oogasalad.view.tiles;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import oogasalad.view.Coordinate;

public class StreetTile extends StackPane implements Tile {
  private int id;
  private Coordinate position;
  private Color color;
  private String name;
  private String price;
  private double tileWidth;
  private double tileHeight;

  public StreetTile(int id, Coordinate position, Color color, String name, String price,
      double width, double height) {
    this.color = color;
    this.name = name;
    this.price = price;
    this.tileWidth = width;
    this.tileHeight = height;
    createTile();
    setPosition(position);
  }

  private void createTile() {
    Rectangle rectangle = new Rectangle(tileWidth, tileHeight);
    rectangle.setFill(color);
    rectangle.setStroke(Color.BLACK);
    rectangle.setStrokeWidth(2);

    Label label = new Label();
    label.setPrefSize(50, 50); // Set the preferred width and height of the label
    label.setWrapText(true); // Enable text wrapping to multiple lines if necessary
    label.setText("This is some long text that may not fit within the label."); // Set the text of the label

    Text text = new Text(label.getText());
    text.setFont(label.getFont());

    double fontSize = 12; // Start with a small font size

    while (text.getBoundsInLocal().getWidth() < label.getPrefWidth() && fontSize < 100) {
      // Increase the font size until the text width exceeds the label width or a maximum font size is reached
      fontSize++;
      text.setFont(Font.font(label.getFont().getFamily(), fontSize));
    }
    label.setFont(Font.font(label.getFont().getFamily(), fontSize));

    getChildren().addAll(rectangle, label);
  }

  @Override
  public int getTileId() {
    return 0;
  }

  @Override
  public double[] getPosition() {
    return new double[0];
  }

  @Override
  public int[] getNext() {
    return new int[0];
  }

  @Override
  public int[] getOnLand() {
    return new int[0];
  }

  @Override
  public int[] getAfterTurn() {
    return new int[0];
  }

  @Override
  public void setColor(Color color) {

  }

  @Override
  public void setPosition(Coordinate coord) {
    this.setLayoutX(coord.getXCoor());
    this.setLayoutY(coord.getYCoor());
  }
}
