package oogasalad.view.tabexplorer.tabs;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import java.util.concurrent.ExecutionException;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import oogasalad.model.accesscontrol.authentication.AuthenticationHandler;
import oogasalad.view.tabexplorer.TabExplorer;

public class LoginTab implements Tab {
  private TabExplorer tabExplorer;
  private GridPane grid;
  private AuthenticationHandler authHandler;
  private TextField tfName;
  private PasswordField pfPwd;

  @Inject
  public LoginTab(@Assisted TabExplorer tabExplorer, AuthenticationHandler authHandler){
    this.tabExplorer = tabExplorer;
    this.authHandler = authHandler;
  }

  @Override
  public void renderTabContent() {
    grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(12);

    HBox hbButtons = new HBox();
    hbButtons.setSpacing(10.0);

    Button loginBtn = new Button("Login");

    loginBtn.setOnAction(e->{
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
    String username = tfName.getText();
    String password = pfPwd.getText();
    if (username.length()== 0 || password.length() == 0){
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Login failed");
      alert.setHeaderText(null);
      alert.setContentText("The username or password is invalid");
      alert.showAndWait();
    } else{
      authHandler.login(username, password);
      tabExplorer.displayDefaultTab();
    }
  }
}
