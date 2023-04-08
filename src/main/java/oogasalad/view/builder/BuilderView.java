package oogasalad.view.builder;

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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import oogasalad.view.Coordinate;
import oogasalad.view.tiles.BasicTile;
import oogasalad.view.tiles.Tile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class BuilderView implements BuilderUtility, BuilderAPI {
    private static final String BASE_RESOURCE_PACKAGE = "view.builder.";
    private static final String DEFAULT_STYLESHEET = "/view/builder/builderDefaultStyle.css";
    private static final double PANE_WIDTH = 500;
    private static final double PANE_HEIGHT = 500;
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
    private GraphInterface myGraph;
    private VBox myLeftSidebar;
    private PopupForm popupForm;
    private int myTileCount = 0;
    private Optional<Tile> myCurrentTile;

    public BuilderView() {
        builderResource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "EnglishBuilderText");
        menuBar1Resource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "MenuBar1");
        sideBar1Resource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "SideBar1");
        tileMenuResource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "TileMenu");
        defaultStylesheet = getClass().getResource(DEFAULT_STYLESHEET).toExternalForm();

        myCurrentlyClickedTiletype = Optional.empty();
        myGraph = new Graph();  // todo: dependency injection
        myCurrentTile = Optional.empty();

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
        myBoardPane = (Pane) boardPane;
        myBoardPane.setOnMouseClicked(e -> createTile(e));

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
    private void test() {
        // temp
    }

    private void tile(){
        myCurrentlyClickedTiletype = Optional.of("Test");
    }


    private void uploadImage(){
        Optional<File> file = fileLoad(builderResource, "UploadImageTitle");

        if (checkIfImage(file) == true){
            System.out.println("Got an image from: " + file.get().toPath() );
            myBoardPane.getChildren().add(turnFileToImage(file.get(), PANE_WIDTH, PANE_HEIGHT));
        }
        else{
            // todo: make this use an error form.
            System.out.println("ERROR -- Got a non-image or nothing from file.");
        }
    }

    private void openTileMenu(){
        myLeftSidebar.getChildren().clear();

        addButtonsToPane(myLeftSidebar, tileMenuResource);
//        printGraph();
    }

    private void backLeftSide(){
        myLeftSidebar.getChildren().clear();
        addButtonsToPane(myLeftSidebar, sideBar1Resource);
    }

    // todo: support different tile types.
    private void createTile(MouseEvent e){
        System.out.println("hello, you clicked on x: " + e.getSceneX() + " and y: " + e.getSceneY());
        if (myCurrentlyClickedTiletype.isPresent()){
            Coordinate tileCoord = new Coordinate((int)e.getX(), (int)e.getY());
            double[] pos = {tileCoord.getXCoor(), tileCoord.getYCoor()};
            //BasicTile tile = new BasicTile(myTileCount, pos, new int[0], new int[0], new int[0]);
            BasicTile tile = new BasicTile(myTileCount, tileCoord);
            //tile.setOnMouseClicked(tile_e->{myBoardPane.getChildren().remove(tile);});
            tile.setOnMouseClicked(tile_e->{
//                popupForm = new PopupForm(BasicTile.class, builderResource);
//                popupForm.displayForm();
                handleTileClick(tile);
            });
            //tile.setOnDragDone(tile_e_two-> {tile.setPosition(new Coordinate((int) tile_e_two.getX(), (int) tile_e_two.getY()));});
            myTileCount++;
            myBoardPane.getChildren().add(tile);
            myGraph.addTile(tile);
            myCurrentlyClickedTiletype = Optional.empty();
        }
    }

    @Override
    public void saveFile() {
        Optional<File> file = fileSave(builderResource, "SaveGameTitle");
        if (file.isPresent()){
            //DataStorer currentData = new DataStorer(myGraph);
            // Send file to the controller to properly save.
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

    private void handleTileClick(Tile tile){
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

    private void deleteTile(){
        if (myCurrentTile.isPresent()){
            myBoardPane.getChildren().remove(myCurrentTile.get());
            myCurrentTile = Optional.empty();
        }
        else{
            // todo: log that we tried to delete a non-existing tile.
            System.out.println("No tile selected to delete-- click the tile first, then delete!");
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    private boolean checkIfImage(Optional<File> thing){
        final String IMAGE_FILE_SUFFIXES = String.format(".*\\.(%s)", String.join("|", ImageIO.getReaderFileSuffixes()));
        return thing.isPresent() && thing.get().getName().matches(IMAGE_FILE_SUFFIXES);
    }

    private ImageView turnFileToImage(File file, double width, double height){
        return new ImageView(new Image(
                file.toURI().toString(),
                width,
                height,
                true,
                true)
        );
    }
}
