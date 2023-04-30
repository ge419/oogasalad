package oogasalad.controller;

public class GameInfo {
  private String title;
  private String description;
  private String genre;
  private String imgSrc;
  private double width;
  private double height;


  public String getTitle() {
    return title;
  }

  public GameInfo setTitle(String title) {
    this.title = title;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public GameInfo setDescription(String description) {
    this.description = description;
    return this;
  }

  public String getGenre() {
    return genre;
  }

  public GameInfo setGenre(String genre) {
    this.genre = genre;
    return this;
  }

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
