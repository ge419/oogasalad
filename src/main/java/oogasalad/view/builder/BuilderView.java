package oogasalad.view.builder;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Optional;
import java.util.ResourceBundle;

public class BuilderView implements BuilderUtility {
    private static final String BASE_RESOURCE_PACKAGE = "view.builder.";
    private static final String DEFAULT_STYLESHEET = "/view/builder/builderDefaultStyle.css";
    private static final double PANE_WIDTH = 500;
    private static final double PANE_HEIGHT = 500;
    private static final double SCENE_WIDTH = 700;
    private static final double SCENE_HEIGHT = 600;

    private ResourceBundle builderResource;
    private ResourceBundle menuBar1Resource;
    private ResourceBundle sideBar1Resource;
    private Pane myBoardPane;
    private String defaultStylesheet;

    public BuilderView() {
        builderResource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "EnglishBuilderText");
        menuBar1Resource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "MenuBar1");
        sideBar1Resource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "SideBar1");
        defaultStylesheet = getClass().getResource(DEFAULT_STYLESHEET).toExternalForm();

        Scene scene = initScene();
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle(builderResource.getString("BuilderTitle"));
        primaryStage.show();
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

        Node boardPane = makePane("BoardPane", PANE_WIDTH, PANE_HEIGHT);
        myBoardPane = (Pane) boardPane;

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
    }

    private void uploadImage(){
        FileChooser chooseFile = new FileChooser();
        chooseFile.setTitle(builderResource.getString("UploadImageTitle"));
        Optional<File> file = Optional.ofNullable(chooseFile.showOpenDialog(null));

        if (checkIfImage(file) == true){
            System.out.println("Got something image from: " + file.get().toPath() );
            myBoardPane.getChildren().add(turnFileToImage(file.get(), PANE_WIDTH, PANE_HEIGHT));
        }
        else{
            System.out.println("Got a non-image or nothing from file.");
        }
    }

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
