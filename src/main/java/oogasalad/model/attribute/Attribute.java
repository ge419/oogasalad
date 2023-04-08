package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Attribute {
  private static final Logger log = LogManager.getLogger(Attribute.class);

  @JsonProperty("key")
  private final String key;

  protected Attribute(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

  protected static <T extends Attribute> T getAttributeAs(Attribute attr, Class<T> clazz) {
    try {
      return clazz.cast(attr);
    } catch (ClassCastException e) {
      log.fatal("failed to perform attribute cast", e);
      throw e;
    }
  }
}
