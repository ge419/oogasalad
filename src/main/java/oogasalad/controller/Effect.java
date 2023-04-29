package oogasalad.controller;

@FunctionalInterface
public interface Effect {
  //Present the UI effect, then call the callback once done
  void present(Runnable callback);
}
