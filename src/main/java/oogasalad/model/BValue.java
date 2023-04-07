package oogasalad.model;

public class BValue {
  //TODO: Replace Object
  private Object value;

  public BValue(String value) {
    this.value = value;
  }

  public BValue(Integer value) {
    this.value = value;
  }

  public String toString() {
    return this.value.toString();
  }

}
