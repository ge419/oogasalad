package oogasalad.controller.builderevents;

import java.awt.Dimension;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Transform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// another very helpful stack overflow post: https://stackoverflow.com/questions/57751706/why-does-some-nodes-have-a-x-and-y-position-and-others-not

/**
 * <p>Implementation of the DraggerAPI, which commits to creating an object that will allow any
 * node to be draggable based on a simple boolean statement.</p>
 *
 * <p>The dragger also needs the parent that holds the node that will be dragged. This is so that
 * the dragger can know the correct dragging bounds to prevent the node from dragging outside of the
 * parent.</p>
 *
 * <p>This was heavily inspired by <a href="https://edencoding.com/drag-shapes-javafx/">this
 * dragging tutorial</a> by Ed Eden-Rump. Thank you!</p>
 *
 * @author tmh85
 * @see DraggerAPI
 */
public class Dragger implements DraggerAPI {

  private static final Logger LOG = LogManager.getLogger(Dragger.class);
  private static final int ACTIVE = 1;
  private static final int INACTIVE = 0;
  private static final double ORIGIN = 0.0;
  private final Node myNode;
  private final MouseButton myDragButton;
  private BooleanProperty myDraggable;
  private int myCycleStatus;
  private EventHandler<MouseEvent> myAnchoredEvent;
  private double myRelativeToSceneInitialX;
  private double myRelativeToSceneInitialY;
  private EventHandler<MouseEvent> myPositionUpdater;
  private EventHandler<MouseEvent> myFinalPositionSetter;
  private double mySceneOffsetX = 0;
  private double mySceneOffsetY = 0;
  private double myNodeWidth;
  private double myNodeHeight;
  private double myMaxHeight;
  private double myMaxWidth;
  private double myMaxWidthAtStartOfDrag;
  private double myMaxHeightAtStartOfDrag;
  private final Dimension myMaxTranslate = new Dimension();
  private final Dimension myMinTranslate = new Dimension();


  public Dragger(Node ourObject, Node parent) {
    this(ourObject, false, MouseButton.PRIMARY, parent);
  }

