package oogasalad.model.engine.rules;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import java.util.List;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import oogasalad.model.attribute.SchemaBinding;
import oogasalad.model.engine.ActionQueue;
import oogasalad.model.engine.EventRegistrar;

/**
 * The primary abstraction for game behavior. Rules are triggered by events, and can add actions to
 * an {@link ActionQueue} to affect the game state.
 *
 * @author Dominic Martinez
 */
@JsonTypeInfo(use = Id.CLASS)
public interface Rule {

  /**
   * Registers all the event listeners for this rule.
   *
   * @param registrar provides event registration methods
   */
  void registerEventHandlers(EventRegistrar registrar);

  /**
   * Returns a list of schema bindings representing the attributes this rule adds onto other
   * schemas.
   */
  @JsonIgnore
  default List<SchemaBinding> getAppliedSchemas() {
    return appliedSchemasProperty().get();
  }

  /**
   * Returns a property corresponding to {@link Rule#getAppliedSchemas()}.
   */
  default ReadOnlyListProperty<SchemaBinding> appliedSchemasProperty() {
    return new SimpleListProperty<>(FXCollections.observableArrayList());
  }
}