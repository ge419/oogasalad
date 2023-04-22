package oogasalad.view.builder;

import java.awt.Dimension;
import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
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
import oogasalad.model.attribute.SchemaDatabase;
import oogasalad.model.constructable.Tile;
import oogasalad.view.Coordinate;
import oogasalad.view.builder.board.BoardInfo;
import oogasalad.view.builder.board.ImmutableBoardInfo;
import oogasalad.view.builder.events.TileEvent;
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

  private ResourceBundle builderResource;
  private final ResourceBundle topBarResource;
  private final ResourceBundle sideBar1Resource;
  private final ResourceBundle tileMenuResource;
  private final ResourceBundle fileMenuResource;
  private final ResourceBundle aboutMenuResource;
  private final ResourceBundle appearanceMenuResource;
  private final ResourceBundle settingsMenuResource;
  private final ResourceBundle toggleMenuResource;
  private Pane myBoardPane;
  private final String defaultStylesheet;
  private Optional<String> myCurrentlyClickedTiletype;
  //todo: dependency injection
  private VBox myLeftSidebar;
  private Node myInfoText;
  private HBox myInfoTextBox;
  private BorderPane myTopBar;
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
  private VBox sidePane;


  public BuilderView(BuilderController bc) {
    this.bc = bc;
    // todo: clean this up. instance blocks maybe?
    builderResource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "EnglishBuilderText");
    topBarResource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "TopBar");
    sideBar1Resource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "SideBar1");
    tileMenuResource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "TileMenu");
    fileMenuResource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "FileMenu");
    appearanceMenuResource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "AppearanceMenu");
    aboutMenuResource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "AboutMenu");
    settingsMenuResource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "SettingsMenu");
    toggleMenuResource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "ToggleMenu");

    defaultStylesheet = getClass().getResource(DEFAULT_STYLESHEET).toExternalForm();

    myCurrentlyClickedTiletype = Optional.empty();
    myCurrentTile = Optional.empty();
    myBoardInfo = new BoardInfo(builderResource);

    Scene scene = initScene();
    myTrailMaker = new TrailMaker(myBoardPane, true);
    Stage primaryStage = new Stage();
    primaryStage.setScene(scene);
    primaryStage.setTitle(builderResource.getString("BuilderTitle"));
    primaryStage.show();
//    System.out.println(
//        myBoardPane.getBoundsInParent().getMinX() + " | " + myBoardPane.getBoundsInParent()
//            .getMaxX());
//    System.out.println(
//        myBoardPane.getBoundsInParent().getMinY() + " | " + myBoardPane.getBoundsInParent()
//            .getMaxY());
    myBoardPaneStartingLocation = new Coordinate(
        (double) myBoardPane.localToScene(myBoardPane.getBoundsInLocal()).getMinX(),
        (double) myBoardPane.localToScene(myBoardPane.getBoundsInLocal()).getMinY(),
        0
    );

  }

  private Scene initScene() {
    VBox topMenu = createMenus();
    BorderPane topBar = createTopBar();
    Node centralContainer = createCentralContainer();
    VBox root = (VBox) makeVBox("RootContainer", topMenu, topBar, centralContainer);

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
//    HBox textBox = (HBox) makeHBox("TextBox", text);
//    myInfoTextBox = textBox;
//    CheckBox checker = (CheckBox) makeCheckBox("GuidelinesToggle", builderResource);
//    checker.setOnAction(e -> handleGuidelineClick());
//    myGuidelinesToggle = checker;
    HBox buttonBox = (HBox) makeHBox("TopBar");
    addButtonsToPane(buttonBox, topBarResource);

    topBar.setLeft(title);
    //topBar.setCenter(myInfoTextBox);
    topBar.setCenter(myInfoText);
    topBar.setRight(buttonBox);
    topBar.setId("TopBar");
    topBar.getStyleClass().add("topBar");
    // (HBox) makeHBox("TopBar", title, textBox, menuBar1);

    return topBar;
  }

  private HBox createCentralContainer() {
    VBox sideBar1 = (VBox) makeVBox("SideBar1");
    addButtonsToPane(sideBar1, sideBar1Resource);
    myLeftSidebar = sideBar1;

    Node boardPane = makePane("BoardPane", PANE_WIDTH, PANE_HEIGHT);
    myBoardInfo.setBoardSize(new Dimension((int) PANE_WIDTH, (int) PANE_HEIGHT));
    myBoardPane = (Pane) boardPane;
    myBoardPane.setOnMouseClicked(e -> handleBoardClick(e));
    myBoardPane.addEventFilter(TileEvent.DELETE_TILE, e -> deleteTile(e));
    myBoardPane.addEventFilter(TileEvent.SET_NEXT_TILE, e -> createTilePath(e));
    myBoardPane.addEventFilter(TileEvent.SHOW_FORM, e -> displayTileForm(e));
    myBoardPane.addEventFilter(TileEvent.SELECT_TILE, e -> selectTile(e));

    sidePane = new VBox();
    sidePane.setPrefWidth(300);

    return (HBox) makeHBox("CentralContainer", sideBar1, boardPane, sidePane);
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

  private VBox createMenus(){
    MenuBar topMenu = new MenuBar();
    Map<String, ResourceBundle> bundleList = Map.of(
        "FileMenu", fileMenuResource,
        "ToggleMenu", toggleMenuResource,
        "AppearanceMenu", appearanceMenuResource,
        "SettingsMenu", settingsMenuResource,
        "AboutMenu", aboutMenuResource
    );
    for (String menuLabel : bundleList.keySet()){
      Menu menuType = new Menu(builderResource.getString(menuLabel));
      menuType.setId(menuLabel);
      addMenuItemsToMenu(menuType, bundleList.get(menuLabel));
      topMenu.getMenus().add(menuType);
    }

    return (VBox) makeVBox("menuBar", topMenu);
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
    System.out.println("why that button sure did do nothing!");
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

  private void handleGuidelineClick(){
    myTrailMaker.toggleEnable();
  }
  private void handleDraggableClick(){
    // todo
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
    //myTopBar.getChildren().remove(myInfoText);
    //System.out.println("did thing");
    myInfoText = makeText(key, builderResource);
    myTopBar.setCenter(myInfoText);
    //myTopBar.getChildren().add(myInfoText);
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
