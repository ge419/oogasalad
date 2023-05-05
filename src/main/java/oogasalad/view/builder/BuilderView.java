package oogasalad.view.builder;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.io.File;
import java.io.IOException;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import oogasalad.controller.BuilderController;
import oogasalad.controller.builderevents.Dragger;
import oogasalad.controller.builderevents.TrailMaker;
import oogasalad.controller.builderevents.TrailMakerAPI;
import oogasalad.util.FileUploader;
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
 * BuilderView implements the JavaFX elements that composes the viewable Builder. This allows the
 * user to create and save a new game or edit an existing game
 *
 * @author tmh85
 * @author jf295
 */
public class BuilderView implements BuilderUtility, BuilderAPI {

  private static final String BASE_RESOURCE_PACKAGE = "view.builder.";
  private static final String CONSTANTS_FILE = "BuilderConstants";
  private static final String DEFAULT_STYLESHEET = "builderDefaultStyle.css";
  private static final String DEFAULT_STYLESHEET_PATH = "/view/builder/" + DEFAULT_STYLESHEET;
  private static final Logger LOG = LogManager.getLogger(BuilderView.class);
  private final String defaultStylesheet;
  private final TrailMakerAPI myTrailMaker;
  private final BuilderController myBuilderController;
  private final ResourceBundle myBuilderResource;
  private final ResourceBundle constantsResource;
  private Pane myBoardPane;
  private RulesPane myRulePane;
  private BorderPane myCenterContainer;
  private SimpleBooleanProperty myTileCreationToggle;
  private SimpleBooleanProperty myTileNextRemovalToggle;
  private SimpleBooleanProperty myDraggableObjectsToggle;
  private SimpleBooleanProperty myDeleteToggle;
  private SimpleBooleanProperty myBoardDragToggle;
  private Optional<ViewTile> myCurrentTile = Optional.empty();
  private Node myInfoText;
  private BorderPane myTopBar;
  private int myTileCount = 0;
  private int myImageCount = 0;
  private int myTrailCount = 0;
  private ItemPane mySidebar;
  private MenuItemPane myMenubar;
  private ItemPane myTopButtonBox;
  private VBox mySidepane;
  private Map<String, String> themeOptions;
  private ComboBox themeSelector;
  private Scene myScene;
  private final Stage myStage;
  private TextField descriptionInput;
  private TextField genreInput;

  /**
   * Creates an instance of the BuilderView and initializes the starting window
   *
   * @param bc             BuilderController
   * @param languageString String locale used to select ResourceBundle with proper language
   */
  @Inject
  public BuilderView(
      @Assisted BuilderController bc,
      @Assisted String languageString
  ) {
    this.myBuilderController = bc;

    myBuilderResource = ResourceBundle.getBundle(
        BASE_RESOURCE_PACKAGE + languageString + "BuilderText");
    constantsResource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + CONSTANTS_FILE);

    defaultStylesheet = getClass().getResource(DEFAULT_STYLESHEET_PATH).toExternalForm();
    initializeToggles();
    myDraggableObjectsToggle = new SimpleBooleanProperty(true);

