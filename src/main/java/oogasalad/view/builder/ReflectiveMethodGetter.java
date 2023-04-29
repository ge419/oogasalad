package oogasalad.view.builder;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * <p>Interface that holds some common methods for getting keys from resource files and creating
 * interactable items out of them.</p>
 *
 * @author tmh85
 * @author jf295
 */
public interface ReflectiveMethodGetter {

  /**
   * <p>For each resource key present in a provided resource bundle, perform some
   * given lambda operation.</p>
   *
   * @param resource  resource bundle you wish to do operations on
   * @param operation lambda expression of your choosing you want to perform on each new key entry.
   *                  Provided argument to the lambda is the current key for manipulation.
   */
  default void forEachResourceKey(ResourceBundle resource, Consumer<String> operation) {
    Enumeration<String> enumeration = resource.getKeys();
    while (enumeration.hasMoreElements()) {
      String key = enumeration.nextElement();
      operation.accept(key);
    }
  }

  /**
   * <p>Convert the provided string to a method in the current class.</p>
   *
   * @param method method to run; given as a string
   */
  default void runMethodFromString(String method) {
    try {
      Method reflectedMethod = this.getClass().getDeclaredMethod(method);
      reflectedMethod.setAccessible(true);
      reflectedMethod.invoke(this);
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new RuntimeException(ex);
    }
  }

}
