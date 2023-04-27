package oogasalad.view.builder;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.io.File;
import java.util.Enumeration;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import oogasalad.controller.BuilderController;
import oogasalad.controller.builderevents.TrailMaker;
import oogasalad.controller.builderevents.TrailMakerAPI;
import oogasalad.view.Coordinate;
import oogasalad.view.builder.bars.BuilderMenubar;
import oogasalad.view.builder.bars.Sidebar;
import oogasalad.view.builder.board.BoardInfo;
import oogasalad.view.builder.events.TileEvent;
import oogasalad.view.builder.gameholder.ImmutableGameHolder;
import oogasalad.view.builder.popupform.PopupForm;
import oogasalad.view.tiles.ViewTile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// https://stackoverflow.com/questions/31148690/get-real-position-of-a-node-in-javafx
// assumptions made so far:

/**
 * BuilderView implements the JavaFX elements that composes the viewable Builder.
 *
 * @author tmh85
 * @author jf295
 */
public class BuilderView implements BuilderUtility, BuilderAPI {

  private static final String BASE_RESOURCE_PACKAGE = "view.builder.";
  private static final String DEFAULT_STYLESHEET = "/view/builder/builderDefaultStyle.css";

  private static final double PANE_WIDTH = 500;
  private static final double PANE_HEIGHT = 500;
  private static final double SCENE_WIDTH = 900;
  private static final double SCENE_HEIGHT = 600;
  private static final Logger LOG = LogManager.getLogger(BuilderView.class);
  private ResourceBundle builderResource;
  private Pane myBoardPane;
  private final String defaultStylesheet;
  private boolean myTileCreationToggle = false;
  private Node myInfoText;
  private BorderPane myTopBar;
  private int myTileCount = 0;
  private Optional<ViewTile> myCurrentTile = Optional.empty();
  private Sidebar mySidebar;
  private BuilderMenubar myMenubar;
  private Sidebar myTopButtonBox;
  private final TrailMakerAPI myTrailMaker;
  private final boolean myDraggableObjectsToggle = true;
  private boolean myDeleteToggle = false;
  private final BuilderController bc;
  private VBox sidePane;


  @Inject
  public BuilderView(
      @Assisted BuilderController bc,
      @Assisted String languageString
  ) {
    this.bc = bc;
    builderResource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + languageString + "BuilderText");

    defaultStylesheet = getClass().getResource(DEFAULT_STYLESHEET).toExternalForm();

