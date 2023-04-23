package oogasalad.view.tabexplorer.tabs;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import oogasalad.model.accesscontrol.authentication.AuthenticationHandler;
import oogasalad.model.accesscontrol.database.DatabaseAccessor;
import oogasalad.view.tabexplorer.TabExplorer;

public class GameLauncherTab implements Tab {

  private final TabExplorer tabExplorer;
  private final AuthenticationHandler authHandler;
  private final DatabaseAccessor db;

  @Inject
  public GameLauncherTab(
      @Assisted TabExplorer tabExplorer, DatabaseAccessor db, AuthenticationHandler authHandler) {
    this.tabExplorer = tabExplorer;
    this.db = db;
    this.authHandler = authHandler;
  }

  //todo refactor method, too long
  public void switchScene() {
    Text yourGames = new Text("Your games: ");
    yourGames.setFont(Font.font(20));

    Text welcomeText = new Text("Welcome! " + authHandler.getActiveUser());
    welcomeText.setFont(Font.font(30));
    welcomeText.setFill(Color.MEDIUMAQUAMARINE);

    Button createGameButton = new Button("Create Game");
    createGameButton.setOnAction(e -> {dialogHandle();}); // todo call GameBuilder

    TilePane tilePane = new TilePane();
    tilePane.setPadding(new Insets(10));
    tilePane.setHgap(5);
    tilePane.setVgap(5);
    tilePane.setPrefColumns(3);
    tilePane.setAlignment(Pos.CENTER);

    db.getGamesForUser(authHandler.getActiveUser()).forEach(game -> {
      String name = (String) game.get("name");
      InputStream stream = null;
      try {
        //todo: load img_path from DB
        String imageResourcePath = authHandler.getActiveUser().equals("rcd") ? "rcd_old.gif"
            : "game_img.png"; // lol, fun easter egg ig?
        stream = new FileInputStream("src/main/resources/" + imageResourcePath);
        Image image = new Image(stream);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(75);
        imageView.setFitHeight(75);
        VBox gameBox = new VBox(imageView, new Label(name));
        gameBox.setAlignment(Pos.CENTER);
        gameBox.setSpacing(5);
        gameBox.setPrefSize(100, 100);
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

    Region region = new Region();
    HBox hbox = new HBox();
    hbox.getChildren().addAll(welcomeText, region, createGameButton);
    HBox.setHgrow(region, Priority.ALWAYS);

    VBox gameLauncher = new VBox();
    gameLauncher.setSpacing(10);
    gameLauncher.getChildren().addAll(hbox, yourGames, tilePane);
    BorderPane.setMargin(gameLauncher, new Insets(30, 30, 0, 30));

    tabExplorer.setCurrentTab(gameLauncher);
  }

  //  todo: delete dialogHandle after adding game builder
  private void dialogHandle() {
    Dialog<String[]> dialog = new Dialog<>();
    dialog.setTitle("Game Builder");

    ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

    TextField nameField = new TextField();
    nameField.setPromptText("Game Name");
    TextField playersField = new TextField();
    playersField.setPromptText("Number of Players");

    VBox vbox = new VBox(10, nameField, playersField);
    dialog.getDialogPane().setContent(vbox);

    dialog.setResultConverter(dialogButton -> {
      if (dialogButton == createButtonType) {
        return new String[]{nameField.getText(), playersField.getText()};
      }
      return null;
    });

// Show the dialog and wait for the user to close it.
    Optional<String[]> result = dialog.showAndWait();
    result.ifPresent(formData -> {
      String name = formData[0];
      String numPlayers = formData[1];
      Map<String, Object> game = new HashMap<>();
      game.put("name", name);
      game.put("numPlayers", numPlayers);

      db.createGame(authHandler.getActiveUser(), game);
    });
  }

  @Override
  public void renderTabContent() {
    switchScene();
  }
}