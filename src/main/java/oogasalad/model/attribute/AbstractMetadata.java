package oogasalad.model.attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import java.util.Objects;
import java.util.function.Predicate;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Abstraction that represents information about the attribute field includes key, name,
 * description, editable and viewable status
 * <p>
 * default values of various data types are implemented by extending AbstractMetadata
 * </p>
 *
 * @author Jay Yoon
 */
@JsonTypeInfo(use = Id.CLASS)
public abstract class AbstractMetadata implements Metadata {

  private static final Logger LOGGER = LogManager.getLogger(AbstractMetadata.class);
  @JsonProperty("key")
  private final String key;
  private final StringProperty name;
  private final StringProperty description;
  private final BooleanProperty editable;
  private final BooleanProperty viewable;

  @JsonCreator
  protected AbstractMetadata(@JsonProperty("key") String key) {
    this.key = key;
    name = new SimpleStringProperty("");
    description = new SimpleStringProperty("");
    editable = new SimpleBooleanProperty(true);
    viewable = new SimpleBooleanProperty(true);
  }

  public static <T extends Metadata> T getAs(Metadata attr, Class<T> clazz) {
    try {
      return clazz.cast(attr);
    } catch (ClassCastException e) {
      LOGGER.fatal("failed to perform metadata cast", e);
      throw e;
    }
  }

  public static <T> boolean checkObject(Object val, Class<T> expected, Predicate<T> validator) {
    try {
      return validator.test(expected.cast(val));
    } catch (ClassCastException e) {
      return false;
    }
  }

  @Override
  public boolean isValid(Attribute attribute) {
    if (!getAttributeClass().isAssignableFrom(attribute.getClass())) {
      return false;
    }
    return checkPreconditions(attribute);
  }

  protected abstract boolean checkPreconditions(Attribute attribute);

  @Override
  public boolean isCorrectType(Attribute attribute) {
    return getAttributeClass().isAssignableFrom(attribute.getClass());
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public String getName() {
    return name.get();
  }

  public void setName(String name) {
    this.name.set(name);
  }

  @Override
  public StringProperty nameProperty() {
    return name;
  }

  @Override
  public String getDescription() {
    return description.get();
  }

  public void setDescription(String description) {
    this.description.set(description);
  }

  @Override
  public StringProperty descriptionProperty() {
    return description;
  }

  @Override
  @JsonGetter("editable")
  public boolean isEditable() {
    return editable.get();
  }

  public void setEditable(boolean editable) {
    this.editable.set(editable);
  }

  @Override
  public BooleanProperty editableProperty() {
    return editable;
  }

  @Override
  @JsonGetter("viewable")
  public boolean isViewable() {
    return viewable.get();
  }

  public void setViewable(boolean viewable) {
    this.viewable.set(viewable);
  }

  @Override
  public BooleanProperty viewableProperty() {
    return viewable;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AbstractMetadata metadata = (AbstractMetadata) o;
    return Objects.equals(key, metadata.key) && Objects.equals(getName(),
        metadata.getName()) && Objects.equals(getDescription(), metadata.getDescription())
        && Objects.equals(isEditable(), metadata.isEditable()) && Objects.equals(isViewable(),
        metadata.isViewable());
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, name, description, editable, viewable);
  }
}
