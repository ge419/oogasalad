package oogasalad.model.observers;

/**
 * Interface for an Observable
 * <p>
 * Implemented by {@link oogasalad.model.constructable.GameHolder} to allow
 * {@link oogasalad.controller.GameController} and {@link oogasalad.view.gameplay.Gameview} to keep
 * track of game
 *
 * @author Jay Yoon
 */
public interface Observable<T> {

  void register(T observer);

  void remove(T observer);

  void notifyList();
}
