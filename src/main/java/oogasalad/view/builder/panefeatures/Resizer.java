package oogasalad.view.builder.panefeatures;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

// TODO: will be refactored to resize using mouse drag

public class Resizer implements ResizerAPI{

  private static final int ACTIVE = 1;
  private static final int INACTIVE = 0;

  private final Node myNode;
  private final MouseButton myResizeButton;
  private BooleanProperty myResizable;
  private int myResizeStatus;
  private EventHandler<MouseEvent> myAnchoredEvent;
  private EventHandler<MouseEvent> mySizeUpdater;
  private EventHandler<MouseEvent> myFinalSizeSetter;

  // TODO: Size set as 100 * 100 by default, needs to be changed
  public Resizer(Node object) {
    this(object, false, new Size(20, 20), MouseButton.PRIMARY);
  }

  public Resizer(Node object, boolean resizable, Size size, MouseButton resizeButton) {
    myNode = object;
    setResizable(resizable);
    myResizeButton = resizeButton;
  }

  public void setResizable(boolean resizeStatus) {
    this.myResizable.set(resizeStatus);
  }

  public BooleanProperty getResizableProperty() {
    return myResizable;
  }

  public boolean getResizeStatus(){
    return myResizable.get();
  }

  private void updateSize(Size size) {
    myNode.resize(size.width(), size.height());
  }

  ///////////////////////////////////////////////////////////
  // Code below not used as of now

  private void createEventHandlers() {
    myAnchoredEvent = e1 -> {
      if (e1.getButton() == myResizeButton) {
        myResizeStatus = ACTIVE;
//        myRelativeToSceneInitialX = e1.getSceneX();
//        myRelativeToSceneInitialY = e1.getSceneY();
//        myOriginalMouseOffsetX = e1.getX();
//        myOriginalMouseOffsetY = e1.getY();
        System.out.println(e1.getX() + " " + e1.getY());
        System.out.println(e1.getSceneX() + " " + e1.getSceneY());
        // get initial width
        // get initial height

      }
    };
    mySizeUpdater = e2 -> {
      if (myResizeStatus != INACTIVE) {
        myNode.getBoundsInParent().getWidth();
      }
     };
    myFinalSizeSetter = e3 -> {

    };
  }

  private void initializeResizableProperty() {
    myResizable = new SimpleBooleanProperty();
    myResizable.addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        myNode.addEventFilter(MouseEvent.MOUSE_PRESSED, myAnchoredEvent);
        myNode.addEventFilter(MouseEvent.MOUSE_DRAGGED, mySizeUpdater);
        myNode.addEventFilter(MouseEvent.MOUSE_RELEASED, myFinalSizeSetter);
      } else {
        myNode.removeEventFilter(MouseEvent.MOUSE_PRESSED, myAnchoredEvent);
        myNode.removeEventFilter(MouseEvent.MOUSE_DRAGGED, mySizeUpdater);
        myNode.removeEventFilter(MouseEvent.MOUSE_RELEASED, myFinalSizeSetter);
      }
    });
  }
}
