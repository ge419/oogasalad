package oogasalad.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class GameInfo {

  private final List<Object> components;
  private String title;
  private String description;
  private String genre;
  private double width;
  private double height;
  private String imgSrc;

  public GameInfo() {
    this.components = new ArrayList<>();
  }
  @JsonCreator
  public GameInfo(@JsonProperty("gameInfo") List<Object> g) {
    this.components = new ArrayList<>(g);
  }

  //TODO: refactor this method
  public void buildGameInfo(String title, String desc, String genre, double width, double height, String path) {
    setTitle(title);
    setDescription(desc);
    setGenre(genre);
    setWidth(width);
    setHeight(height);
    setImgSrc(path);
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

  public String getImgSrc() {
    return imgSrc;
  }

  public void setImgSrc(String path) {
    this.imgSrc = path;
  }
}
