package oogasalad.controller;

import oogasalad.model.builder.BBuilder;
import oogasalad.view.builder.BuilderView;
import oogasalad.view.builder.gameholder.ImmutableGameHolder;

/**
 * Temporary controller for front and backend builder
 */
public class BuilderController implements BuilderControllerInterface{

  private BuilderView front;
  private BBuilder back;

  public BuilderController() {
    //initialize frontend builder
    front = new BuilderView();
    //initialize backend builder
    back = new BBuilder(this);
  }

  /**
   * Take the ImmutableGameHolder from frontend and call on backend to save
   * @param holder
   */
  @Override
  public void save(ImmutableGameHolder holder) {
    //take holder as parameter
    back.save(holder);
  }

  /**
   * Take the ImmutableGameHolder from backend and call on frontend to load
   * @param holder
   */
  @Override
  public void load(ImmutableGameHolder holder) {
    //take holder as parameter?
    front.loadFile();
  }
}
