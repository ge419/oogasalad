package oogasalad.view.tabexplorer.tabs;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.util.concurrent.ExecutionException;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import oogasalad.model.accesscontrol.authentication.AuthenticationHandler;
import oogasalad.model.accesscontrol.dao.UserDao;
import oogasalad.model.accesscontrol.database.schema.UserSchema;
import oogasalad.util.AlertPopUp;
import oogasalad.view.tabexplorer.TabExplorer;

public class LoginTab implements Tab {

  private final TabExplorer tabExplorer;
  private final AuthenticationHandler authHandler;
  private final UserDao userDao;
  private GridPane grid;
  private TextField tfName;
  private PasswordField pfPwd;

  @Inject
  public LoginTab(@Assisted TabExplorer tabExplorer, AuthenticationHandler authHandler,
      UserDao userDao) {
    this.tabExplorer = tabExplorer;
    this.authHandler = authHandler;
    this.userDao = userDao;
  }

  @Override
  public void renderTabContent() {
    grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(12);

    HBox hbButtons = new HBox();
    hbButtons.setSpacing(10.0);

    Button loginBtn = new Button("Login");

    loginBtn.setOnAction(e -> {
      try {
        login();
      } catch (ExecutionException ex) {
        throw new RuntimeException(ex);
      } catch (InterruptedException ex) {
        throw new RuntimeException(ex);
      }
    });

    Label lblName = new Label("Username:");
    tfName = new TextField();
    Label lblPwd = new Label("Password:");
    pfPwd = new PasswordField();

    hbButtons.getChildren().addAll(loginBtn);
    grid.add(lblName, 0, 0);
    grid.add(tfName, 1, 0);
    grid.add(lblPwd, 0, 1);
    grid.add(pfPwd, 1, 1);
    grid.add(hbButtons, 0, 2, 2, 1);
    grid.setAlignment(Pos.CENTER);

    tabExplorer.setCurrentTab(grid);
  }

  private void login() throws ExecutionException, InterruptedException {
    String username = tfName.getText().trim().toLowerCase();
    String password = pfPwd.getText();

    if (username.length() == 0 || password.length() == 0) {
      AlertPopUp.show(AlertType.ERROR, "Login failed", "The username or password is invalid");
    } else {
      // user is not registered so just signed them up
      if (!userDao.isUserRegistered(username)) {
        authHandler.register(username, password);
      } else {
        String userID = userDao.getUserID(username);
        String userPwd = (String) userDao.getUserData(userID)
            .get(UserSchema.PASSWORD.getFieldName());
        // password is correct
        if (userPwd.equals(password)) {
          authHandler.login(username, password);
          tabExplorer.displayDefaultTab();
        } else { // password incorrect
          AlertPopUp.show(AlertType.ERROR, "Incorrect login detail",
              "The username or password is incorrect");
        }
      }
    }
  }
}
