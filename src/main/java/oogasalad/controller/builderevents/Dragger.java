package oogasalad.controller.builderevents;

import java.awt.Dimension;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import oogasalad.view.Coordinate;

// another very helpful stack overflow post: https://stackoverflow.com/questions/57751706/why-does-some-nodes-have-a-x-and-y-position-and-others-not

/**
 * <p>Implementation of the DraggerAPI, which commits to creating an object that will allow any
 * node to be draggable based on a simple boolean statement.</p>
 *
 * <p>Again, this was heavily inspired by <a href="https://edencoding.com/drag-shapes-javafx/">this
 * tutorial</a> by Ed Eden-Rump. Thank you!</p>
 *
 * @author tmh85
 */
public class Dragger implements DraggerAPI {

  private static final int ACTIVE = 1;
  private static final int INACTIVE = 0;
  private static final int MAX_DISPLACEMENT = 2147483647;
  private final Node myNode;
  private final MouseButton myDragButton;
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
  private double myNodeWidth;
  private double myNodeHeight;
  private double myMaxHeight;
  private double myMaxWidth;
  private double myOriginalMaxWidth;
  private double myOriginalMaxHeight;
  private Dimension myMaxTranslate;
  private Dimension myMinTranslate;



  public Dragger(Node ourObject, ReadOnlyObjectProperty<Bounds> parentBounds) {
    this(ourObject, false, new Coordinate(0, 0, 0), MouseButton.PRIMARY, parentBounds);
  }

  public Dragger(Node ourObject, boolean canWeDrag, Coordinate offset, MouseButton dragButton, ReadOnlyObjectProperty<Bounds> parentBounds) {
    myNode = ourObject;
    mySceneOffsetX = offset.getXCoor();
    mySceneOffsetY = offset.getYCoor();
    createEventHandlers();
    initializeDraggableProperty();
    setDraggable(canWeDrag);
    myDragButton = dragButton;
    myNodeWidth = myNode.getBoundsInParent().getMaxX() - myNode.getBoundsInParent().getMinX();
    myNodeHeight = myNode.getBoundsInParent().getMaxY() - myNode.getBoundsInParent().getMinY();
    initializeParentSizeListener(parentBounds);
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
      if (e1.getButton() == myDragButton) {
        myCycleStatus = ACTIVE;
        myRelativeToSceneInitialX = e1.getSceneX();
        myRelativeToSceneInitialY = e1.getSceneY();
        myOriginalMouseOffsetX = e1.getX();
        myOriginalMouseOffsetY = e1.getY();
        myOriginalMaxWidth = myMaxWidth;
        myOriginalMaxHeight = myMaxHeight;
        myMaxTranslate = new Dimension();
        myMinTranslate = new Dimension();

        myMaxTranslate.setSize(
            myMaxWidth - myNode.getBoundsInParent().getMinX(),
            myMaxHeight - myNode.getBoundsInParent().getMinY()
        );
        myMinTranslate.setSize(
            0 - myNode.getBoundsInParent().getMinX(),
            0 - myNode.getBoundsInParent().getMinY()
        );

        System.out.println(myMaxTranslate + " | " + myMinTranslate);
//        System.out.println(String.format("Mouse click relative to node: {%f,%f}", e1.getX(), e1.getY()));
//        System.out.println(String.format("Mouse click relative to scene: {%f,%f}", e1.getSceneX(), e1.getSceneY()));
      }
    };
    myPositionUpdater = e2 -> {
      if (myCycleStatus != INACTIVE) {
        double dragLocationX = e2.getSceneX() - myRelativeToSceneInitialX;
        double dragLocationY = e2.getSceneY() - myRelativeToSceneInitialY;
        Dimension location = checkForBounds(myNode, dragLocationX, dragLocationY);
        myNode.setTranslateX(location.getWidth());
        myNode.setTranslateY(location.getHeight());
      }
    };
    myFinalPositionSetter = e3 -> {
      if (myCycleStatus != INACTIVE) {
        myNode.relocate(myNode.getBoundsInParent().getMinX(), myNode.getBoundsInParent().getMinY());
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

  private void initializeParentSizeListener(ReadOnlyObjectProperty<Bounds> parentBounds){
    myMaxWidth = parentBounds.get().getMaxX();
    myMaxHeight = parentBounds.get().getMaxY();
    System.out.println("Max X: " + myMaxWidth + "Max Y: " + myMaxHeight);

    parentBounds.addListener(((observable, oldValue, newValue) -> {
      if (newValue.getMaxX() != oldValue.getMaxX()){
        myMaxWidth = newValue.getMaxX();
      }
      if (newValue.getMaxY() != oldValue.getMaxY()){
        myMaxHeight = newValue.getMaxY();
      }
    }));
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////

  private double calculateNodeLocation(double sceneCoord, double mouseOffset,
      double parentNodeStartingLocation) {
    double ans = getLocationRelativeToParent(sceneCoord, parentNodeStartingLocation);
    System.out.println(String.format("Given Scene value: %f | Given mouse offset: %f | Given board pane starting location: %f", sceneCoord, mouseOffset, parentNodeStartingLocation));
    System.out.println("Calculated end point: " + ans);
    return ans;
  }

  private int convertInt(double value) {
    return (int) (value + 0.5);
  }

  private double getLocationRelativeToParent(double sceneValue, double offset) {
    return sceneValue - offset;
  }

  private Dimension checkForBounds(Node node, double xLocation, double yLocation){
    Dimension ans = new Dimension();
    System.out.println(String.format("Inputted: {%f,%f} and max is {%f,%f}", xLocation, yLocation, myOriginalMaxWidth, myOriginalMaxHeight));
    ans.setSize(
        (myOriginalMaxWidth < (myNode.getBoundsInParent().getMinX() + myNodeWidth)) ? myMaxTranslate.getWidth() : xLocation,
        (myOriginalMaxHeight < (myNode.getBoundsInParent().getMinY() + myNodeHeight)) ? myMaxTranslate.getHeight() : yLocation
    );
    return ans;
  }
}
