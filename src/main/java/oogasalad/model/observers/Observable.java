package oogasalad.model.observers;

public interface Observable<T> {

  void register(T observer);

  void remove(T observer);

  void notifyList();
}
