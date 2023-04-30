package oogasalad.view.tabexplorer.tabs;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import oogasalad.model.accesscontrol.authentication.AuthenticationHandler;
import oogasalad.model.accesscontrol.dao.GameDao;
import oogasalad.model.accesscontrol.dao.UserDao;
import oogasalad.model.accesscontrol.database.schema.UserSchema;
import oogasalad.view.tabexplorer.TabExplorer;
import oogasalad.view.tabexplorer.userpreferences.UserPreferences;

public class GameLauncherTab implements Tab {

  private final TabExplorer tabExplorer;
  private final AuthenticationHandler authHandler;
  private final UserDao userDao;
  private final GameDao gameDao;
  private ResourceBundle languageResourceBundle;
  private final UserPreferences userPref;
  private Text yourGames;
  private Text welcomeText;
  private Button createGameButton;
  private TilePane tilePane;
  private Region region;
  private HBox hbox;
  private VBox gameLauncher;

  @Inject
  public GameLauncherTab(
      @Assisted TabExplorer tabExplorer, AuthenticationHandler authHandler,
      UserDao userDao, GameDao gameDao, UserPreferences userPref,
      ResourceBundle languageResourceBundle) {
    this.tabExplorer = tabExplorer;
    this.authHandler = authHandler;
    this.userDao = userDao;
    this.gameDao = gameDao;
    this.userPref = userPref;
    this.languageResourceBundle = languageResourceBundle;
    userPref.addObserver(this::onLanguageChange);
  }

  private void onLanguageChange(String pathToLanguageBundle) {
    languageResourceBundle = ResourceBundle.getBundle(pathToLanguageBundle);
    initTextOnNodes(pathToLanguageBundle);
  }


  private void initNodes() {
    yourGames = new Text();
    yourGames.setFont(Font.font(20));

    welcomeText = new Text();
    welcomeText.setId("welcome-text");


    createGameButton = new Button();
    createGameButton.setOnAction(e -> {
      dialogHandle();
    }); // todo call GameBuilder

    tilePane = new TilePane();
    tilePane.setPadding(new Insets(10));
    tilePane.setHgap(5);
    tilePane.setVgap(5);
    tilePane.setPrefColumns(3);
    tilePane.setAlignment(Pos.CENTER);

    region = new Region();
    hbox = new HBox();
    hbox.getChildren().addAll(welcomeText, region, createGameButton);
    HBox.setHgrow(region, Priority.ALWAYS);

    gameLauncher = new VBox();
    gameLauncher.setSpacing(10);
    gameLauncher.getChildren().addAll(hbox, yourGames, tilePane);
    BorderPane.setMargin(gameLauncher, new Insets(30, 30, 0, 30));
  }

  private void initTextOnNodes(String path) {
    languageResourceBundle = ResourceBundle.getBundle(path);
    yourGames.setText(languageResourceBundle.getString("YourGames"));
    String username = (String) userDao.getUserData(authHandler.getActiveUserID())
        .get(UserSchema.USERNAME.getFieldName());
    welcomeText.setText(languageResourceBundle.getString("Welcome") + username);
    createGameButton.setText(languageResourceBundle.getString("CreateGameBtn"));
  }

  public void switchScene() {
    initNodes();
    initTextOnNodes(userPref.getPreferredLanguagePath());
    buildGameList();
    tabExplorer.setCurrentTab(gameLauncher);
  }

  private void buildGameList() {
    List<String> userGames = (List<String>) userDao.getUserData(authHandler.getActiveUserID())
        .get(UserSchema.GAMES.getFieldName());

    //System.out.println("active user: "+authHandler.getActiveUserID());
    userGames.forEach(gameID -> {
      Map<String, Object> gameMetaData = gameDao.getGameData(gameID);
      String name = (String) gameMetaData.get("title");
      InputStream stream = null;
      try {
        //todo: load img_path from DB
        String imageResourcePath = authHandler.getActiveUserID().equals("rcd") ? "rcd_old.gif"
            : "game_img.png"; // lol, fun easter egg ig?
        stream = new FileInputStream("src/main/resources/" + imageResourcePath);
        Image image = new Image(stream);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(75);
        imageView.setFitHeight(75);
        VBox gameBox = new VBox(imageView, new Label(name));
        gameBox.getStyleClass().add("game-box");
//        gameBox.setAlignment(Pos.CENTER);
//        gameBox.setSpacing(5);
        gameBox.setOnMouseClicked(e-> tabExplorer.launchGame(gameID));
//        gameBox.setOnMouseEntered(e->gameBox.setCursor(Cursor.HAND));
//        gameBox.setPrefSize(100, 100);
        tilePane.getChildren().add(gameBox);
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        if (stream != null) {
          try {
            stream.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    });
  }

  //  todo: delete dialogHandle after adding game builder
  private void dialogHandle() {
    Dialog<String[]> dialog = new Dialog<>();
    dialog.setTitle("Game Builder");

    ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

    TextField titleField = new TextField();
    titleField.setPromptText("Game Title");

    VBox vbox = new VBox(10, titleField);
    dialog.getDialogPane().setContent(vbox);

    dialog.setResultConverter(dialogButton -> {
      if (dialogButton == createButtonType) {
        return new String[]{titleField.getText()};
      }
      return null;
    });

// Show the dialog and wait for the user to close it.
    Optional<String[]> result = dialog.showAndWait();
    result.ifPresent(formData -> {
      String name = formData[0];

      Map<String, Object> game = new HashMap<>();
      game.put("title", name);

      String gameID = gameDao.createGame(authHandler.getActiveUserID());
      gameDao.updateGame(gameID, game);
      tabExplorer.launchGameBuilder(gameID);
    });
  }

  @Override
  public void renderTabContent() {
    switchScene();
  }
}
