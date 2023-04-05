package oogasalad.view.builder;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Enumeration;
import java.util.ResourceBundle;

public class BuilderView implements BuilderUtility {
    private static final String BASE_RESOURCE_PACKAGE = "view.builder.";
    private static final double PANE_WIDTH = 500;
    private static final double PANE_HEIGHT = 500;
    private static final double SCENE_WIDTH = 800;
    private static final double SCENE_HEIGHT = 800;

    private ResourceBundle builderResource;
    private ResourceBundle menuBar1Resource;
    private ResourceBundle sideBar1Resource;

    public BuilderView() {
        builderResource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "EnglishBuilderText");
        menuBar1Resource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "MenuBar1");
        sideBar1Resource = ResourceBundle.getBundle(BASE_RESOURCE_PACKAGE + "SideBar1");

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
    
}