  public Dragger(Node ourObject, boolean canWeDrag, MouseButton dragButton, Node parent) {
    myNode = ourObject;
    createEventHandlers();
    initializeDraggableProperty();
    setDraggable(canWeDrag);
    myDragButton = dragButton;
    initializeNodeListener();
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
        myMaxWidthAtStartOfDrag = myMaxWidth;
        myMaxHeightAtStartOfDrag = myMaxHeight;
        setMaxAndMinTranslateValues();
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
        myNode.relocate(getNodeXLocationInParent(), getNodeYLocationInParent());
        myNode.setTranslateX(0);
        myNode.setTranslateY(0);
      }
    };
  }

  /**
   * <p>Initialize our draggable property by connecting it to our created event handlers.</p>
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

  /**
   * <p>Initializes the listener for the size of the parent node.</p>
   * <p>If the size of the parent node changes, we update our saved maximum values for the
   * parent node, which will be used in our dragging.</p>
   *
   * @param parent parent node of the draggable object
   */
  private void initializeParentSizeListener(Node parent) {
    updateBounds(parent.getBoundsInParent());

    parent.boundsInParentProperty().addListener(((observable, oldValue, newValue) -> {
      updateBounds(newValue);
    }));
  }

  /**
   * <p>Initializes a listener for the transformation matrix that converts the parent to the scene
   * itself.</p>
   * <p>When we pickup a change in the transformation matrix, we update the parents' bounds
   * by this transformation, and then update our instance variables for our scene offset in use in
   * dragging.</p>
   *
   * @param parent parent node of the draggable object
   */
  private void initializeParentTransformListener(Node parent) {
    transformPoints(parent.getLocalToSceneTransform(), parent.getBoundsInLocal());

    parent.localToSceneTransformProperty().addListener(((observable, oldValue, newValue) -> {
      transformPoints(newValue, parent.getBoundsInLocal());
    }));
  }

  /**
   * <p>Initializes the listener for the draggable node itself.</p>
   * <p>If we detect changes in this nodes' local property, we will update the saved
   * width and height values of the node that is used in dragging.</p>
   */
  private void initializeNodeListener() {
    updateSavedSizes();

    myNode.boundsInLocalProperty().addListener(((observable, oldValue, newValue) -> {
      updateSavedSizes();
    }));
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * <p>Sets the maximum translation based on the current maximum width and height of the parent
   * pane
   * and the current location of the node.</p>
   *
   * <p>Sets the minimum translation based off the origin point of the parent (assumed to be
   * zero) and the current location of the node.</p>
   *
   * <p>Note that maximum translate also factors in the width and height of the node itself.</p>
   */
  private void setMaxAndMinTranslateValues() {
    myMaxTranslate.setSize(
        myMaxWidthAtStartOfDrag - getNodeXLocationInParent() - myNodeWidth,
        myMaxHeightAtStartOfDrag - getNodeYLocationInParent() - myNodeHeight
    );
    myMinTranslate.setSize(
        ORIGIN - getNodeXLocationInParent(),
        ORIGIN - getNodeYLocationInParent()
    );
  }

  /**
   * <p>Updates the saved instance variables, myNodeWidth and myNodeHeight, with the current
   * height and width of the draggable node.</p>
   */
  private void updateSavedSizes() {
    myNodeWidth = myNode.getBoundsInLocal().getWidth();
    myNodeHeight = myNode.getBoundsInLocal().getHeight();
  }

  /**
   * <p>Updates the saved instance variables, myMaxWidth and myMaxHeight, with the current height
   * and width of the parent node.</p>
   *
   * @param parentBounds bounds of the parent node
   */
  private void updateBounds(Bounds parentBounds) {
    myMaxWidth = parentBounds.getWidth();
    myMaxHeight = parentBounds.getHeight();
  }

  /**
   * <p>Transforms the minimum X and Y of a given bound by a provided transform matrix.</p>
   * <p>The result of this is then saved to the mySceneOffset instance variables for use in
   * dragging.</p>
   *
   * @param transformMatrix transformation matrix
   * @param desiredBounds   bounds that we wish to transform
   */
  private void transformPoints(Transform transformMatrix, Bounds desiredBounds) {
    Point2D transformed = transformMatrix.transform(desiredBounds.getMinX(),
        desiredBounds.getMinY());
    mySceneOffsetX = transformed.getX();
    mySceneOffsetY = transformed.getY();
  }

  private double getNodeXLocationInParent() {
    return myNode.getBoundsInParent().getMinX();
  }

  private double getNodeYLocationInParent() {
    return myNode.getBoundsInParent().getMinY();
  }

  /**
   * Given a coordinate in the coordinate space of the scene, convert it to a coordinate relative to
   * the parent node.
   *
   * @param sceneValueX
   * @param sceneValueY
   * @return dimension that contains coordinates in the coordinate space of the parent
   */
  private Dimension getLocationRelativeToParent(double sceneValueX, double sceneValueY) {
    Dimension location = new Dimension();
    location.setSize(
        sceneValueX - mySceneOffsetX,
        sceneValueY - mySceneOffsetY
    );
    return location;
  }

  /**
   * <p>Checks the area clicked by the mouse to see if it is within the bounds of the parent.</p>
   * <p>If the area is within bounds, the given defaultXTranslation and defaultYTranslation are
   * returned back to the
   * user in a dimension object.</p>
   * <p>If the area is not within bounds, the result inside of the dimension object will be the
   * edge in the parent node closest to the mouse event (dictated by myMinTranslate and
   * myMaxTranslate
   * </p>
   *
   * @param event               mouse event
   * @param defaultXTranslation original x translation decided before checks
   * @param defaultYTranslation original y translation decided before checks
   * @return a dimension containing the correct translation in each direction
   */
  private Dimension checkForBounds(MouseEvent event, double defaultXTranslation,
      double defaultYTranslation) {
    Dimension ans = new Dimension();
    Dimension currMouseLocation = getLocationRelativeToParent(event.getSceneX(), event.getSceneY());
    LOG.debug(
        String.format("Current location: {%f,%f} and max is {%f,%f}", currMouseLocation.getWidth(),
            currMouseLocation.getHeight(),
            myMaxWidthAtStartOfDrag, myMaxHeightAtStartOfDrag));
    checkTopAndLeftSides(ans, currMouseLocation.getWidth(), currMouseLocation.getHeight(),
        defaultXTranslation, defaultYTranslation);
    checkRightAndBottomSides(ans, currMouseLocation.getWidth(), currMouseLocation.getHeight(),
        ans.getWidth(), ans.getHeight());

    return ans;
  }

  /**
   * <p> Checks if the mouse click is within the bounds of the right and bottom sides of the parent
   * pane</p>
   *
   * @param resultHolder
   * @param currentMouseX
   * @param currentMouseY
   * @param defaultTranslatedX
   * @param defaultTranslatedY
   */
  private void checkRightAndBottomSides(Dimension resultHolder, double currentMouseX,
      double currentMouseY, double defaultTranslatedX, double defaultTranslatedY) {
    resultHolder.setSize(
        (myMaxWidthAtStartOfDrag < currentMouseX + myNodeWidth) ? myMaxTranslate.getWidth()
            : defaultTranslatedX,
        (myMaxHeightAtStartOfDrag < currentMouseY + myNodeHeight) ? myMaxTranslate.getHeight()
            : defaultTranslatedY
    );
  }

  /**
   * <p>Checks if the mouse click is within the bounds of the left and top sides of the parent
   * pane.</p>
   *
   * @param resultHolder
   * @param currentMouseX
   * @param currentMouseY
   * @param defaultTranslatedX
   * @param defaultTranslatedY
   */
  private void checkTopAndLeftSides(Dimension resultHolder, double currentMouseX,
      double currentMouseY, double defaultTranslatedX, double defaultTranslatedY) {
    resultHolder.setSize(
        (ORIGIN > currentMouseX) ? myMinTranslate.getWidth() : defaultTranslatedX,
        (ORIGIN > currentMouseY) ? myMinTranslate.getHeight() : defaultTranslatedY
    );
  }
}
