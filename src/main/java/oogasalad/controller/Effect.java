package oogasalad.controller;

/**
 * Present the UI effect, then call the callback once done
 * <p>
 * Used in {@link GameController} to present changes in the frontend
 */
@FunctionalInterface
public interface Effect {
  void present(Runnable callback);
}
