package oogasalad.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BAction {

  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @JsonProperty("schema")
  private List<String> schema;

  public BAction(){
    this.name = "name";
    this.description = "description";
    this.schema = List.of();
  }

  public String getName() {
    return name;
  }

  public List<String> getSchema(){
    return schema;
  }
}
