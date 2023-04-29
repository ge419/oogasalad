package oogasalad.view;

public interface IDable {
  String getCompId();

  boolean equals(Object o);

  @Override
  String toString();
}