    Scene scene = initScene();
    myTrailMaker = new TrailMaker(myBoardPane, true);
    Stage primaryStage = new Stage();
    primaryStage.setScene(scene);
    primaryStage.setTitle(builderResource.getString("BuilderTitle"));
    primaryStage.show();
//    System.out.println(myBoardPane.localToScene(myBoardPane.getBoundsInLocal()).getWidth());
//    System.out.println(myBoardPane.localToScene(myBoardPane.getBoundsInLocal()).getHeight());
  }

  @Override
  public void saveFile(){
    Optional<File> file = directoryGet(builderResource, "SaveGameTitle");
    if (file.isPresent()) {
//      ImmutableGameHolder game = createGameHolder();
      String givenDirectory = file.get().getPath();
      // Send file to the controller to properly save.
      //Controller.save(ImmutableGameHolder);
      //bc.save();
      System.out.println(givenDirectory);
      //return Optional.ofNullable(game);
    } else {
      // todo: replace with LOG
      System.out.println("Ruh-roh, can't save to a file that doesn't exist!");
      new Alert(Alert.AlertType.ERROR, builderResource.getString("FileNotFoundError"));
    }
  }

  @Override
  public void loadFile() {
    Optional<File> file = directoryGet(builderResource, "LoadGameTitle");
    if (file.isPresent()){
      System.out.println("Given directory: " + file.get().getPath());
      bc.load(file.get().getPath());
    }
    else{
      // todo: display error
    }
  }
  @Override
  public void updateInfoText(String key){
    myInfoText = makeText(key, builderResource);
    myTopBar.setCenter(myInfoText);
  }
  @Override
  public void cancelAction(){
    myTileCreationToggle = false;
    myDeleteToggle = false;
    //todo: make a better way of cancelling all the toggles, especially next
    myCurrentTile.ifPresent(viewTile -> viewTile.setColor(Color.LIGHTBLUE));
    myCurrentTile = Optional.empty();
    updateInfoText("RegularMode");
  }
  @Override
  public void toggleTileCreation() {
    myTileCreationToggle = true;
    updateInfoText("TileAdditionMode");
  }

  public void toggleGuidelines(){
    myTrailMaker.toggleEnable();
  }
  public void toggleDraggables(){
    // todo
  }

  public void toggleTileDeletion() {
    myDeleteToggle = !myDeleteToggle;
    if (myDeleteToggle){
      updateInfoText("DeleteMode");
    }
    else{
      updateInfoText("RegularMode");
    }
  }

  private Scene initScene() {
//    VBox topMenu = createMenus();
    myMenubar = new BuilderMenubar(builderResource, "MenuBar", this);
    BorderPane topBar = createTopBar();
    Node centralContainer = createCentralContainer();
    VBox root = (VBox) makeVBox("RootContainer", myMenubar.asNode(), topBar, centralContainer);

    Scene newScene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    newScene.getStylesheets().add(defaultStylesheet);
    return newScene;
  }

  private BorderPane createTopBar() {
    BorderPane topBar = new BorderPane();
    myTopBar = topBar;
    Node title = makeText("BuilderHeader", builderResource);
    Node text = makeText("RegularMode", builderResource);
    myInfoText = text;
    myTopButtonBox = new Sidebar(builderResource, "ButtonBox", this);
    myTopButtonBox.addItems("TopBar");
//    HBox buttonBox = (HBox) makeHBox("TopBar");
//    addButtonsToPane(buttonBox, topBarResource);

    topBar.setLeft(title);
    topBar.setCenter(myInfoText);
    topBar.setRight(myTopButtonBox.asNode());
    topBar.setId("TopBar");
    topBar.getStyleClass().add("topBar");

    return topBar;
  }

  private Pane createCentralContainer() {
    mySidebar = new Sidebar(builderResource, "SideBar1", this);
    mySidebar.addItems("SideBar1");

    Node boardPane = makePane("BoardPane", PANE_WIDTH, PANE_HEIGHT);
    myBoardPane = (Pane) boardPane;
    setBoardSize(PANE_WIDTH, PANE_HEIGHT);

    myBoardPane.setOnMouseClicked(e -> handleBoardClick(e));
    myBoardPane.addEventFilter(TileEvent.DELETE_TILE, e -> deleteTile(e));
    myBoardPane.addEventFilter(TileEvent.SET_NEXT_TILE, e -> createTilePath(e));
    myBoardPane.addEventFilter(TileEvent.SHOW_FORM, e -> displayTileForm(e));
    myBoardPane.addEventFilter(TileEvent.SELECT_TILE, e -> selectTile(e));

    sidePane = new VBox();
    sidePane.setPrefWidth(300);

    BorderPane pane = new BorderPane();
    pane.setId("CentralContainer");
    pane.setLeft(mySidebar.asNode());
    pane.setCenter(boardPane);
    pane.setRight(sidePane);

//    return (HBox) makeHBox("CentralContainer", sideBar1, boardPane, sidePane);
    return pane;
  }

  private void setBoardSize(double width, double height){
    myBoardPane.setMinSize(width, height);
    myBoardPane.setMaxSize(width, height);
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////

  private void uploadImage() {
    // todo: redo this method
//    Optional<File> file = fileLoad(builderResource, "UploadImageTitle");
//
//    if (checkIfImage(file)) {
//      System.out.println("Got an image from: " + file.get().getPath());
//      ImageView ourImage = turnFileToImage(file.get(), DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT,
//          new Coordinate(0, 0, 0));
//      initializeNode(ourImage, "Image" + myImageCount, e->handleImageClick(ourImage));
//      myImageCount++;
//    } else {
//      //
//      System.out.println("ERROR -- Got a non-image or nothing from file.");
//      new Alert(Alert.AlertType.ERROR, builderResource.getString("EmptyFileError"));
//    }
  }

  // todo: support different tile types.
  private void createTile(MouseEvent e) {
    ViewTile tile = bc.addTile(e);
    tile.asNode().setOnMouseDragged(event -> fireDragEvent(event, tile));
    initializeNode(tile.asNode(), "Tile" + myTileCount, tile_e -> handleTileClick(tile));
    myTileCount++;
    myTileCreationToggle = false;
    updateInfoText("RegularMode");
  }
  private void fireDragEvent(MouseEvent event, ViewTile tile) {
    TileEvent tileEvent = new TileEvent(TileEvent.DRAG_TILE, tile);
    myBoardPane.fireEvent(tileEvent);
  }
  private void handleBoardClick(MouseEvent e) {
    System.out.println("hello, you clicked on x: " + e.getSceneX() + " and y: " + e.getSceneY());
    System.out.println("relative x: " + e.getX() + " and y: " + e.getY());

    if (myTileCreationToggle) {
      createTile(e);
    }
  }

  private void handleTileClick(ViewTile tile) {
    if (myDeleteToggle) {
      TileEvent event = new TileEvent(TileEvent.DELETE_TILE, tile);
      myBoardPane.fireEvent(event);
    } else if (myCurrentTile.isPresent() && myCurrentTile.get() != tile) {
      TileEvent event = new TileEvent(TileEvent.SET_NEXT_TILE, tile);
      myBoardPane.fireEvent(event);
    } else if (myCurrentTile.isPresent() && myCurrentTile.get() == tile) {
      TileEvent event = new TileEvent(TileEvent.SHOW_FORM, tile);
      myBoardPane.fireEvent(event);
    } else if (myCurrentTile.isEmpty()) {
      TileEvent event = new TileEvent(TileEvent.SELECT_TILE, tile);
      myBoardPane.fireEvent(event);
    }
  }
  private void deleteTile(TileEvent event) {
    bc.removeTile(event.getViewTile().getTileId());
    myTrailMaker.removeTrail(event.getViewTile().asNode());
    myTileCount = deleteNode(event.getViewTile().asNode(), myTileCount);
    myCurrentTile = Optional.empty();
  }
  private void createTilePath(TileEvent event) {
    event.getViewTile().setColor(Color.LIGHTGREEN);
    setNextTile(myCurrentTile.get(), event.getViewTile());
    myCurrentTile.get().setColor(Color.LIGHTBLUE);
    myCurrentTile = Optional.empty();
    event.getViewTile().setColor(Color.LIGHTBLUE);
    updateInfoText("RegularMode");
  }
  private void displayTileForm(TileEvent event) {
    myCurrentTile = Optional.empty();
    event.getViewTile().setColor(Color.LIGHTBLUE);
    new PopupForm(event.getTile(), builderResource, sidePane);
    updateInfoText("RegularMode");
  }
  private void selectTile(TileEvent event) {
    myCurrentTile = Optional.ofNullable(event.getViewTile());
    myCurrentTile.get().setColor(Color.BLUE);
    updateInfoText("TileNextMode");
  }

  private void setNextTile(ViewTile origin, ViewTile desiredNext){
    bc.addNext(origin.getTileId(), desiredNext.getTileId());
    myTrailMaker.createTrailBetween(desiredNext.asNode(), origin.asNode(), "test" + myTileCount);
  }

  private int deleteNode(Node node, int objCounter) {
    myBoardPane.getChildren().remove(node);
    toggleTileDeletion();
    return objCounter--;
  }


  // -----------------------------------------------------------------------------------------------------------------
  private boolean checkIfImage(Optional<File> thing) {
    final String IMAGE_FILE_SUFFIXES = String.format(".*\\.(%s)",
        String.join("|", ImageIO.getReaderFileSuffixes()));
    return thing.isPresent() && thing.get().getName().matches(IMAGE_FILE_SUFFIXES);
  }

  private ImageView turnFileToImage(File file, double width, double height, Coordinate location) {
    ImageView image = new ImageView(new Image(
        file.toURI().toString(),
        width,
        height,
        true,
        true
    )
    );

    setNodeLocation(image, location);
    return image;
  }

  private void initializeNode(Node node, String identifier, EventHandler<MouseEvent> mouseClickHandle){
    bc.createEventsForNode(node,
        mouseClickHandle,
        myBoardPane);
    node.setId(identifier);
    node.getStyleClass().add("clickable");
    myBoardPane.getChildren().add(node);
  }
}
