package oogasalad.view.builder;

import java.awt.Dimension;
import java.io.File;
import java.util.Enumeration;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import oogasalad.controller.BuilderController;
import oogasalad.view.Coordinate;
import oogasalad.view.builder.board.BoardInfo;
import oogasalad.view.builder.board.ImmutableBoardInfo;
import oogasalad.view.builder.gameholder.GameHolder;
import oogasalad.view.builder.gameholder.ImmutableGameHolder;
import oogasalad.view.tiles.BasicTile;
import oogasalad.view.tiles.ViewTile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// https://stackoverflow.com/questions/31148690/get-real-position-of-a-node-in-javafx
// assumptions made so far: board pane cannot be dragged (if it was, this would break dragging for
// all other tiles unfortunately. eventual fix maybe.)
public class BuilderView implements BuilderUtility, BuilderAPI {

  private static final String BASE_RESOURCE_PACKAGE = "view.builder.";
  private static final String DEFAULT_STYLESHEET = "/view/builder/builderDefaultStyle.css";
  private static final double PANE_WIDTH = 500;
  private static final double PANE_HEIGHT = 500;
  private static final double DEFAULT_IMAGE_WIDTH = 100;
  private static final double DEFAULT_IMAGE_HEIGHT = 100;
  private static final double SCENE_WIDTH = 700;
  private static final double SCENE_HEIGHT = 600;
  private static final Logger LOG = LogManager.getLogger(BuilderView.class);

  private final ResourceBundle builderResource;
  private final ResourceBundle menuBar1Resource;
  private final ResourceBundle sideBar1Resource;
  private final ResourceBundle tileMenuResource;
  private Pane myBoardPane;
  private final String defaultStylesheet;
  private Optional<String> myCurrentlyClickedTiletype;
  //todo: dependency injection
  private VBox myLeftSidebar;
  private PopupForm popupForm;
  private int myTileCount = 0;
  private int myImageCount = 0;
  private Optional<ViewTile> myCurrentTile;
  private final BoardInfo myBoardInfo;
  private final boolean myDraggableObjectsToggle = true;
  private boolean myDeleteToggle = false;
  private final Coordinate myBoardPaneStartingLocation;
  private final BuilderController myBuilderController = new BuilderController();


