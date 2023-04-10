package oogasalad.view.builder;

import java.awt.Dimension;
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
import java.io.File;
import java.util.Enumeration;
import java.util.Optional;
import java.util.ResourceBundle;
import oogasalad.view.Coordinate;
import oogasalad.view.builder.board.BoardInfo;
import oogasalad.view.builder.board.NodeStorer;
import oogasalad.view.builder.board.ImmutableBoardInfo;
import oogasalad.view.builder.gameholder.GameHolder;
import oogasalad.view.builder.gameholder.ImmutableGameHolder;
import oogasalad.view.builder.graphs.Graph;
import oogasalad.view.builder.graphs.ImmutableGraph;
import oogasalad.view.builder.panefeatures.Dragger;
import oogasalad.view.tiles.BasicTile;
import oogasalad.view.tiles.Tile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


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
    private ResourceBundle menuBar1Resource;
    private ResourceBundle sideBar1Resource;
    private ResourceBundle tileMenuResource;
    private Pane myBoardPane;
    private String defaultStylesheet;
    private Optional<String> myCurrentlyClickedTiletype;
    //todo: dependency injection
    private Graph myGraph;
    private VBox myLeftSidebar;
    private PopupForm popupForm;
    private int myTileCount = 0;
    private int myImageCount = 0;
    private Optional<Tile> myCurrentTile;
    private BoardInfo myBoardInfo;
    private NodeStorer myNodeHolder;
    private boolean myDraggableObjectsToggle = true;
    private boolean myDeleteToggle = false;


    public BuilderView() {
        builderResource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "EnglishBuilderText");
        menuBar1Resource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "MenuBar1");
        sideBar1Resource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "SideBar1");
        tileMenuResource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "TileMenu");
        defaultStylesheet = getClass().getResource(DEFAULT_STYLESHEET).toExternalForm();

        myCurrentlyClickedTiletype = Optional.empty();
        myGraph = new Graph();  // todo: dependency injection
        myNodeHolder = new NodeStorer();
        myCurrentTile = Optional.empty();
        myBoardInfo = new BoardInfo(builderResource);

        Scene scene = initScene();
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle(builderResource.getString("BuilderTitle"));
        primaryStage.show();

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
        myBoardInfo.setBoardSize(new Dimension((int)PANE_WIDTH, (int)PANE_HEIGHT));
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
                    BuilderView.this.getClass().getDeclaredMethod(resource.getString(key)).invoke(BuilderView.this);
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
        // temp
    }

    @Override
    public void saveFile() {
        Optional<File> file = directoryGet(builderResource, "SaveGameTitle");
        if (file.isPresent()){
            ImmutableGameHolder game = createGameHolder();
            String givenDirectory = file.get().getPath();
            // Send file to the controller to properly save.
            //Controller.save(ImmutableGameHolder);
            System.out.println(givenDirectory);
        }
        else{
            // todo: replace with LOG
            System.out.println("Ruh-roh, can't save to a file that doesn't exist!");
        }
    }

    @Override
    public void loadFile() {
        myGraph.print();
        //Optional<File> file = fileLoad(builderResource, "LoadGameTitle");
    }

    private void tile(){
        myCurrentlyClickedTiletype = Optional.of("Test");
    }


    private void uploadImage(){
        Optional<File> file = fileLoad(builderResource, "UploadImageTitle");

        if (checkIfImage(file) == true){
            System.out.println("Got an image from: " + file.get().getPath() );
            ImageView ourImage = turnFileToImage(file.get(), DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT, new Coordinate(0,0));
            ourImage.setId("Image" + myImageCount);
            ourImage.setOnMouseClicked(e->handleImageClick(ourImage));
            createTileFeaturesForObject(ourImage);
            myNodeHolder.addNode(ourImage, file.get().getPath());
            myBoardPane.getChildren().add(ourImage);
            myImageCount++;
            // Add to board info at the end.
            //myBoardInfo.addImage(file.get().getPath(), new Coordinate(0,0), new Dimension((int) PANE_WIDTH, (int)PANE_HEIGHT));
        }
        else{
            // todo: make this use an error form.
            System.out.println("ERROR -- Got a non-image or nothing from file.");
        }
    }

    // todo: support different tile types.
    private void createTile(MouseEvent e){
        Coordinate tileCoord = new Coordinate((int)e.getX(), (int)e.getY());
        BasicTile tile = new BasicTile(myTileCount, tileCoord);
        //createTileFeaturesForObject(tile);
        tile.setOnMouseClicked(tile_e->{
//                popupForm = new PopupForm(BasicTile.class, builderResource);
//                popupForm.displayForm();
            handleTileClick(tile);
        });
        tile.setId("Tile" + myTileCount);
        myTileCount++;
        myBoardPane.getChildren().add(tile);
        myGraph.addTile(tile);
        myCurrentlyClickedTiletype = Optional.empty();
    }

    private void openTileMenu(){
        addNewButtonsToPane(myLeftSidebar, tileMenuResource);
    }

    private void backLeftSide(){
        addNewButtonsToPane(myLeftSidebar, sideBar1Resource);
    }

    private void addNewButtonsToPane(Pane pane, ResourceBundle resourceBundle){
        pane.getChildren().clear();
        addButtonsToPane(pane, resourceBundle);
    }

    private void handleBoardClick(MouseEvent e){
        System.out.println("hello, you clicked on x: " + e.getSceneX() + " and y: " + e.getSceneY());
        System.out.println("relative x: " + e.getX() + " and y: " + e.getY());
        if (myCurrentlyClickedTiletype.isPresent()){
            createTile(e);
        }
    }

    private void handleTileClick(Tile tile){
        if (myDeleteToggle){
            deleteTile(tile);
        }
        if (myCurrentTile.isPresent()){
            tile.setColor(Color.LIGHTGREEN);
            myGraph.addTileNext(myCurrentTile.get(), tile);
            myCurrentTile.get().setColor(Color.LIGHTBLUE);
            myCurrentTile = Optional.empty();
            tile.setColor(Color.LIGHTBLUE);
        }
        else{
            myCurrentTile = Optional.ofNullable(tile);
            myCurrentTile.get().setColor(Color.BLUE);
        }
    }

    private void handleImageClick(Node node){
        if (myDeleteToggle){
            deleteNode(node, myImageCount);
            return;
        }
    }

    private void deleteTile(Tile tile){
        if (myDeleteToggle){
            myBoardPane.getChildren().remove(tile);
            myCurrentTile = Optional.empty();
            myGraph.removeTile(tile);
            myTileCount--;
            myDeleteToggle = false;
        }
        else{
            // todo: log that we tried to delete a non-existing tile.
            System.out.println("Not in delete mode; how were you trying to delete??");
        }
    }

    private void deleteToggle(){
        myDeleteToggle = !myDeleteToggle;
    }

    private int deleteNode(Node node, int objCounter){
        if (myDeleteToggle){
            myBoardPane.getChildren().remove(node);
            myDeleteToggle = false;
            return objCounter--;
        }
        else{
            // todo: log that we somehow tried to delete without being in delete mode.
            System.out.println("Not in delete mode... how were you trying to delete??");
            return objCounter;
        }
    }


    // -----------------------------------------------------------------------------------------------------------------
    private boolean checkIfImage(Optional<File> thing){
        final String IMAGE_FILE_SUFFIXES = String.format(".*\\.(%s)", String.join("|", ImageIO.getReaderFileSuffixes()));
        return thing.isPresent() && thing.get().getName().matches(IMAGE_FILE_SUFFIXES);
    }

    private ImageView turnFileToImage(File file, double width, double height, Coordinate location){
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

    private ImmutableGameHolder createGameHolder(){
        GameHolder game = new GameHolder();
        game.setBoardInfo(new ImmutableBoardInfo(myBoardInfo));
        game.setTileGraph(new ImmutableGraph(myGraph));
        return new ImmutableGameHolder(game);
    }

    private void createTileFeaturesForObject(Node node){
        Dragger nodeDragger = new Dragger(node, myDraggableObjectsToggle);
        myNodeHolder.addDragger(nodeDragger);
    }
}
