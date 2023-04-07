package oogasalad.model;

public class BValue {
  private String value = "VALUE";

  public BValue(String value) {
    this.value = value;
  }

  public BValue(int value) {
    this.value = String.valueOf(value);
  }

  public String toString() {
    return this.value;
  }

}