  public BuilderView() {
    builderResource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "EnglishBuilderText");
    menuBar1Resource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "MenuBar1");
    sideBar1Resource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "SideBar1");
    tileMenuResource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "TileMenu");
    defaultStylesheet = getClass().getResource(DEFAULT_STYLESHEET).toExternalForm();

    myCurrentlyClickedTiletype = Optional.empty();
    myCurrentTile = Optional.empty();
    myBoardInfo = new BoardInfo(builderResource);

    Scene scene = initScene();
    Stage primaryStage = new Stage();
    primaryStage.setScene(scene);
    primaryStage.setTitle(builderResource.getString("BuilderTitle"));
    primaryStage.show();
    System.out.println(
        myBoardPane.getBoundsInParent().getMinX() + " | " + myBoardPane.getBoundsInParent()
            .getMaxX());
    System.out.println(
        myBoardPane.getBoundsInParent().getMinY() + " | " + myBoardPane.getBoundsInParent()
            .getMaxY());
    myBoardPaneStartingLocation = new Coordinate(
        (int) myBoardPane.localToScene(myBoardPane.getBoundsInLocal()).getMinX(),
        (int) myBoardPane.localToScene(myBoardPane.getBoundsInLocal()).getMinY()
    );

    // Example of the popup form using the Tile object
    //popupForm = new PopupForm(Tile.class, builderResource);
    //popupForm.displayForm();
  }

  private Scene initScene() {
    Node topBar = createTopBar();
    Node centralContainer = createCentralContainer();
    VBox root = (VBox) makeVBox("RootContainer", topBar, centralContainer);

    Scene newScene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    newScene.getStylesheets().add(defaultStylesheet);
    return newScene;
  }

  private HBox createTopBar() {
    Node title = makeText("BuilderHeader", builderResource);
    HBox menuBar1 = (HBox) makeHBox("MenuBar1");
    addButtonsToPane(menuBar1, menuBar1Resource);

    return (HBox) makeHBox("TopBar", title, menuBar1);
  }

  private HBox createCentralContainer() {
    VBox sideBar1 = (VBox) makeVBox("SideBar1");
    addButtonsToPane(sideBar1, sideBar1Resource);
    myLeftSidebar = sideBar1;

    Node boardPane = makePane("BoardPane", PANE_WIDTH, PANE_HEIGHT);
    myBoardInfo.setBoardSize(new Dimension((int) PANE_WIDTH, (int) PANE_HEIGHT));
    myBoardPane = (Pane) boardPane;
    myBoardPane.setOnMouseClicked(e -> handleBoardClick(e));

    return (HBox) makeHBox("CentralContainer", sideBar1, boardPane);
  }

  private void addButtonsToPane(Pane pane, ResourceBundle resource) {
    Enumeration<String> enumeration = resource.getKeys();
    while (enumeration.hasMoreElements()) {
      String key = enumeration.nextElement();
      Node btn = makeButton(key, builderResource, e -> {
        try {
          BuilderView.this.getClass().getDeclaredMethod(resource.getString(key))
              .invoke(BuilderView.this);
        } catch (Exception ex) {
          ex.printStackTrace();
          throw new RuntimeException(ex);
        }
      });
      pane.getChildren().add(btn);
    }
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////

  private void test() {
    // nothing
  }

  @Override
  public Optional<ImmutableGameHolder> saveFile() {
    Optional<File> file = directoryGet(builderResource, "SaveGameTitle");
    if (file.isPresent()) {
      ImmutableGameHolder game = createGameHolder();
      String givenDirectory = file.get().getPath();
      // Send file to the controller to properly save.
      //Controller.save(ImmutableGameHolder);
      System.out.println(givenDirectory);
      return Optional.ofNullable(game);
    } else {
      // todo: replace with LOG
      System.out.println("Ruh-roh, can't save to a file that doesn't exist!");
      return Optional.empty();
    }
  }

  @Override
  public void loadFile() {
    Optional<File> file = directoryGet(builderResource, "LoadGameTitle");
    if (file.isPresent()){
      System.out.println("Given directory: " + file.get().getPath());
      myBuilderController.load(file.get().getPath());
    }
    else{
      // todo: display error
    }
  }

  private void tile() {
    myCurrentlyClickedTiletype = Optional.of("Test");
  }


  private void uploadImage() {
    Optional<File> file = fileLoad(builderResource, "UploadImageTitle");

    if (checkIfImage(file)) {
      System.out.println("Got an image from: " + file.get().getPath());
      ImageView ourImage = turnFileToImage(file.get(), DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT,
          new Coordinate(0, 0));
      initializeNode(ourImage, "Image" + myImageCount, e->handleImageClick(ourImage));
      myImageCount++;
    } else {
      // todo: make this use an error form.
      System.out.println("ERROR -- Got a non-image or nothing from file.");
    }
  }

  // todo: support different tile types.
  private void createTile(MouseEvent e) {
    BasicTile tile = myBuilderController.addTile(e);
    initializeNode(tile, "Tile" + myTileCount, tile_e -> handleTileClick(tile));
    myTileCount++;
    tile.setId("Tile" + myTileCount);
    myCurrentlyClickedTiletype = Optional.empty();
  }
  private void openTileMenu() {
    refreshButtonsOnPane(myLeftSidebar, tileMenuResource);
  }

  private void backToSidebarMenu() {
    refreshButtonsOnPane(myLeftSidebar, sideBar1Resource);
  }

  private void refreshButtonsOnPane(Pane pane, ResourceBundle resourceBundle) {
    pane.getChildren().clear();
    addButtonsToPane(pane, resourceBundle);
  }

  private void handleBoardClick(MouseEvent e) {
    System.out.println("hello, you clicked on x: " + e.getSceneX() + " and y: " + e.getSceneY());
    System.out.println("relative x: " + e.getX() + " and y: " + e.getY());

    if (myCurrentlyClickedTiletype.isPresent()) {
      createTile(e);
    }
  }

  private void handleTileClick(ViewTile tile) {
    if (myDeleteToggle) {
      deleteTile(tile);
    }
    if (myCurrentTile.isPresent()) {
      tile.setColor(Color.LIGHTGREEN);
      setNextTile(myCurrentTile.get(), tile);
      myCurrentTile.get().setColor(Color.LIGHTBLUE);
      myCurrentTile = Optional.empty();
      tile.setColor(Color.LIGHTBLUE);
    } else {
      myCurrentTile = Optional.ofNullable(tile);
      myCurrentTile.get().setColor(Color.BLUE);
    }
  }

  private void setNextTile(ViewTile origin, ViewTile desiredNext){
    myBuilderController.addNext(origin.getTileId(), desiredNext.getTileId());
    // Set guideline between current and next tile?
  }

  private void handleImageClick(Node node) {
    if (myDeleteToggle) {
      myImageCount = deleteNode(node, myImageCount);
    }
  }

  private void deleteTile(ViewTile tile) {
    myBuilderController.removeTile(tile.getTileId());
    myTileCount = deleteNode(tile.asNode(), myTileCount);
    myCurrentTile = Optional.empty();
  }

  private void deleteToggle() {
    myDeleteToggle = !myDeleteToggle;
  }

  private int deleteNode(Node node, int objCounter) {
    myBoardPane.getChildren().remove(node);
    myDeleteToggle = false;
    return objCounter--;
  }

  private void changeAppearanceOnClick(Node node){

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

  private ImmutableGameHolder createGameHolder() {
    GameHolder game = new GameHolder();
    game.setBoardInfo(new ImmutableBoardInfo(myBoardInfo));
    //game.setTileGraph(new ImmutableGraph(myGraph));
    return new ImmutableGameHolder(game);
  }

  private void initializeNode(Node node, String identifier, EventHandler<MouseEvent> mouseClickHandle){
    myBuilderController.createEventsForNode(node, mouseClickHandle, myBoardPaneStartingLocation);
    node.setId(identifier);
    node.getStyleClass().add("clickable");
    myBoardPane.getChildren().add(node);
  }
}
