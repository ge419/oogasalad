package oogasalad.view.builder.rules;

import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import oogasalad.controller.BuilderController;
import oogasalad.view.builder.BuilderUtility;
import oogasalad.view.builder.BuilderView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RulesPane extends BorderPane implements BuilderUtility {

  private static final Logger LOG = LogManager.getLogger(RulesPane.class);
  private BuilderView myBuilder;
  private ResourceBundle myLanguage;
  private BuilderController myBuilderController;
  private ListView<String> myRulesList;
  private ObservableList<String> myRules;
  private ComboBox<String> myCheckbox;
  private Button myEditButton;
  private Button myDeleteButton;
  private TextArea myTextbox;
  private String myCurrentDescription;

  public RulesPane(BuilderView builder, BuilderController bc, ResourceBundle language) {
    super();
    myLanguage = language;
    myBuilder = builder;
    myBuilderController = bc;
    this.setId("RulesPane");
    initializeRulesListview();
    this.setCenter(myRulesList);
    initializeTiletypesCheckbox();
    this.setLeft(myCheckbox);
    initializeRightSide();
    initializeBottom();
    this.setId("RulesPane");
  }

  private void initializeBottom(){
    initializeTextbox();
    HBox bottomBox = (HBox) makeHBox("RulesBottom", myTextbox);
    this.setBottom(bottomBox);
  }

  private void initializeRightSide(){
    initializeButtons();
    VBox rightsideBox = (VBox) makeVBox("RulesRightside", myEditButton, myDeleteButton);
    rightsideBox.setAlignment(Pos.CENTER);
    this.setRight(rightsideBox);
  }

  private void initializeButtons() {
    myEditButton = new Button(myLanguage.getString("SetRule"));
    myDeleteButton = new Button(myLanguage.getString("RemoveRule"));
    myEditButton.setOnAction(e -> {
      // tell builder controller to give me the properties for this rule.
      String selectedRule = myRulesList.getSelectionModel().getSelectedItem();
      String selectedTiletype = myCheckbox.getSelectionModel().getSelectedItem();
      if (!myBuilderController.makeRulesPopup(selectedTiletype, selectedRule)){
        myBuilder.showError("UneditableRuleError");
      }
    });

    myDeleteButton.setOnAction(e -> {
      //tell builder controller to delete rule from tile type
      String selectedRule = myRulesList.getSelectionModel().getSelectedItem();
      String selectedTiletype = myCheckbox.getSelectionModel().getSelectedItem();
      if (!myBuilderController.removeRuleFromTiletype(selectedTiletype, selectedRule)){
        myBuilder.showError("UnremovableRuleError");
      }
    });
  }


  private void initializeRulesListview() {
    myRulesList = new ListView<>();
    myRules = FXCollections.observableArrayList(myBuilderController.getListOfRules());
    myRulesList.setItems(myRules);
    myRulesList.getSelectionModel().selectedItemProperty()
        .addListener(((observable, oldValue, newValue) -> {
          LOG.info("The user selected the rule " + newValue + " in the rule menu.");
          updateText(newValue);
        }));
    myRulesList.setId("RulesListview");
//    myRulesList.setMinSize(100, 100);
  }

  private void initializeTiletypesCheckbox() {
    myCheckbox = new ComboBox<>();
    myCheckbox.setPromptText(myLanguage.getString("SelectTiletype"));
    myCheckbox.setItems(
        FXCollections.observableArrayList(myBuilderController.getCurrentTiletypes()));
    myCheckbox.valueProperty().addListener(((observable, oldValue, newValue) -> {
      LOG.info("The user selected tiletype " + newValue + " in the rule menu.");
    }));
    myCheckbox.setId("RulesTiletypeSelector");
  }

  private void initializeTextbox(){
    myTextbox = new TextArea();
    resetText();
    myTextbox.setEditable(false);
    myTextbox.setId("RulesDescriptionBox");
  }

  private void resetText(){
    myCurrentDescription = myLanguage.getString("DefaultRuleDescription");
    myTextbox.clear();
    myTextbox.setPromptText(myCurrentDescription);
  }

  private void updateText(String selectedRule){
    myCurrentDescription = myBuilderController.getRuleDescription(selectedRule);
    myTextbox.setText(myCurrentDescription);
  }
}
