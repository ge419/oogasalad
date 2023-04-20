package oogasalad.view.builder;

import java.awt.Dimension;
import java.io.File;
import java.util.Enumeration;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert;
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
import oogasalad.controller.builderevents.TrailMaker;
import oogasalad.controller.builderevents.TrailMakerAPI;
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.view.Coordinate;
import oogasalad.view.builder.board.BoardInfo;
import oogasalad.view.builder.board.ImmutableBoardInfo;
import oogasalad.view.builder.gameholder.GameHolder;
import oogasalad.view.builder.gameholder.ImmutableGameHolder;
import oogasalad.view.builder.popupform.PopupForm;
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
  private Node myInfoText;
  private HBox myInfoTextBox;
  private CheckBox myGuidelinesToggle;
  private PopupForm popupForm;
  private int myTileCount = 0;
  private int myImageCount = 0;
  private Optional<ViewTile> myCurrentTile;
  private final BoardInfo myBoardInfo;
  private TrailMakerAPI myTrailMaker;
  private final boolean myDraggableObjectsToggle = true;
  private boolean myDeleteToggle = false;
  private final Coordinate myBoardPaneStartingLocation;
  private final BuilderController bc;
  private final SchemaDatabase schemas;


  public BuilderView(BuilderController bc) {
    this.bc = bc;
    builderResource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "EnglishBuilderText");
    menuBar1Resource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "MenuBar1");
    sideBar1Resource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "SideBar1");
    tileMenuResource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "TileMenu");
    defaultStylesheet = getClass().getResource(DEFAULT_STYLESHEET).toExternalForm();
    schemas = new SchemaDatabase();

    myCurrentlyClickedTiletype = Optional.empty();
    myCurrentTile = Optional.empty();
    myBoardInfo = new BoardInfo(builderResource);

    Scene scene = initScene();
    myTrailMaker = new TrailMaker(myBoardPane, false);
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
        (double) myBoardPane.localToScene(myBoardPane.getBoundsInLocal()).getMinX(),
        (double) myBoardPane.localToScene(myBoardPane.getBoundsInLocal()).getMinY(),
        0
    );

    // Example of the popup form using the Tile object
    //popupForm = new PopupForm(Tile.class, builderResource);
    //popupForm.displayForm();
  }

  private Scene initScene() {
    VBox topMenu = createTopMenu();
    HBox topBar = createTopBar();
    Node centralContainer = createCentralContainer();
    VBox root = (VBox) makeVBox("RootContainer", topMenu, topBar, centralContainer);

    Scene newScene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    newScene.getStylesheets().add(defaultStylesheet);
    return newScene;
  }

  private HBox createTopBar() {
    Node title = makeText("BuilderHeader", builderResource);
    Node text = makeText("RegularMode", builderResource);
    myInfoText = text;
    HBox textBox = (HBox) makeHBox("TopBar", text);
    myInfoTextBox = textBox;
    CheckBox checker = (CheckBox) makeCheckBox("GuidelinesToggle", builderResource);
    checker.setOnAction(e -> handleGuidelineClick());
    myGuidelinesToggle = checker;
    HBox menuBar1 = (HBox) makeHBox("MenuBar1", checker);
    addButtonsToPane(menuBar1, menuBar1Resource);

    return (HBox) makeHBox("TopBar", title, textBox, menuBar1);
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

  // todo: this is VERY similar to addButtonsToPane... any way to consolidate?
  private void addMenuItemsToMenu(Menu menu, ResourceBundle resource){
    Enumeration<String> enumeration = resource.getKeys();
    while (enumeration.hasMoreElements()){
      String key = enumeration.nextElement();
      MenuItem item = makeMenuItem(key, builderResource, e-> {
        try{
          BuilderView.this.getClass().getDeclaredMethod(resource.getString(key))
              .invoke(BuilderView.this);
        } catch (Exception ex) {
          ex.printStackTrace();
          throw new RuntimeException(ex);
        }
      });
      menu.getItems().add(item);
    }
  }

  private void createMenus(){

  }

  private VBox createTopMenu(){
    MenuBar topMenu = new MenuBar();
    MenuItem item1 = new MenuItem(builderResource.getString("Save"));
    MenuItem item2 = new MenuItem(builderResource.getString("Load"));
    Menu menuLabel = new Menu("File");
    menuLabel.getItems().add(item1);
    menuLabel.getItems().add(item2);
    topMenu.getMenus().add(menuLabel);
    return new VBox(topMenu);

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
      new Alert(Alert.AlertType.ERROR, builderResource.getString("FileNotFoundError"));
      return Optional.empty();
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

  private void tile() {
    myCurrentlyClickedTiletype = Optional.of("Test");
    updateInfoText("TileAdditionMode");
  }


  private void uploadImage() {
    Optional<File> file = fileLoad(builderResource, "UploadImageTitle");

    if (checkIfImage(file)) {
      System.out.println("Got an image from: " + file.get().getPath());
      ImageView ourImage = turnFileToImage(file.get(), DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT,
          new Coordinate(0, 0, 0));
      initializeNode(ourImage, "Image" + myImageCount, e->handleImageClick(ourImage));
      myImageCount++;
    } else {
      // todo: make this use an error form.
      System.out.println("ERROR -- Got a non-image or nothing from file.");
      new Alert(Alert.AlertType.ERROR, builderResource.getString("EmptyFileError"));
    }
  }

  // todo: support different tile types.
  private void createTile(MouseEvent e) {
    BasicTile tile = bc.addTile(e);
    initializeNode(tile, "Tile" + myTileCount, tile_e -> handleTileClick(tile));
    myTileCount++;
    myCurrentlyClickedTiletype = Optional.empty();
    updateInfoText("RegularMode");
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
      return;
    }
    if (myCurrentTile.isPresent()) {
      tile.setColor(Color.LIGHTGREEN);
      setNextTile(myCurrentTile.get(), tile);
      myCurrentTile.get().setColor(Color.LIGHTBLUE);
      myCurrentTile = Optional.empty();
      tile.setColor(Color.LIGHTBLUE);
      new PopupForm(tile.getTile(), builderResource).displayForm();
      updateInfoText("RegularMode");
    } else {
      myCurrentTile = Optional.ofNullable(tile);
      myCurrentTile.get().setColor(Color.BLUE);
      updateInfoText("TileNextMode");
    }

    // TODO: Graph is no longer used
//    if (myCurrentTile.isPresent()) {
//      tile.setColor(Color.LIGHTGREEN);
//      myGraph.addTileNext(myCurrentTile.get(), tile);
//      myCurrentTile.get().setColor(Color.LIGHTBLUE);
//      myCurrentTile = Optional.empty();
//      tile.setColor(Color.LIGHTBLUE);
//    } else {
//      myCurrentTile = Optional.ofNullable(tile);
//      myCurrentTile.get().setColor(Color.BLUE);
//    }
  }

  private void handleGuidelineClick(){
    myTrailMaker.toggleEnable();
  }

  private void setNextTile(ViewTile origin, ViewTile desiredNext){
    bc.addNext(origin.getTileId(), desiredNext.getTileId());
    myTrailMaker.createTrailBetween(desiredNext.asNode(), origin.asNode(), "test" + myTileCount);
    // Set guideline between current and next tile?
  }

  private void handleImageClick(Node node) {
    if (myDeleteToggle) {
      myImageCount = deleteNode(node, myImageCount);
    }
  }

  private void deleteTile(ViewTile tile) {
    bc.removeTile(tile.getTileId());
    myTrailMaker.removeTrail(tile.asNode());
    myTileCount = deleteNode(tile.asNode(), myTileCount);
    myCurrentTile = Optional.empty();
  }

  private void deleteToggle() {
    myDeleteToggle = !myDeleteToggle;
    if (myDeleteToggle){
      updateInfoText("DeleteMode");
    }
    else{
      updateInfoText("RegularMode");
    }
  }

  private int deleteNode(Node node, int objCounter) {
    myBoardPane.getChildren().remove(node);
    deleteToggle();
    return objCounter--;
  }

  private void updateInfoText(String key){
    myInfoTextBox.getChildren().remove(myInfoText);
    myInfoText = makeText(key, builderResource);
    myInfoTextBox.getChildren().add(myInfoText);
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
    bc.createEventsForNode(node, mouseClickHandle, myBoardPaneStartingLocation);
    node.setId(identifier);
    node.getStyleClass().add("clickable");
    myBoardPane.getChildren().add(node);
  }
}
