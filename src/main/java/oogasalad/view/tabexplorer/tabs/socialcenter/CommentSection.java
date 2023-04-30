package oogasalad.view.tabexplorer.tabs.socialcenter;

import com.google.cloud.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import oogasalad.model.accesscontrol.authentication.AuthenticationHandler;
import oogasalad.model.accesscontrol.dao.GameDao;
import oogasalad.model.accesscontrol.dao.UserDao;
import oogasalad.model.accesscontrol.database.schema.GameSchema;
import oogasalad.model.accesscontrol.database.schema.UserSchema;
import oogasalad.util.PathFinder;

public class CommentSection extends VBox {

  private final AuthenticationHandler authHandler;
  private final UserDao userDao;
  private final GameDao gameDao;
  private ObservableList<Map<String, Object>> comments;
  private TextArea commentField;
  private Button submitButton;
  private ListView<Map<String, Object>> commentList;
  private final String gameID;
  private final String userID;
  private final GameDetailsTab gameDetailsTab;
  private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy hh:mm:ss a");

  public CommentSection(AuthenticationHandler authHandler,
      UserDao userDao, GameDao gameDao, String gameID, GameDetailsTab gameDetailsTab) {
    this.authHandler = authHandler;
    this.userDao = userDao;
    this.gameDao = gameDao;
    this.gameID = gameID;
    this.userID = authHandler.getActiveUserID();
    this.gameDetailsTab = gameDetailsTab;
    render();
  }

  public Node getCommentSection() {
    return this;
  }

  private void render() {

    Label reviewsText = new Label("Reviews");
    reviewsText.setStyle("-fx-font-weight: bold; -fx-font-size: 25;");

    commentField = new TextArea();
    commentField.setPrefHeight(200);
    commentField.setWrapText(true);
    submitButton = new Button("Submit");
    VBox addComment = new VBox(commentField, submitButton);
    commentList = new ListView<>();

    // Add a listener to the submit button
    submitButton.setOnAction(event -> addComment());

    comments = FXCollections.observableArrayList();
    // get all comments from DB
    List<Map<String, Object>> allComments = (List<Map<String, Object>>) gameDao.getGameData(gameID).get(
        GameSchema.REVIEWS.getFieldName()); // we want this reversed
    Collections.reverse(allComments);
    comments.addAll(allComments);
    commentList.setItems(comments);

    commentList.setCellFactory(listView -> new ListCell<>() {
      @Override
      protected void updateItem(Map<String, Object> commentEntry, boolean empty) {
        super.updateItem(commentEntry, empty);
        if (empty || commentEntry == null) {
          setText(null);
        } else {

          String commentorUserID = (String) commentEntry.get("author");
          Timestamp timestamp = (Timestamp) commentEntry.get("date_posted");
          String datePosted = dateFormat.format(timestamp.toDate());
          String review = (String) commentEntry.get("review");

          VBox reviewContainer = new VBox();

          // upper gridpane
          GridPane commentMetaDataBox = new GridPane();
          commentMetaDataBox.setHgap(10); // sets horizontal gap between columns
          commentMetaDataBox.setVgap(10); // sets vertical gap between rows

          ColumnConstraints col1 = new ColumnConstraints();
//          col1.setPercentWidth(20); // sets the width of the first column to 50% of the gridpane width
          col1.setHgrow(Priority.NEVER); // do not allow the first column to grow

          ColumnConstraints col2 = new ColumnConstraints();
//          col2.setPercentWidth(80); // sets the width of the second column to 50% of the gridpane width
          col2.setHgrow(Priority.ALWAYS); // do not allow the first column to grow

          RowConstraints row1 = new RowConstraints();
          row1.setPrefHeight(Region.USE_COMPUTED_SIZE);
          commentMetaDataBox.getRowConstraints().add(row1);

          commentMetaDataBox.getColumnConstraints()
              .addAll(col1, col2); // add the column constraints to the gridpane

          ImageView imageView = new ImageView(new Image(PathFinder.getUserAvatar(userID)));
          imageView.setFitWidth(50);
          imageView.setFitHeight(50);

          VBox container = new VBox();
          Label username = new Label((String) userDao.getUserData(commentorUserID)
              .get(UserSchema.USERNAME.getFieldName()));
          Label datePostedLabel = new Label(datePosted);

          container.getChildren().addAll(username, datePostedLabel);

          Button ellipsis = new Button("delete"); //todo should only show if user is author

          HBox userNameContainer = new HBox();
          Region spacer = new Region();
          HBox.setHgrow(spacer, Priority.ALWAYS);

//          userNameContainer.getChildren().addAll(container, ellipsis);

          userNameContainer.getChildren().addAll(container, spacer, ellipsis);

          commentMetaDataBox.add(imageView, 0, 0);
          commentMetaDataBox.add(userNameContainer, 1, 0);

          Label reviewLabel = new Label(review);

          commentMetaDataBox.add(reviewLabel, 1, 1);

          setGraphic(commentMetaDataBox);

        }
      }
    });

    // Add the components to the VBox
    getChildren().addAll(reviewsText, addComment, commentList);
  }

  private void addComment() {
    String comment = commentField.getText();
    // call DB to post comment
    gameDao.postGameReview(comment, gameID, userID);
    commentField.clear();
//    commentList.refresh();
    gameDetailsTab.renderTabContent();


  }

}
