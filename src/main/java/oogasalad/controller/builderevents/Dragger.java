package oogasalad.controller.builderevents;

import java.awt.Dimension;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Transform;
import javafx.stage.Screen;
import oogasalad.view.Coordinate;

// another very helpful stack overflow post: https://stackoverflow.com/questions/57751706/why-does-some-nodes-have-a-x-and-y-position-and-others-not

/**
 * <p>Implementation of the DraggerAPI, which commits to creating an object that will allow any
 * node to be draggable based on a simple boolean statement.</p>
 *
 * <p>The dragger also needs the parent that holds the node that will be dragged. This is so that
 * the dragger can know the correct bounds of the parent to prevent the node from dragging outside
 * of the parent.</p>
 *
 * <p>Again, this was heavily inspired by <a href="https://edencoding.com/drag-shapes-javafx/">this
 * tutorial</a> by Ed Eden-Rump. Thank you!</p>
 *
 * @author tmh85
 */
public class Dragger implements DraggerAPI {

  private static final int ACTIVE = 1;
  private static final int INACTIVE = 0;
  private static final double ORIGIN = 0.0;
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



  public Dragger(Node ourObject, Node parent) {
    this(ourObject, false, MouseButton.PRIMARY, parent);
  }

  public Dragger(Node ourObject, boolean canWeDrag, MouseButton dragButton, Node parent) {
    myNode = ourObject;
    createEventHandlers();
    initializeDraggableProperty();
    setDraggable(canWeDrag);
    myDragButton = dragButton;
    myNodeWidth = myNode.getBoundsInParent().getWidth();
    myNodeHeight = myNode.getBoundsInParent().getHeight();
    initializeParentSizeListener(parent);
    initializeParentTransformListener(parent);
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
        System.out.println(String.format("Max dimensions: {%f,%f}", myOriginalMaxWidth, myOriginalMaxHeight));
        System.out.println("Minimum translations: " + myMinTranslate);

        myMaxTranslate.setSize(
            myMaxWidth - myNode.getBoundsInParent().getMinX() - myNodeWidth,
            myMaxHeight - myNode.getBoundsInParent().getMinY() - myNodeHeight
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
        Dimension location = checkForBounds(e2, dragLocationX, dragLocationY);
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

  private void initializeParentSizeListener(Node parent){
    updateBounds(parent.getBoundsInParent());
    System.out.println("Max X: " + myMaxWidth + "Max Y: " + myMaxHeight);

    parent.boundsInParentProperty().addListener(((observable, oldValue, newValue) -> {
      updateBounds(newValue);
    }));
  }

  private void initializeParentTransformListener(Node parent){
    transformPoints(parent.getLocalToSceneTransform(), parent.getBoundsInLocal());

    parent.localToSceneTransformProperty().addListener(((observable, oldValue, newValue) -> {
      transformPoints(newValue, parent.getBoundsInLocal());
    }));
  }

  private void updateBounds(Bounds parentBounds){
    myMaxWidth = parentBounds.getWidth();
    myMaxHeight = parentBounds.getHeight();
    System.out.println(String.format("New maxs: {%f,%f}", myMaxWidth, myMaxHeight));
    System.out.println(String.format("Minimums for boardpane: {%f, %f}", parentBounds.getMinX(), parentBounds.getMinY()));
  }

  private void transformPoints(Transform transformMatrix, Bounds desiredBounds){
    Point2D transformed = transformMatrix.transform(desiredBounds.getMinX(), desiredBounds.getMinY());
    mySceneOffsetX = transformed.getX();
    mySceneOffsetY = transformed.getY();
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

  private Dimension checkForBounds(MouseEvent event, double xLocation, double yLocation){
    Dimension ans = new Dimension();
    double currX = getLocationRelativeToParent(event.getSceneX(), mySceneOffsetX);
    double currY = getLocationRelativeToParent(event.getSceneY(), mySceneOffsetY);
//    System.out.println(String.format("Inputted: {%f,%f} and max is {%f,%f}", xLocation, yLocation, myOriginalMaxWidth, myOriginalMaxHeight));
    System.out.println(String.format("Current location: {%f,%f} and max is {%f,%f}", currX, currY, myOriginalMaxWidth, myOriginalMaxHeight));
    checkTopAndLeftSides(ans, currX, currY, xLocation, yLocation);
    checkRightAndBottomSides(ans, currX, currY, ans.getWidth(), ans.getHeight());

    return ans;
  }

  private void checkRightAndBottomSides(Dimension resultHolder, double currentMouseX, double currentMouseY, double defaultTranslatedX, double defaultTranslatedY){
    resultHolder.setSize(
        (myOriginalMaxWidth < currentMouseX + myNodeWidth) ? myMaxTranslate.getWidth() : defaultTranslatedX,
        (myOriginalMaxHeight < currentMouseY + myNodeHeight) ? myMaxTranslate.getHeight() : defaultTranslatedY
    );
  }

  private void checkTopAndLeftSides(Dimension resultHolder, double currentMouseX, double currentMouseY, double defaultTranslatedX, double defaultTranslatedY){
    resultHolder.setSize(
        (ORIGIN > currentMouseX) ? myMinTranslate.getWidth() : defaultTranslatedX,
        (ORIGIN > currentMouseY) ? myMinTranslate.getHeight() : defaultTranslatedY
    );
  }
}
