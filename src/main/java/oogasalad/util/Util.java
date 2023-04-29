package oogasalad.util;

import java.util.function.Consumer;
import javafx.beans.property.ReadOnlyProperty;

/**
 * Miscellaneous application utilities.
 *
 * @author Dominic Martinez
 */
public class Util {

  private Util() {
    throw new IllegalStateException("Utility class");
  }

  public static <T> void initializeAndListen(ReadOnlyProperty<T> property, Consumer<T> fn) {
    property.addListener(((observable, oldValue, newValue) -> fn.accept(newValue)));
    fn.accept(property.getValue());
  }
}
