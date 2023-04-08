//package oogasalad.model;
//
//import javafx.beans.property.ObjectProperty;
//import javafx.beans.property.ReadOnlyObjectProperty;
//import javafx.beans.property.SimpleObjectProperty;
//
//public class BValue {
//  private BType type;
//  private Object value;
//
//  public BValue(BMetaData data, Object val) {
//    this.type = data.getType();
//    this.value = val;
//    System.out.println(val.getClass());
//  }
//
//  public <T> T getValue(Class<? extends T> clazz) {
//    return valueProperty(clazz).getValue();
//  }
//
//  public <T> ReadOnlyObjectProperty<T> valueProperty(Class<? extends T> clazz) {
//    if (!clazz.isAssignableFrom(type.getTypeClass())) {
//      throw new IllegalArgumentException("invalid value cast");
//    }
//    return (ReadOnlyObjectProperty<T>) value;
//  }
//
//
//
//}
