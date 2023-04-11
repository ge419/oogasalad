package oogasalad.view.builder.panefeatures;

import java.awt.Dimension;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import oogasalad.view.Coordinate;

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
  private double myRelativeToSceneInitialX;
  private double myRelativeToSceneInitialY;
  private double myMouseOffsetX;
  private double myMouseOffsetY;
  private EventHandler<MouseEvent> myPositionUpdater;
  private EventHandler<MouseEvent> myFinalPositionSetter;
  private double myInitialLayoutX = 0;
  private double myInitialLayoutY = 0;
  private double mySceneOffsetX = 0;
  private double mySceneOffsetY = 0;

  private Dimension myParentSize;
  private static final int ACTIVE = 1;
  private static final int INACTIVE = 0;


  public Dragger(Node ourObject){
    this(ourObject, false, new Coordinate(0,0));
  }

  public Dragger(Node ourObject, boolean canWeDrag, Coordinate offset){
    myNode = ourObject;
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
   * Creates the event handlers needed for drag operations, specifically event handlers
   * for first clicking the node, dragging it, then releasing it.
   */
  private void createEventHandlers(){
    myAnchoredEvent = e1 -> {
      if (checkIfDragButtonIsPressed(e1)){
        myCycleStatus = ACTIVE;
        myInitialLayoutX = myNode.getLayoutX();
        myInitialLayoutY = myNode.getLayoutY();
        myRelativeToSceneInitialX = e1.getSceneX();
        myRelativeToSceneInitialY = e1.getSceneY();
        myMouseOffsetX = e1.getX();
        myMouseOffsetY = e1.getY();
        System.out.println(e1.getX() + " " + e1.getY());
        System.out.println(e1.getSceneX() + " " + e1.getSceneY());
      }
    };
    myPositionUpdater = e2 ->{
      if (myCycleStatus != INACTIVE){
        myNode.setTranslateX(e2.getSceneX() - myRelativeToSceneInitialX);
        myNode.setTranslateY(e2.getSceneY() - myRelativeToSceneInitialY);
      }
    };
    myFinalPositionSetter = e3 -> {
      if (myCycleStatus != INACTIVE){
        System.out.println(String.format("Relative x and y: (%f,%f)\tScene x and y: (%f,%f)\tWindow x and y: (%f,%f)",
            e3.getX(), e3.getY(), e3.getSceneX(), e3.getSceneY(), e3.getScreenX(), e3.getScreenY()));
        System.out.println(String.format("Current translations: (%f,%f)", myNode.getTranslateX(), myNode.getTranslateY()));
        System.out.println(String.format("Mouse offset: (%f, %f)", myMouseOffsetX, myMouseOffsetY));
        System.out.println(String.format("Scene offset: (%f,%f)", mySceneOffsetX, mySceneOffsetY));

//        myNode.relocate(myInitialLayoutX + myNode.getTranslateX(),
//            myInitialLayoutY + myNode.getTranslateY());
        myNode.setTranslateX(0);
        myNode.setTranslateY(0);
//        myNode.setLayoutX(0);
//        myNode.setLayoutY(0);
        myNode.relocate(calculateNodeLocation(e3.getSceneX(), myMouseOffsetX, mySceneOffsetX),
            calculateNodeLocation(e3.getSceneY(), myMouseOffsetY, mySceneOffsetY));
//        myNode.relocate(0,
//            0);
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

  private double calculateNodeLocation(double sceneCoord, double mouseOffset, double parentNodeStartingLocation){
    System.out.println(String.format("%f -%f - %f = %f", sceneCoord, mouseOffset,
        parentNodeStartingLocation, sceneCoord-mouseOffset-parentNodeStartingLocation));
    return sceneCoord - mouseOffset - parentNodeStartingLocation;
  }
}
