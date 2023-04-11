package oogasalad.view.builder.panefeatures;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import oogasalad.view.Coordinate;

// another very helpful stack overflow post: https://stackoverflow.com/questions/57751706/why-does-some-nodes-have-a-x-and-y-position-and-others-not

/**
 * <p>Implementation of the DraggerAPI, which commits to creating an object that will allow any
 * node to be draggable based on a simple boolean statement.</p>
 *
 * <p>Again, this was heavily inspired by <a href="https://edencoding.com/drag-shapes-javafx/">this
 * tutorial</a> by Ed Eden-Rump.
 * Thank you!</p>
 *
 * @author tmh85
 */
public class Dragger implements DraggerAPI {

  private final Node myNode;
  private BooleanProperty myDraggable;
  private int myCycleStatus;
  private EventHandler<MouseEvent> myAnchoredEvent;
  private double myRelativeToSceneInitialX;
  private double myRelativeToSceneInitialY;
  private double myOriginalMouseOffsetX;
  private double myOriginalMouseOffsetY;
  private EventHandler<MouseEvent> myPositionUpdater;
  private EventHandler<MouseEvent> myFinalPositionSetter;
  private double mySceneOffsetX = 0;
  private double mySceneOffsetY = 0;

  private static final int ACTIVE = 1;
  private static final int INACTIVE = 0;


  public Dragger(Node ourObject) {
    this(ourObject, false, new Coordinate(0, 0));
  }

  public Dragger(Node ourObject, boolean canWeDrag, Coordinate offset) {
    myNode = (Node) ourObject;
    mySceneOffsetX = offset.getXCoor();
    mySceneOffsetY = offset.getYCoor();
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
   * Creates the event handlers needed for drag operations, specifically event handlers for first
   * clicking the node, dragging it, then releasing it.
   */
  private void createEventHandlers() {
    myAnchoredEvent = e1 -> {
      if (checkIfDragButtonIsPressed(e1)) {
        myCycleStatus = ACTIVE;
        myRelativeToSceneInitialX = e1.getSceneX();
        myRelativeToSceneInitialY = e1.getSceneY();
        myOriginalMouseOffsetX = e1.getX();
        myOriginalMouseOffsetY = e1.getY();
        System.out.println(e1.getX() + " " + e1.getY());
        System.out.println(e1.getSceneX() + " " + e1.getSceneY());
      }
    };
    myPositionUpdater = e2 -> {
      if (myCycleStatus != INACTIVE) {
        myNode.setTranslateX(e2.getSceneX() - myRelativeToSceneInitialX);
        myNode.setTranslateY(e2.getSceneY() - myRelativeToSceneInitialY);
      }
    };
    myFinalPositionSetter = e3 -> {
      if (myCycleStatus != INACTIVE) {
        myNode.setLayoutX(
            calculateNodeLocation(e3.getSceneX(), myOriginalMouseOffsetX, mySceneOffsetX));
        myNode.setLayoutY(
            calculateNodeLocation(e3.getSceneY(), myOriginalMouseOffsetY, mySceneOffsetY));
        myNode.setTranslateX(0);
        myNode.setTranslateY(0);
      }
    };
  }

  /**
   * Initialize our draggable property by connecting it to our created event handlers.
   */
  private void initializeDraggableProperty() {
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

  ////////////////////////////////////////////////////////////////////////////////////////////////////

  private double calculateNodeLocation(double sceneCoord, double mouseOffset,
      double parentNodeStartingLocation) {
    return getLocationRelativeToParent(sceneCoord, parentNodeStartingLocation) - convertInt(
        mouseOffset);
  }

  private int convertInt(double value) {
    return (int) (value + 0.5);
  }

  private int getLocationRelativeToParent(double sceneValue, double offset) {
    return convertInt(sceneValue) - convertInt(offset);
  }
}
