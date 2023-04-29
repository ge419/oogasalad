package oogasalad.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

public class GameInfo {

  private List<Object> gameInfo;

  private String title;
  private String description;
  private String genre;
  private double width;
  private double height;
  // TODO: change to image path
//  private Image thumbnail;

  public GameInfo() {
    this.gameInfo = new ArrayList<>();
  }
  @JsonCreator
  public GameInfo(@JsonProperty("gameInfo") List<Object> g) {
    this.gameInfo = new ArrayList<>(g);
  }

  //TODO: refactor this method
  public void setGameInfo(String title, String desc, String genre, double width, double height, Image thumbnail) {
    setTitle(title);
    setDescription(desc);
    setGenre(genre);
    setWidth(width);
    setHeight(height);
//    setThumbnail(thumbnail);
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

//  public Image getThumbnail() {
//    return thumbnail;
//  }
//
//  public void setThumbnail(Image thumbnail) {
//    this.thumbnail = thumbnail;
//  }
}
