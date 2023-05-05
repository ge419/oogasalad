package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TileMetadata extends AbstractMetadata {

  public static final Class<TileAttribute> ATTRIBUTE_CLASS = TileAttribute.class;
  private final StringProperty defaultValue;


  @JsonCreator
  public TileMetadata(@JsonProperty("key") String key) {
    super(key);
    defaultValue = new SimpleStringProperty("");
  }

  public static TileMetadata from(Metadata meta) {
    return getAs(meta, TileMetadata.class);
  }

  @Override
  protected boolean checkPreconditions(Attribute attribute) {
    Optional<String> id = TileAttribute.from(attribute).getId();
    return id.isPresent() && isValidTileId(id.get());
  }

  public boolean isValidTileId(String id) {
    // No preconditions
    return true;
  }

  public String getDefaultValue() {
    return defaultValue.get();
  }

  public void setDefaultValue(String defaultValue) {
    this.defaultValue.set(defaultValue);
  }

  @Override
  public Attribute makeAttribute() {
    return makeTileAttribute();
  }

  @Override
  public Class<? extends Attribute> getAttributeClass() {
    return ATTRIBUTE_CLASS;
  }

  public TileAttribute makeTileAttribute() {
    return new TileAttribute(getKey(), "");
  }
}
