package oogasalad;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BRule {

  @JsonProperty("key")
  private String key;
  @JsonProperty("fields")
  private List<String> fields;

  public BRule(){
    this.key = key;
    this.fields = fields;
  }

}
