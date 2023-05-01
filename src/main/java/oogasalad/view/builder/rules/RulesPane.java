package oogasalad.view.builder.rules;

import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import oogasalad.controller.BuilderController;
import oogasalad.view.builder.BuilderView;

public class RulesPane extends BorderPane {

  private final BuilderView myBuilder;
  private final ResourceBundle myLanguage;
  private final BuilderController myBuilderController;
  private ListView<String> myRulesList;
  private ObservableList<String> myRules;
  private ComboBox<String> myCheckbox;
  private Button myEditButton;
  private Button myDeleteButton;

  public RulesPane(BuilderView builder, BuilderController bc, ResourceBundle language) {
    super();
    myLanguage = language;
    myBuilder = builder;
    myBuilderController = bc;
    this.setId("RulesPane");
    initializeRulesListview();
    initializeTiletypesCheckbox();
    VBox buttonBox = initializeButtons();
    this.setLeft(myCheckbox);
    this.setCenter(myRulesList);
    this.setRight(buttonBox);
  }

  private VBox initializeButtons() {
    myEditButton = new Button(myLanguage.getString("SetRule"));
    myDeleteButton = new Button(myLanguage.getString("RemoveRule"));
    myEditButton.setOnAction(e -> {
      // tell builder controller to give me the properties for this rule.
      String selectedRule = myRulesList.getSelectionModel().getSelectedItem();
      String selectedTiletype = myCheckbox.getSelectionModel().getSelectedItem();
      try {
        myBuilderController.makeRulesPopup(myBuilderController.getClassForRule(selectedRule));
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    });

    myDeleteButton.setOnAction(e -> {
      //tell builder controller to delete rule from tile type
      String selectedRule = myRulesList.getSelectionModel().getSelectedItem();
      String selectedTiletype = myCheckbox.getSelectionModel().getSelectedItem();
      myBuilderController.removeRuleFromTiletype(selectedTiletype, selectedRule);
    });
    return new VBox(myEditButton, myDeleteButton);
  }


  private void initializeRulesListview() {
    myRulesList = new ListView<>();
    myRules = FXCollections.observableArrayList(myBuilderController.getListOfRules());
    myRulesList.setItems(myRules);
    myRulesList.getSelectionModel().selectedItemProperty()
        .addListener(((observable, oldValue, newValue) -> {
          System.out.println(
              "oh wow, you selected " + myRulesList.getSelectionModel().getSelectedItem());
        }));
  }

  private void initializeTiletypesCheckbox() {
    myCheckbox = new ComboBox<>();
    myCheckbox.setPromptText(myLanguage.getString("SelectTiletype"));
    updateTileTypes();
    myCheckbox.valueProperty().addListener(((observable, oldValue, newValue) -> {
      System.out.println("Oh my, you selected " + newValue);
    }));
  }

  /**
   * <p>Update the tiletypes selectable by the user with the current set of tiletypes.</p>
   */
  public void updateTileTypes() {
    myCheckbox.setItems(
        FXCollections.observableArrayList(myBuilderController.getCurrentTiletypes()));
  }
}
