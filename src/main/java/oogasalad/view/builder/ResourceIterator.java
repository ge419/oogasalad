package oogasalad.view.builder;

import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public interface ResourceIterator {
  default void forEachResourceKey(ResourceBundle resource, Consumer<String> operation){
    Enumeration<String> enumeration = resource.getKeys();
    while (enumeration.hasMoreElements()){
      String key = enumeration.nextElement();
      operation.accept(key);
    }
  }

}
