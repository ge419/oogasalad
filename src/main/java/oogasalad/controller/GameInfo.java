package oogasalad.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import javafx.scene.image.Image;

public class GameInfo {

  private String title;
  private String description;
  private String genre;
  private double width;
  private double height;
  private Image thumbnail;

  @JsonCreator
  public GameInfo(String title, String description, String genre, double width, double height,
      Image thumbnail) {
    this.title = title;
    this.description = description;
    this.genre = genre;
    this.width = width;
    this.height = height;
    this.thumbnail = thumbnail;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public double getWidth() {
    return width;
  }

  public void setWidth(double width) {
    this.width = width;
  }

  public double getHeight() {
    return height;
  }

  public void setHeight(double height) {
    this.height = height;
  }

  public Image getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(Image thumbnail) {
    this.thumbnail = thumbnail;
  }
}
