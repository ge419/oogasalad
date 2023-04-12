package oogasalad.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.util.List;
import java.util.Optional;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.AttributeList;

@JsonTypeInfo(use= Id.CLASS)
public abstract class Constructable {
  @JsonProperty("type")
  private String schemaName;
  @JsonIgnore
  private final SchemaDatabase database;
  @JsonUnwrapped
  private final AttributeList attributeList;

  protected Constructable(String schemaName, SchemaDatabase database) {
    this.database = database;
    this.attributeList = new AttributeList();
    setSchemaName(schemaName);
  }

  protected Constructable(String schemaName, SchemaDatabase database, AttributeList attributeList) {
    this.database = database;
    this.attributeList = attributeList;
    setSchemaName(schemaName);
  }

  @JsonIgnore
  public List<Attribute> getAllAttributes() {
    return attributeList.getAllAttributes();
  }

  @JsonIgnore
  public Optional<Attribute> getAttribute(String key) {
    return attributeList.getAttribute(key);
  }

  @JsonGetter("type")
  public String getSchemaName() {
    return schemaName;
  }

  @JsonSetter("type")
  public void setSchemaName(String schema) {
    this.schemaName = schema;
    // TODO: Reset attributes
  }
}