    Scene scene = initScene();
    myTrailMaker = new TrailMaker(myBoardPane, true);
    Stage primaryStage = new Stage();
    primaryStage.setScene(scene);
    primaryStage.setTitle(myBuilderResource.getString("BuilderTitle"));
    primaryStage.show();
    myStage = primaryStage;
  }

  /**
   * Uses the BuilderController to save the current game to the database
   */
  @Override
  public void saveFile() {
    myBuilderController.save();
  }

  /**
   * Updates the text content of the top bar
   *
   * @param key String used to get the intended text from the ResourceBundle
   */
  @Override
  public void updateInfoText(String key) {
    myInfoText = makeText(key, myBuilderResource);
    myTopBar.setCenter(myInfoText);
  }

  /**
   * Unselects any selected tiles, and cancels any actions such as deleting tiles
   */
  @Override
  public void cancelAction() {
    myTileCreationToggle.set(false);
    myDeleteToggle.set(false);
    myTileNextRemovalToggle.set(false);
    //todo: make a better way of cancelling all the toggles, especially next
    myCurrentTile = Optional.empty();
    updateInfoText("RegularMode");
  }

  /**
   * Toggles Tile creation mode. If Tile creation mode is active, all clicks on the board result in
   * the creation of a new tile at the click location
   */
  @Override
  public void toggleTileCreation() {
    toggle(myTileCreationToggle, "TileAdditionMode");
  }

  /**
   * Toggles next removal. When active, next removal removes the tiles selected as the "next" tile
   * in the board sequence
   */
  public void toggleNextRemoval() {
    toggle(myTileNextRemovalToggle, "TileNextRemovalModePart1");
  }

  /**
   * Toggles the visibility of the guidelines, which show the order in which the tiles are
   * connected. Note that toggling does not change the order of tiles.
   */
  @Override
  public void toggleGuidelines() {
    myTrailMaker.toggleEnable();
  }

  /**
   * Toggles draggability of draggable objects such as tiles. When active, draggable objects can be
   * dragged in the board pane.
   */
  @Override
  public void toggleDraggables() {
    myDraggableObjectsToggle.set(!myDraggableObjectsToggle.get());
  }

  /**
   * Toggles Tile deletion mode. If Tile deletion mode is active, all clicks on a tile result in the
   * deletion of that tile
   */
  @Override
  public void toggleTileDeletion() {
    toggle(myDeleteToggle, "DeleteMode");
  }

  /**
   * Switches the view to the Rules view, where users can edit and assign rules to Tiles
   */
  public void switchToRules() {
    cancelAction();
    myRulePane.updateTileTypes();
    switchCenterPane(myRulePane);
    updateInfoText("RulesMode");
  }

  /**
   * Switches the view to the Board view, which is the standard view that appears when the builder
   * is initialized
   */
  public void switchToBoard() {
    switchCenterPane(myBoardPane);
    updateInfoText("RegularMode");
  }

  /**
   * Returns the current ResourceBundle, which is used to generate all text in the window
   *
   * @return ResourceBundle for the current language
   */
  public ResourceBundle getLanguage() {
    return myBuilderResource;
  }

  /**
   * Returns the pane reserved for displaying forms for user input
   *
   * @return Pane used for displaying user input
   */
  public Pane getPopupPane() {
    return mySidepane;
  }

  private Scene initScene() {
//    VBox topMenu = createMenus();
    myMenubar = new MenuItemPane(myBuilderResource, "MenuBar", this);
    BorderPane topBar = createTopBar();
    Node centralContainer = createCentralContainer();
    VBox root = (VBox) makeVBox("RootContainer", myMenubar.asNode(), topBar, centralContainer);

    myScene = new Scene(root, Double.parseDouble(constantsResource.getString("SCENE_WIDTH")),
        Double.parseDouble(constantsResource.getString("SCENE_HEIGHT")));
    myScene.getStylesheets().add(defaultStylesheet);
    return myScene;
  }

  /**
   * Displays the inputs for the user to input general game information. Note that this is NOT
   * generated with the popup form, since the information displayed is not for a game construct.
   * Consider this a location to get the form for miscellaneous data
   *
   * @param container Pane meant to contain the form
   */
  public void displayGameInfoForm(Pane container) {
    container.getChildren().clear();
    Text descriptionLabel = (Text) makeText("GameDescriptionLabel", myBuilderResource);
    descriptionInput = (TextField) makeTextField("GameDescriptionInput");
    Text genreLabel = (Text) makeText("GameGenreLabel", myBuilderResource);
    genreInput = (TextField) makeTextField("GameGenreInput");
    Text widthLabel = (Text) makeText("BoardWidthLabel", myBuilderResource);
    Spinner<Double> widthInput = (Spinner<Double>) makeDoubleSpinner("BoardWidthInput",
        Double.parseDouble(constantsResource.getString("BOARD_MIN_SIZE")),
        Double.parseDouble(constantsResource.getString("BOARD_MAX_SIZE")), myBoardPane.getWidth());
    widthInput.valueProperty().addListener(
        ((observable, oldValue, newValue) -> setPaneSize(myBoardPane, newValue,
            myBoardPane.getHeight())));
    widthInput.valueProperty().addListener(
        (((observable, oldValue, newValue) -> myBuilderController.updateWidth(newValue))));
    Text heightLabel = (Text) makeText("BoardHeightLabel", myBuilderResource);
    Spinner<Double> heightInput = (Spinner<Double>) makeDoubleSpinner("BoardWidthInput",
        Double.parseDouble(constantsResource.getString("BOARD_MIN_SIZE")),
        Double.parseDouble(constantsResource.getString("BOARD_MAX_SIZE")), myBoardPane.getHeight());
    heightInput.valueProperty().addListener(
        ((observable, oldValue, newValue) -> setPaneSize(myBoardPane, myBoardPane.getWidth(),
            newValue)));
    heightInput.valueProperty().addListener(
        (((observable, oldValue, newValue) -> myBuilderController.updateHeight(newValue))));
    container.getChildren().add(new HBox(descriptionLabel, descriptionInput));
    container.getChildren().add(new HBox(genreLabel, genreInput));
    container.getChildren().add(new HBox(widthLabel, widthInput));
    container.getChildren().add(new HBox(heightLabel, heightInput));
  }

  /**
   * Saves the Game Info to the database using the BuilderController
   */
  public void saveGameInfo() {
    myBuilderController.saveInfo(genreInput.getCharacters().toString(), descriptionInput.getCharacters().toString());
    //TODO: update board dimension
  }

  /**
   * Prompts the user to input a thumbnail image for the game, and uploads the image to the
   * database
   */
  public void uploadThumbnailImage() {
    FileUploader.uploadGameThumbnail(myBuilderController.getGameID());
  }

  private BorderPane createTopBar() {
    BorderPane topBar = new BorderPane();
    myTopBar = topBar;
    Node title = makeText("BuilderHeader", myBuilderResource);
    Node text = makeText("RegularMode", myBuilderResource);
    myInfoText = text;
    myTopButtonBox = new ItemPane(myBuilderResource, "ButtonBox", this);
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
    mySidebar = new ItemPane(myBuilderResource, "SideBar1", this);
    mySidebar.addItems("SideBar1");
    myCenterContainer = new BorderPane();

    initializeBoardPane();
    initializeRulePane();

    mySidepane = new VBox();
    mySidepane.setPrefWidth(Double.parseDouble(constantsResource.getString("SIDE_PANE_WIDTH")));

    myCenterContainer.setId("CentralContainer");
    myCenterContainer.setLeft(mySidebar.asNode());
    myCenterContainer.setCenter(myBoardPane);
    myCenterContainer.setRight(mySidepane);
//    return (HBox) makeHBox("CentralContainer", sideBar1, boardPane, sidePane);
    return myCenterContainer;
  }

  private void initializeBoardPane() {
    myBoardPane = (Pane) makePane("BoardPane",
        Double.parseDouble(constantsResource.getString("PANE_WIDTH")),
        Double.parseDouble(constantsResource.getString("PANE_HEIGHT")));
    setPaneSize(myBoardPane, Double.parseDouble(constantsResource.getString("PANE_WIDTH")),
        Double.parseDouble(constantsResource.getString("PANE_HEIGHT")));
    createBoardEventFilters();
    initializeBoardDragger();

    LOG.debug("Initialized board pane successfully.");
  }

  private void createBoardEventFilters() {
    myBoardPane.setOnMouseClicked(this::handleBoardClick);
    myBoardPane.addEventFilter(TileEvent.DELETE_TILE, this::deleteTile);
    myBoardPane.addEventFilter(TileEvent.SET_NEXT_TILE, this::createTilePath);
    myBoardPane.addEventFilter(TileEvent.DELETE_NEXT_TILE, this::deleteTilePath);
    myBoardPane.addEventFilter(TileEvent.SHOW_FORM, this::displayTileForm);
    myBoardPane.addEventFilter(TileEvent.SELECT_TILE, this::selectTile);
  }

  private void initializeBoardDragger() {
    Dragger boardDragger = new Dragger(myBoardPane, false, MouseButton.PRIMARY, myCenterContainer);
    myBoardDragToggle = new SimpleBooleanProperty();
    myBoardDragToggle.addListener(((observable, oldValue, newValue) -> {
      boardDragger.setDraggable(newValue);
    }));
  }

  private void removeBoardEventFilters() {
    myBoardPane.removeEventFilter(MouseEvent.MOUSE_CLICKED, this::handleBoardClick);
    myBoardPane.removeEventFilter(TileEvent.DELETE_TILE, this::deleteTile);
    myBoardPane.removeEventFilter(TileEvent.SET_NEXT_TILE, this::createTilePath);
    myBoardPane.removeEventFilter(TileEvent.DELETE_NEXT_TILE, this::deleteTilePath);
    myBoardPane.removeEventFilter(TileEvent.SHOW_FORM, this::displayTileForm);
    myBoardPane.removeEventFilter(TileEvent.SELECT_TILE, this::selectTile);
  }

  private void initializeRulePane() {
    myRulePane = new RulesPane(this, myBuilderController, myBuilderResource);
    setPaneSize(myRulePane, Double.parseDouble(constantsResource.getString("RULES_WIDTH")),
        Double.parseDouble(constantsResource.getString("RULES_HEIGHT")));
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

  /**
   * Allows the user to select an image from a file explorer, and displays the image on the board
   *
   * @throws IOException
   */
  public void uploadImage() throws IOException {
    Optional<File> file = fileLoad(myBuilderResource, "UploadImageTitle");

    if (checkIfImage(file)) {
      LOG.info("Got an image from: " + file.get().getPath());
      double imageSize = Double.parseDouble(constantsResource.getString("IMAGE_SIZE"));
      Optional<BoardImageTile> ourImage = turnFileToImage(file.get(), imageSize, imageSize,
          new Coordinate(0, 0, 0));
      //TODO: call save asset in the BuilderController
      if (ourImage.isEmpty()) {
        return;
      }
      initializeNode(ourImage.get(), "Image" + myImageCount,
          e -> myBuilderController.createPopupForm(
              ourImage.get().getBoardImage(), myBuilderResource, mySidepane));
      myImageCount++;
//      myBuilderController.saveImage(file.get().toPath());
    } else {
      //
      LOG.warn("ERROR -- Got a non-image or nothing from file.");
      showError("EmptyFileError");
    }
  }

  // todo: support different tile types.
  private void createTile(MouseEvent e) {
    ViewTile tile = myBuilderController.addTile(e);
    loadTile(tile);
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
    if (myTileCreationToggle.get()) {
      myTileCreationToggle.set(false);
      updateInfoText("RegularMode");
    }
    if (myDeleteToggle.get()) {
      TileEvent event = new TileEvent(TileEvent.DELETE_TILE, tile);
      myBoardPane.fireEvent(event);
    } else if (myCurrentTile.isPresent() && myCurrentTile.get() != tile
        && myTileNextRemovalToggle.get()) {
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

  private void deleteTilePath(TileEvent event) {
    if (myBuilderController.removeNext(myCurrentTile.get().getTileId(),
        event.getViewTile().getTileId())) {
      myTrailMaker.removeTrail(myCurrentTile.get().asNode(), event.getViewTile().asNode());
      myTrailCount--;
      cancelAction();
    } else {
      showError("DeletingImaginaryPathError");
    }
  }

  private void displayTileForm(TileEvent event) {
    myCurrentTile = Optional.empty();
    myBuilderController.createPopupForm(event.getTile(), myBuilderResource, mySidepane);
    updateInfoText("RegularMode");
  }

  private void selectTile(TileEvent event) {
    myCurrentTile = Optional.ofNullable(event.getViewTile());
    if (myTileNextRemovalToggle.get()) {
      updateInfoText("TileNextRemovalModePart2");
    } else {
      updateInfoText("TileSelectMode");
    }
  }

  private void setNextTile(ViewTile origin, ViewTile desiredNext) {
    if (myBuilderController.addNext(origin.getTileId(), desiredNext.getTileId())) {
      myTrailMaker.createTrailBetween(desiredNext.asNode(), origin.asNode(), "test" + myTrailCount);
      myTrailCount++;
    } else {
      // Can be an error if desired.
      showError("SamePathCreationError");
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

  private Optional<BoardImageTile> turnFileToImage(File file, double width, double height,
      Coordinate location) throws IOException {
    Optional<BoardImageTile> image = myBuilderController.createBoardImage(file.toPath());
    if (image.isEmpty()) {
      return Optional.empty();
    }
    image.get().setPreserveRatio(true);
    image.get().setImage(new Image(
            file.toURI().toString(),
            width,
            height,
            true,
            true
        )
    );
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

  private void initializeToggles() {
    myDraggableObjectsToggle = new SimpleBooleanProperty(true);
    myDeleteToggle = new SimpleBooleanProperty(false);
    myTileCreationToggle = new SimpleBooleanProperty(false);
    myTileNextRemovalToggle = new SimpleBooleanProperty(false);
  }

  /**
   * Displays a basic about window for the project.
   */
  public void displayAboutWindow() {
    new AboutView(myBuilderResource, DEFAULT_STYLESHEET);
  }

  private void toggle(SimpleBooleanProperty toggle, String resourceKey) {
    cancelAction();
    toggle.set(true);
    updateInfoText(resourceKey);
  }

  /**
   * Adds a ViewTile to the board
   *
   * @param tile ViewTile
   */
  public void loadTile(ViewTile tile) {
    tile.asNode().setOnMouseDragged(event -> fireDragEvent(event, tile));
    initializeNode(tile.asNode(), "Tile" + myTileCount, tile_e -> handleTileClick(tile));
    myTileCount++;
  }

  /**
   * Sets the size of the board using a width and height
   *
   * @param width  double
   * @param height double
   */
  public void loadBoardSize(double width, double height) {
    setPaneSize(myBoardPane, width, height);
  }

  /**
   * Displays an error message to the user. The message is looked up from the ResourceBundle using
   * the provided resourceKey
   *
   * @param resourceKey String used to look up error message
   */
  public void showError(String resourceKey) {
    ErrorHandler.displayError(myBuilderResource.getString(resourceKey));
  }

  /**
   * Toggles whether the board can be dragged by the user
   */
  public void toggleBoardDrag() {
    cancelAction();
    if (myBoardDragToggle.get()) {
      createBoardEventFilters();
      updateInfoText("RegularMode");
    } else {
      removeBoardEventFilters();
      updateInfoText("BoardDragMode");
    }
    myBoardDragToggle.set(!myBoardDragToggle.get());
  }

}
