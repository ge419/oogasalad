package oogasalad.view.tabexplorer.tabs.socialcenter;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import oogasalad.model.accesscontrol.authentication.AuthenticationHandler;
import oogasalad.model.accesscontrol.dao.GameDao;
import oogasalad.model.accesscontrol.dao.UserDao;
import oogasalad.model.accesscontrol.database.schema.GameSchema;
import oogasalad.model.accesscontrol.database.schema.UserSchema;
import oogasalad.util.PathFinder;
import oogasalad.view.tabexplorer.TabExplorer;
import oogasalad.view.tabexplorer.tabs.Tab;
import oogasalad.view.tabexplorer.tabs.TabFactory;

public class SocialCenterTab implements Tab {
  private final TabExplorer tabExplorer;
  private final AuthenticationHandler authHandler;
  private final UserDao userDao;
  private final GameDao gameDao;

  ListView<String> mostPlayedGames;
  private ObservableList<String> gameEntries;
  private List<String> allGames;
  //  private List<Map<String, String>> allGames;
  private TabFactory tabFactory;

  @Inject
  public SocialCenterTab(@Assisted TabExplorer tabExplorer, AuthenticationHandler authHandler,
      UserDao userDao, GameDao gameDao, TabFactory tabFactory){
    this.tabExplorer = tabExplorer;
    this.authHandler = authHandler;
    this.tabFactory = tabFactory;

    this.userDao = userDao;
    this.gameDao = gameDao;
  }


  @Override
  public void renderTabContent() {
    //    StackPane root = new StackPane();
    BorderPane root = new BorderPane();
    VBox vbox = new VBox();
    vbox.setPrefWidth(200);
    vbox.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    vbox.getChildren().add(new Label("Online Users"));

    VBox vbox2 = new VBox();
    vbox2.setPrefWidth(150);
    vbox2.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));


    mostPlayedGames = getMostPlayedGames();
    VBox container = new VBox(mostPlayedGames);

    container.setPadding(new Insets(0,50,0,50));

//    mostPlayedGames.prefWidthProperty().bind(root.widthProperty());
//    mostPlayedGames.setPrefWidth(200);
//    mostPlayedGames.setMaxWidth(200);
//    mostPlayedGames.minWidth(200);

    root.setCenter(mostPlayedGames);
//    root.setLeft(vbox);

    tabExplorer.setCurrentTab(root);

  }



  private ListView<String> getMostPlayedGames(){
    gameEntries = FXCollections.observableArrayList();
    allGames = gameDao.getAllGames(); // todo should prob return a List<String>, mostly so that i can have access to the gameID to then do things like clone
    //System.out.println("all game ID: "+ allGames);
    gameEntries.addAll(allGames);

    ListView<String> listView = new ListView();

    listView.setEditable(true);
//  WHERE DO I INSERT THIS?  listView.prefWidthProperty().bind(parentContainer.widthProperty());
    listView.setItems(gameEntries);

    listView.setCellFactory(gameListView -> new ListCell<String>() {
      @Override
      protected void updateItem(String gameEntryID, boolean empty) {
        super.updateItem(gameEntryID, empty);

        if (empty || gameEntryID == null) {
          setText(null);
          setGraphic(null);
          return;
        }

        setWrapText(true);
//        setPrefHeight(50);
        //System.out.println(gameEntryID+" is the gameID");

        Map<String, Object> gameEntryMetadata = gameDao.getGameData(gameEntryID);

        // Create a new GridPane for each game entry
        GridPane gridPane = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
//        col1.setMinWidth(100); // set the minimum width of the first column
//        col1.setMaxWidth(100); // set the maximum width of the first column
        col1.setHgrow(Priority.NEVER); // do not allow the first column to grow

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS); // allow the second column to grow to fill the remaining space

        gridPane.getColumnConstraints().addAll(col1, col2);
        gridPane.setHgap(5);
        gridPane.setVgap(5);


        // Populate the GridPane with the necessary information
        // (e.g. game image, title, author, description)
        ImageView imageView = new ImageView(new Image(PathFinder.getGameThumbnail(gameEntryID)));
        imageView.setFitWidth(75);
        imageView.setFitHeight(75);
        gridPane.add(imageView, 0, 0);

        HBox container = new HBox();
        Button cloneButton = new Button("Clone");
        cloneButton.setOnAction(e->{
          userDao.subscribeToGame(authHandler.getActiveUserID(), gameEntryID);
          renderTabContent();
        });

        List<String> userGames = (List<String>) userDao.getUserData(authHandler.getActiveUserID()).get(
            UserSchema.GAMES.getFieldName());
        if(userGames.contains(gameEntryID)){
          cloneButton.setDisable(true);
          cloneButton.setText("Cloned");
        }
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        String title = (String) gameEntryMetadata.get(GameSchema.TITLE.getFieldName());
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        container.getChildren().addAll(titleLabel, spacer, cloneButton);
        gridPane.add(container, 1, 0);


        String description = (String) gameEntryMetadata.get(GameSchema.DESCRIPTION.getFieldName());
        Label descriptionLabel = new Label(description);
        descriptionLabel.setWrapText(true);
//        descriptionLabel.setMaxWidth(100);
        descriptionLabel.setTextOverrun(OverrunStyle.ELLIPSIS);
        descriptionLabel.setEllipsisString("...");
        descriptionLabel.setPrefHeight(50);
        gridPane.add(descriptionLabel, 1, 1);


        String author = (String) gameEntryMetadata.get("author");
        String authorUserName = (String) userDao.getUserData(author).get(UserSchema.USERNAME.getFieldName());
        Label authorLabel = new Label("By " + authorUserName);
        authorLabel.setFont(Font.font("Arial", 14));
        gridPane.add(authorLabel, 1, 2);

        // for some reason makes gridpane bound to listview width
        // and also makes text not overflow requiring horizontal scrollbar to view
        gridPane.setPrefWidth(0);
        gridPane.setPadding(new Insets(0,30,0,30));
//        gridPane.setMaxHeight(150);

        this.setOnMouseClicked(event -> {
              int selectedIndex = getListView().getSelectionModel().getSelectedIndex();
              tabFactory.makeGameDetailsTab(tabExplorer).renderGameDetail(gameEntryID, selectedIndex+1);
            }
        );

        setGraphic(gridPane);
      }
    });

    return listView;
  }
}
