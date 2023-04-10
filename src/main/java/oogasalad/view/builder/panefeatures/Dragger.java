package oogasalad.view.builder.panefeatures;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * Implementation of the DraggerAPI, which commits to creating an object that will allow any
 * node to be draggable based on a simple boolean statement.
 *
 * Again, this was heavily inspired by https://edencoding.com/drag-shapes-javafx/ by Ed Eden-Rump.
 * Thank you!
 *
 * @author tmh85
 */
public class Dragger implements DraggerAPI {
  private final Node myNode;
  private BooleanProperty myDraggable;
  private int myCycleStatus;
  private EventHandler<MouseEvent> myAnchoredEvent;
  private double myRelativeSceneInitialX;
  private double myRelativeSceneInitialY;
  private EventHandler<MouseEvent> myPositionUpdater;
  private EventHandler<MouseEvent> myFinalPositionSetter;
  private static final int ACTIVE = 1;
  private static final int INACTIVE = 0;


  public Dragger(Node ourObject){
    this(ourObject, false);
  }

  public Dragger(Node ourObject, boolean canWeDrag){
    myNode = ourObject;
    createEventHandlers();
    initializeDraggableProperty();
    setDraggable(canWeDrag);
  }

  @Override
  public void setDraggable(boolean dragStatus) {
    this.myDraggable.set(dragStatus);
  }

  @Override
  public BooleanProperty getDragProperty() {
    return myDraggable;
  }

  @Override
  public boolean getDragStatus() {
    return myDraggable.get();
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Creates the event handlers needed for drag operations, specifically event handlers
   * for first clicking the node, dragging it, then releasing it.
   */
  private void createEventHandlers(){
    myAnchoredEvent = e1 -> {
      if (checkIfDragButtonIsPressed(e1)){
        myCycleStatus = ACTIVE;
        myRelativeSceneInitialX = e1.getSceneX();
        myRelativeSceneInitialY = e1.getSceneY();
      }
    };
    myPositionUpdater = e2 ->{
      if (myCycleStatus != INACTIVE){
        myNode.setTranslateX(e2.getSceneX() - myRelativeSceneInitialX);
        myNode.setTranslateY(e2.getSceneY() - myRelativeSceneInitialY);
      }
    };
    myFinalPositionSetter = e3 -> {
      if (myCycleStatus != INACTIVE){
        myNode.relocate(myNode.getLayoutX() + myNode.getTranslateX(),
            myNode.getLayoutY() + myNode.getTranslateY());
        myNode.setTranslateX(0);
        myNode.setTranslateY(0);
//        System.out.println(String.format("Relative x and y: (%f,%f)\tScene x and y: (%f,%f)\tWindow x and y: (%f,%f)",
//            e3.getX(), e3.getY(), e3.getSceneX(), e3.getSceneY(), e3.getScreenX(), e3.getScreenY()));
      }
    };
  }

  /**
   * Initialize our draggable property by connecting it to our created event handlers.
   */
  private void initializeDraggableProperty(){
    myDraggable = new SimpleBooleanProperty();
    myDraggable.addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        myNode.addEventFilter(MouseEvent.MOUSE_PRESSED, myAnchoredEvent);
        myNode.addEventFilter(MouseEvent.MOUSE_DRAGGED, myPositionUpdater);
        myNode.addEventFilter(MouseEvent.MOUSE_RELEASED, myFinalPositionSetter);
      } else {
        myNode.removeEventFilter(MouseEvent.MOUSE_PRESSED, myAnchoredEvent);
        myNode.removeEventFilter(MouseEvent.MOUSE_DRAGGED, myPositionUpdater);
        myNode.removeEventFilter(MouseEvent.MOUSE_RELEASED, myFinalPositionSetter);
      }
    });
  }
}
