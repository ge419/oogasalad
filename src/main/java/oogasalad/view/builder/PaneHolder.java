package oogasalad.view.builder;

import java.util.LinkedList;
import java.util.Queue;
import javafx.scene.layout.Pane;

public class PaneHolder {

  private final Queue<Pane> myPrevious;
  private Pane myCurrent;

  PaneHolder(Pane current) {
    myPrevious = new LinkedList<>();
    myCurrent = current;
  }

  public void addPane(Pane pane) {
    myPrevious.add(myCurrent);
    myCurrent = pane;
  }

  public Pane restorePreviousPane() {
    myCurrent = myPrevious.poll();
    return myCurrent;
  }

  public Pane getCurrent() {
    return myCurrent;
  }

  public Pane getPrevious() {
    return myPrevious.element();
  }
}
