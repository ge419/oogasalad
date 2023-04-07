package oogasalad.model;

import java.util.HashMap;
import java.util.Map;

public class BMetaData {
  private String name;
  private HashMap<BAttribute, String> type;

  public BMetaData(String name, Map<BAttribute, String> type) {
    this.name = name;
    this.type = (HashMap<BAttribute, String>) type;
  }
}
