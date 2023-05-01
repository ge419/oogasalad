package oogasalad.view.tabexplorer.tabs.socialcenter;

import com.google.cloud.Timestamp;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.text.SimpleDateFormat;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
import oogasalad.model.accesscontrol.authentication.AuthenticationHandler;
import oogasalad.model.accesscontrol.dao.GameDao;
import oogasalad.model.accesscontrol.dao.UserDao;
import oogasalad.util.PathFinder;
import oogasalad.view.tabexplorer.TabExplorer;
import oogasalad.view.tabexplorer.tabs.Tab;
import oogasalad.view.tabexplorer.tabs.TabFactory;

public class GameDetailsTab implements Tab {

  private final TabExplorer tabExplorer;
  private final AuthenticationHandler authHandler;
  private final UserDao userDao;
  private final GameDao gameDao;
  private Map<String, Object> gameEntryMetadata;
  private String gameID;
  private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
  private int popularityIndex;
  private final TabFactory tabFactory;

  @Inject
  public GameDetailsTab(@Assisted TabExplorer tabExplorer, AuthenticationHandler authHandler,
      UserDao userDao, GameDao gameDao, TabFactory tabFactory) {
    this.tabExplorer = tabExplorer;
    this.authHandler = authHandler;
    this.userDao = userDao;
    this.gameDao = gameDao;
    this.tabFactory = tabFactory;
  }

  @Override
  public void renderTabContent() {

    // move to instance variables
    String title = (String) gameEntryMetadata.get("title");
    String description = (String) gameEntryMetadata.get("description");
    String author = (String) gameEntryMetadata.get("author");
    String genre = (String) gameEntryMetadata.get("genre");
    Timestamp timestamp = (Timestamp) gameEntryMetadata.get("date_created");
    String date_created = dateFormat.format(timestamp.toDate());
//    String date_created = (String) gameEntryMetadata.get("date_created"); //todo should prob return error

    VBox mainLayout = new VBox();

    // First section: game ranking
    HBox gameRankingSection = new HBox();
//    gameRankingSection.setBorder(new Border(
//        new BorderStroke(Color.CYAN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
//            BorderWidths.DEFAULT)));
    Label gameRankingLabel = new Label("#" + popularityIndex + " most played game");
    gameRankingLabel.setFont(Font.font(15));
// Set the height of the label to 10% of the parent node
    gameRankingLabel.prefHeightProperty().bind(mainLayout.heightProperty().multiply(0.075));
    gameRankingSection.getChildren().add(gameRankingLabel);

    // second section
    GridPane gridPane = new GridPane();
    gridPane.setHgap(20);
    gridPane.setVgap(20);
    gridPane.prefHeightProperty().bind(mainLayout.heightProperty().multiply(0.25));
//    gridPane.setPadding(new Insets(10));
    ColumnConstraints column1 = new ColumnConstraints();
    column1.setHgrow(Priority.ALWAYS);
    column1.setPercentWidth(60);
    ColumnConstraints column2 = new ColumnConstraints();
    column2.setHgrow(Priority.ALWAYS);
    column2.setPercentWidth(40);
    gridPane.getColumnConstraints().addAll(column1, column2);

    // Column 1: Game Name and Description
    VBox gameInfo = new VBox();

    HBox container = new HBox();
    container.setPadding(new Insets(0, 0, 25, 0));
    Label gameName = new Label(title);
    gameName.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
    Button clone = new Button("clone");
    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    container.getChildren().addAll(gameName, spacer, clone);
    Label descriptionText = new Label("Description");
    descriptionText.setStyle("-fx-font-weight: bold; -fx-font-size: 13;");
    Label gameDescription = new Label(description);
    gameDescription.setWrapText(true);
    gameInfo.getChildren().addAll(new VBox(descriptionText, gameDescription));
    gridPane.add(gameInfo, 0, 0);

    // Column 2: Game Thumbnail, Author, Date Posted, and Genre
    VBox gameDetails = new VBox();
    ImageView thumbnail = new ImageView(new Image(PathFinder.getGameThumbnail(gameID)));
    thumbnail.setFitHeight(175);
    thumbnail.setFitWidth(175);
    VBox labels = new VBox();
    Label authorLabel = new Label("Author: " + author);
    Label dateLabel = new Label("Date Created: " + date_created);
    Label genreLabel = new Label("Genre: " + genre);
    labels.getChildren().addAll(authorLabel, dateLabel, genreLabel);
    gameDetails.setAlignment(Pos.CENTER);
    gameDetails.getChildren()
        .addAll(thumbnail, labels);
    gridPane.add(gameDetails, 1, 0);

//    // Third section: comment section
//    VBox commentSection = new VBox();
//    commentSection.prefHeightProperty().bind(mainLayout.heightProperty().multiply(0.2));
//    Label comment1 = new Label("This is comment 1");
//    Label comment2 = new Label("This is comment 2");
//    Label comment3 = new Label("This is comment 3");
//// Add comments and reviews to the comment section (assume they are called comment1, comment2, etc.)
//    commentSection.getChildren().addAll(comment1, comment2, comment3);

    CommentSection commentSection = new CommentSection(authHandler, userDao, gameDao, gameID, this);
    Region hey = (Region) commentSection.getCommentSection();
    hey.prefHeightProperty().bind(mainLayout.heightProperty().multiply(0.675));

// Add the three sections to the main layout
    mainLayout.getChildren().addAll(gameRankingSection, container, gridPane, hey);

// Wrap the comment section in a ScrollPane to allow for scrolling if there are too many comments
    ScrollPane scrollPane = new ScrollPane(mainLayout);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);

// Add the ScrollPane to the main layout
//    mainLayout.getChildren().add(scrollPane);

//    StackPane root = new StackPane();
    BorderPane root = new BorderPane();

    VBox vbox = new VBox();
    vbox.setPrefWidth(150);
//    vbox.setBorder(new Border(
//        new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
//            BorderWidths.DEFAULT)));
    vbox.getChildren().add(new Label("LeaderBoard"));

    VBox vbox2 = new VBox();

    vbox2.setPrefWidth(90);
    vbox2.setBorder(new Border(
        new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
            BorderWidths.DEFAULT)));

    root.setCenter(scrollPane);
//    root.setTop(new Label("This is top"));
//    root.setBottom(new Label("This is bottom"));
//    root.setLeft(new Label("This is left"));
    root.setRight(vbox);

    tabExplorer.setCurrentTab(root);
  }

  public void renderGameDetail(String gameID, int popularityIndex) {
    this.gameID = gameID;
    this.popularityIndex = popularityIndex;
    gameEntryMetadata = gameDao.getGameData(gameID);
    renderTabContent();
  }
}
