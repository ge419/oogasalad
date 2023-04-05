package oogasalad.model;

import java.util.HashMap;
import java.util.Map;

@Deprecated
public class BScheme {
  //TODO: replace Strings with predefined records/ enums
  private String name;
  private HashMap<BAttribute, String> type;

  public BScheme(String name, Map<BAttribute, String> type) {
    this.name = name;
    this.type = (HashMap<BAttribute, String>) type;
  }
}
