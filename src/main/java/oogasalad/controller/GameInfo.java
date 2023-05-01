package oogasalad.controller;

public class GameInfo {

  private String imgSrc;
  private double width;
  private double height;

  public double getWidth() {
    return width;
  }

  public GameInfo setWidth(double width) {
    this.width = width;
    return this;
  }

  public double getHeight() {
    return height;
  }

  public GameInfo setHeight(double height) {
    this.height = height;
    return this;
  }

}
