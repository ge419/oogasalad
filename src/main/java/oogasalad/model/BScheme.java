package oogasalad.model;

import java.util.Collection;
import java.util.List;

public class BScheme {
  //TODO: replace Strings with predefined records/ enums
  private String name;
  private List<BAttribute> attributes;

  public BScheme(String name, Collection<BAttribute> attr) {
    this.name = name;
    this.attributes = (List<BAttribute>) attr;
  }
}
