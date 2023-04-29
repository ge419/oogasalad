package oogasalad.view.builder;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.io.File;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import oogasalad.controller.BuilderController;
import oogasalad.controller.builderevents.TrailMaker;
import oogasalad.controller.builderevents.TrailMakerAPI;
import oogasalad.view.Coordinate;
import oogasalad.view.builder.events.TileEvent;
import oogasalad.view.builder.itempanes.ItemPane;
import oogasalad.view.builder.itempanes.MenuItemPane;
import oogasalad.view.builder.rules.RulesPane;
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
  private static final String DEFAULT_STYLESHEET = "builderDefaultStyle.css";
  private static final String DEFAULT_STYLESHEET_PATH = "/view/builder/" + DEFAULT_STYLESHEET;

  private static final double PANE_WIDTH = 500;
  private static final double PANE_HEIGHT = 500;
  private static final double SCENE_WIDTH = 900;
  private static final double SCENE_HEIGHT = 600;
  private static final Logger LOG = LogManager.getLogger(BuilderView.class);
  private ResourceBundle builderResource;
  private Pane myBoardPane;
  private RulesPane myRulePane;
  private BorderPane myCenterContainer;
  private final String defaultStylesheet;
  private SimpleBooleanProperty myTileCreationToggle;
  private SimpleBooleanProperty myTileNextRemovalToggle;
  private SimpleBooleanProperty myDraggableObjectsToggle;
  private SimpleBooleanProperty myDeleteToggle;
  private Optional<ViewTile> myCurrentTile = Optional.empty();
  private Node myInfoText;
  private BorderPane myTopBar;
  private int myTileCount = 0;
  private int myTrailCount = 0;
  private ItemPane mySidebar;
  private MenuItemPane myMenubar;
  private ItemPane myTopButtonBox;
  private final TrailMakerAPI myTrailMaker;
  private final BuilderController myBuilderController;
  private VBox sidePane;
  private Map<String, String> themeOptions;
  private ComboBox themeSelector;
  private Scene myScene;
  private Stage myStage;

  @Inject
  public BuilderView(
      @Assisted BuilderController bc,
      @Assisted String languageString
  ) {
    this.myBuilderController = bc;
    builderResource = ResourceBundle.getBundle(
        BASE_RESOURCE_PACKAGE + languageString + "BuilderText");

    defaultStylesheet = getClass().getResource(DEFAULT_STYLESHEET_PATH).toExternalForm();
    initializeToggles();
    myDraggableObjectsToggle = new SimpleBooleanProperty(true);

    Scene scene = initScene();
    myTrailMaker = new TrailMaker(myBoardPane, true);
    Stage primaryStage = new Stage();
    primaryStage.setScene(scene);
    primaryStage.setTitle(builderResource.getString("BuilderTitle"));
    primaryStage.show();
    myStage = primaryStage;
  }

  @Override
  public void saveFile() {
    myBuilderController.save();
//    Optional<File> file = directoryGet(builderResource, "SaveGameTitle");
//    if (file.isPresent()) {
//      String givenDirectory = file.get().getPath();
//      myBuilderController.save();
//      LOG.info("Saved to directory: " + givenDirectory);
//    } else {
//      LOG.error(
//          "Either cancelled out of file save window or tried to save to a file that doesn't exist.");
//      ErrorHandler.displayError(builderResource.getString("FileNotFoundError"));
//    }
  }

  @Override
  public void updateInfoText(String key) {
    myInfoText = makeText(key, builderResource);
    myTopBar.setCenter(myInfoText);
  }

  @Override
  public void cancelAction() {
    myTileCreationToggle.set(false);
    myDeleteToggle.set(false);
    myTileNextRemovalToggle.set(false);
    //todo: make a better way of cancelling all the toggles, especially next
    myCurrentTile = Optional.empty();
    updateInfoText("RegularMode");
  }

  @Override
  public void toggleTileCreation() {
    toggle(myTileCreationToggle, "TileAdditionMode");
  }

  public void toggleNextRemoval(){
    toggle(myTileNextRemovalToggle, "TileNextRemovalModePart1");
  }

  @Override
  public void toggleGuidelines() {
    myTrailMaker.toggleEnable();
  }

  @Override
  public void toggleDraggables() {
    myDraggableObjectsToggle.set(!myDraggableObjectsToggle.get());
  }

  @Override
  public void toggleTileDeletion() {
    toggle(myDeleteToggle, "DeleteMode");
  }

  public void switchToRules() {
    cancelAction();
    switchCenterPane(myRulePane);
    updateInfoText("RulesMode");
  }

  public void switchToBoard() {
    switchCenterPane(myBoardPane);
    updateInfoText("RegularMode");
  }

  public ResourceBundle getLanguage() {
    return builderResource;
  }

  public Pane getPopupPane() {
    return sidePane;
  }

  private Scene initScene() {
//    VBox topMenu = createMenus();
    myMenubar = new MenuItemPane(builderResource, "MenuBar", this);
    BorderPane topBar = createTopBar();
    Node centralContainer = createCentralContainer();
    VBox root = (VBox) makeVBox("RootContainer", myMenubar.asNode(), topBar, centralContainer);

    myScene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    myScene.getStylesheets().add(defaultStylesheet);
    return myScene;
  }

  private BorderPane createTopBar() {
    BorderPane topBar = new BorderPane();
    myTopBar = topBar;
    Node title = makeText("BuilderHeader", builderResource);
    Node text = makeText("RegularMode", builderResource);
    myInfoText = text;
    myTopButtonBox = new ItemPane(builderResource, "ButtonBox", this);
    myTopButtonBox.addItems("TopBar");

    themeOptions = myBuilderController.getThemeOptions();
    ObservableList<String> observableThemes = themeOptions.keySet().stream()
        .collect(Collectors.toCollection(javafx.collections.FXCollections::observableArrayList));
    themeSelector = (ComboBox) makeDropdown("ThemeSelector", observableThemes, e -> changeTheme());
    themeSelector.setValue(DEFAULT_STYLESHEET);

    topBar.setLeft(title);
    topBar.setCenter(myInfoText);
    topBar.setRight(new HBox(themeSelector, myTopButtonBox.asNode()));
    topBar.setId("TopBar");
    topBar.getStyleClass().add("topBar");

    return topBar;
  }

  private void changeTheme() {
    myScene.getStylesheets().clear();
    String newStyleSheet = getClass().getResource(themeOptions.get(themeSelector.getValue()))
        .toExternalForm();
    myScene.getStylesheets().add(newStyleSheet);
  }

  private Pane createCentralContainer() {
    mySidebar = new ItemPane(builderResource, "SideBar1", this);
    mySidebar.addItems("SideBar1");

    initializeBoardPane();
    initializeRulePane();

    sidePane = new VBox();
    sidePane.setPrefWidth(300);

    myCenterContainer = new BorderPane();
    myCenterContainer.setId("CentralContainer");
    myCenterContainer.setLeft(mySidebar.asNode());
    myCenterContainer.setCenter(myBoardPane);
    myCenterContainer.setRight(sidePane);

//    return (HBox) makeHBox("CentralContainer", sideBar1, boardPane, sidePane);
    return myCenterContainer;
  }

  private void initializeBoardPane() {
    myBoardPane = (Pane) makePane("BoardPane", PANE_WIDTH, PANE_HEIGHT);
    setPaneSize(myBoardPane, PANE_WIDTH, PANE_HEIGHT);

    myBoardPane.setOnMouseClicked(e -> handleBoardClick(e));
    myBoardPane.addEventFilter(TileEvent.DELETE_TILE, e -> deleteTile(e));
    myBoardPane.addEventFilter(TileEvent.SET_NEXT_TILE, e -> createTilePath(e));
    myBoardPane.addEventFilter(TileEvent.DELETE_NEXT_TILE, e->deleteTilePath(e));
    myBoardPane.addEventFilter(TileEvent.SHOW_FORM, e -> displayTileForm(e));
    myBoardPane.addEventFilter(TileEvent.SELECT_TILE, e -> selectTile(e));

    LOG.debug("Initialized board pane successfully.");
  }

  private void initializeRulePane() {
    myRulePane = new RulesPane(this, myBuilderController, builderResource);
    setPaneSize(myRulePane, PANE_WIDTH, PANE_HEIGHT);
    LOG.debug("Initialized rule pane successfully.");
  }

  private void setPaneSize(Pane pane, double width, double height) {
    pane.setMinSize(width, height);
    pane.setMaxSize(width, height);
  }

  private void switchCenterPane(Pane desiredPane) {
    myCenterContainer.getChildren().remove(myCenterContainer.getCenter());
    myCenterContainer.setCenter(desiredPane);
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
    ViewTile tile = myBuilderController.addTile(e);
    tile.asNode().setOnMouseDragged(event -> fireDragEvent(event, tile));
    initializeNode(tile.asNode(), "Tile" + myTileCount, tile_e -> handleTileClick(tile));
    myTileCount++;
//    myTileCreationToggle = false;
//    updateInfoText("RegularMode");
    LOG.debug("Successfully created tile " + tile.getTileId());
  }

  private void fireDragEvent(MouseEvent event, ViewTile tile) {
    TileEvent tileEvent = new TileEvent(TileEvent.DRAG_TILE, tile);
    myBoardPane.fireEvent(tileEvent);
  }

  private void handleBoardClick(MouseEvent e) {
    LOG.info(String.format(
        "User clicked on board at scene coordinates {%f, %f} and board coordinates {%f, %f}",
        e.getSceneX(), e.getSceneY(), e.getX(), e.getY()));

    if (myTileCreationToggle.get()) {
      createTile(e);
    }
  }

  private void handleTileClick(ViewTile tile) {
    LOG.debug("Clicked on tile " + tile.getTileId());
    if (myTileCreationToggle.get()){
      myTileCreationToggle.set(false);
      updateInfoText("RegularMode");
    }
    if (myDeleteToggle.get()) {
      TileEvent event = new TileEvent(TileEvent.DELETE_TILE, tile);
      myBoardPane.fireEvent(event);
    } else if (myCurrentTile.isPresent() && myCurrentTile.get() != tile && myTileNextRemovalToggle.get()) {
      TileEvent event = new TileEvent(TileEvent.DELETE_NEXT_TILE, tile);
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
    String id = event.getTile().getId();
    myBuilderController.removeTile(event.getViewTile().getTileId());
    myTrailMaker.removeTrail(event.getViewTile().asNode());
    myTileCount = deleteNode(event.getViewTile().asNode(), myTileCount);
    myCurrentTile = Optional.empty();
    LOG.debug("Deleted tile " + id);
  }

  private void createTilePath(TileEvent event) {
    setNextTile(myCurrentTile.get(), event.getViewTile());
    cancelAction();
  }

  private void deleteTilePath(TileEvent event){
    if (myBuilderController.removeNext(myCurrentTile.get().getTileId(), event.getViewTile().getTileId())){
      myTrailMaker.removeTrail(myCurrentTile.get().asNode(), event.getViewTile().asNode());
      myTrailCount--;
      cancelAction();
    }
    else{
      ErrorHandler.displayError(builderResource.getString("DeletingImaginaryPathError"));
    }
  }

  private void displayTileForm(TileEvent event) {
    myCurrentTile = Optional.empty();
    myBuilderController.createPopupForm(event.getTile(), builderResource, sidePane);
    updateInfoText("RegularMode");
  }

  private void selectTile(TileEvent event) {
    myCurrentTile = Optional.ofNullable(event.getViewTile());
    if (myTileNextRemovalToggle.get()){
      updateInfoText("TileNextRemovalModePart2");
    }
    else {
      updateInfoText("TileNextMode");
    }
  }

  private void setNextTile(ViewTile origin, ViewTile desiredNext) {
    if(myBuilderController.addNext(origin.getTileId(), desiredNext.getTileId())){
      myTrailMaker.createTrailBetween(desiredNext.asNode(), origin.asNode(), "test" + myTrailCount);
      myTrailCount++;
    }
    else{
      // Can be an error if desired.
      ErrorHandler.displayError(builderResource.getString("SamePathCreationError"));
    }
  }

  private int deleteNode(Node node, int objCounter) {
    myBoardPane.getChildren().remove(node);
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

  private void initializeNode(Node node, String identifier,
      EventHandler<MouseEvent> mouseClickHandle) {
    myBuilderController.createEventsForNode(node,
        mouseClickHandle,
        myBoardPane,
        myDraggableObjectsToggle);
    node.setId(identifier);
    node.getStyleClass().add("clickable");
    myBoardPane.getChildren().add(node);
  }
  private void initializeToggles(){
    myDraggableObjectsToggle = new SimpleBooleanProperty(true);
    myDeleteToggle = new SimpleBooleanProperty(false);
    myTileCreationToggle = new SimpleBooleanProperty(false);
    myTileNextRemovalToggle = new SimpleBooleanProperty(false);
  }

  /**
   * <p>Displays a basic about window for the project.</p>
   */
  public void displayAboutWindow() {
    new AboutView(builderResource, DEFAULT_STYLESHEET);
  }

  private void toggle(SimpleBooleanProperty toggle, String resourceKey){
      cancelAction();
      toggle.set(true);
      updateInfoText(resourceKey);
  }


}
