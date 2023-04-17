package oogasalad.model.attribute;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents the information about a particular {@link ObjectSchema} field. Metadata specifies the
 * field type, and editing information for the field, but not the value.
 *
 * @author Dominic Martinez
 * @see AbstractAttribute
 * @see ObjectSchema
 */
public interface Metadata {

  /**
   * @return an attribute matching this metadata type, with the appropriate default value.
   */
  Attribute makeAttribute();

  /**
   * @return the {@link Class} that would be returned by {@link Metadata#makeAttribute()}
   */
  Class<? extends Attribute> getAttributeClass();

  /**
   * Returns whether the provided attribute is a valid assignment for this metadata.
   *
   * @return true if attribute is valid, false otherwise.
   */
  boolean isValid(Attribute attribute);

  /**
   * @param attribute concrete attribute to check against metadata
   * @return true if attribute type matches metadata, false otherwise
   */
  boolean isCorrectType(Attribute attribute);

  /**
   * Returns the key associated with this field. The key is constant.
   */
  String getKey();

  /**
   * @return UI name for field
   */
  String getName();

  /**
   * @return UI name property for field
   */
  StringProperty nameProperty();

  /**
   * @return UI description for field
   */
  String getDescription();

  /**
   * @return UI description property for field
   */
  StringProperty descriptionProperty();

  /**
   * @return true if user should be able to edit field, false otherwise
   */
  boolean isEditable();

  /**
   * @return property for editable value
   * @see Metadata#isEditable()
   */
  BooleanProperty editableProperty();

  /**
   * @return true if field should be shown on the editing form, false otherwise
   */
  boolean isViewable();

  /**
   * @return property for viewable value
   * @see Metadata#isViewable()
   */
  BooleanProperty viewableProperty();
}
