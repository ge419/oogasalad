package oogasalad.view.tabexplorer.tabs;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
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
import oogasalad.model.accesscontrol.database.schema.GameSchema;
import oogasalad.model.accesscontrol.database.schema.UserSchema;
import oogasalad.util.AlertPopUp;
import oogasalad.util.PathFinder;
import oogasalad.view.tabexplorer.TabExplorer;
import oogasalad.view.tabexplorer.userpreferences.UserPreferences;

public class GameLauncherTab implements Tab {

  private final TabExplorer tabExplorer;
  private final AuthenticationHandler authHandler;
  private final UserDao userDao;
  private final GameDao gameDao;
  private final UserPreferences userPref;
  private ResourceBundle languageResourceBundle;
  private Text yourGames;
  private Text welcomeText;
  private Button createGameButton;
  private TilePane tilePane;
  private Region region;
  private HBox hbox;
  private VBox gameLauncher;
  private MenuItem editMenuItem;
  private MenuItem deleteMenuItem;
  private MenuItem unsubscribeMenuItem;

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
        ImageView imageView = new ImageView(new Image(PathFinder.getGameThumbnail(gameID)));
        imageView.setFitWidth(75);
        imageView.setFitHeight(75);

        editMenuItem = new MenuItem("edit");
        deleteMenuItem = new MenuItem("delete");
        unsubscribeMenuItem = new MenuItem("unsubscribe");
        MenuButton menuButton = new MenuButton(null, null, editMenuItem);
        menuButton.setPrefSize(3, 3);

        String author = (String) gameMetaData.get(GameSchema.AUTHOR.getFieldName());

        boolean isAuthorOfGame = authHandler.getActiveUserID().equals(author);

        menuButton.getItems().add(isAuthorOfGame ? deleteMenuItem : unsubscribeMenuItem);

        deleteMenuItem.setOnAction(e -> {
          userDao.deleteGame(gameID);
          renderTabContent();
        });
        unsubscribeMenuItem.setOnAction(e -> {
          userDao.unsubscribeToGame(authHandler.getActiveUserID(), gameID);
          renderTabContent();
        });
        editMenuItem.setOnAction(e -> tabExplorer.launchGameBuilder(gameID));

        HBox container = new HBox(imageView, menuButton);
        VBox gameBox = new VBox(container, new Label(name));
        gameBox.getStyleClass().add("game-box");

        gameBox.setOnMouseClicked(e -> tabExplorer.launchGame(gameID));
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

      if (name.isBlank()) {
        AlertPopUp.show(AlertType.ERROR, "Input cannot be null", "Please add a name for your game");
      } else {
        Map<String, Object> game = new HashMap<>();
        game.put(GameSchema.TITLE.getFieldName(), name);

        String gameID = gameDao.createGame(authHandler.getActiveUserID());
        gameDao.updateGame(gameID, game);
        tabExplorer.launchGameBuilder(gameID);
        renderTabContent();
      }
    });
  }

  @Override
  public void renderTabContent() {
    switchScene();
  }
}
