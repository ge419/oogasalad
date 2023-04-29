package oogasalad.view.builder.itempanes;

import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import oogasalad.view.builder.BuilderUtility;
import oogasalad.view.builder.BuilderView;

/**
 * <p>Sidebar will implement and manage a pane of buttons for any UI.</p>
 *
 * <p>This class will create it's buttons based off of property files that
 * dictate what each button's functionality is by listing it's method.</p>
 *
 * <p>The actual name of each button displayed is controlled by the languageResource,
 * which is a required constructor argument.</p>
 *
 * @author tmh85
 */
public class ItemPane extends AbstractItemPane implements BuilderUtility {

  private Pane myPane;
  private String myCurrentBundleName;

  /**
   * @see AbstractItemPane#AbstractItemPane(ResourceBundle, String, BuilderView)
   */
  public ItemPane(ResourceBundle languageResource, String id, BuilderView builder) {
    // create pane
    super(languageResource, id, builder);
    ScrollPane scrollablePane = new ScrollPane();
    myPane = (Pane) makeVBox(id);
    scrollablePane.setContent(myPane);
  }

  @Override
  public void addItems(String functionFileName) {
    myCurrentBundleName = functionFileName;
    ResourceBundle bundle = getResource(functionFileName);
    forEachResourceKey(bundle,
        key -> createButton(key, bundle.getString(key)));
  }

  @Override
  public void refreshItems(String newFunctionFileName) {
    myPane.getChildren().clear();
    addItems(newFunctionFileName);
  }

  @Override
  public void updateLanguage(String fileName) {
    setLanguage(getResource(fileName));
    refreshItems(myCurrentBundleName);
  }

  @Override
  public Node asNode() {
    return myPane;
  }

  /**
   * <p>Reflectively create a button and add it to a pane</p>
   * @param key resource file key
   * @param buttonClickMethodName method name as a string
   */
  private void createButton(String key, String buttonClickMethodName) {
    Node button = makeButton(key, getLanguage(),
        e -> runMethodFromString(buttonClickMethodName));
    myPane.getChildren().add(button);
  }

  private void test() {
    // nothing
    System.out.println("why that button sure did do nothing!");
  }

  private void openTileMenu() {
    refreshItems("TileSideMenu");
  }

  private void backToSidebarMenu() {
    refreshItems("SideBar1");
  }

  private void backFromRules(){
    backToSidebarMenu();
    getBuilder().switchToBoard();
  }

  private void rulesMenu(){
    refreshItems("RulesSideMenu");
    getBuilder().switchToRules();
  }

  private void toggleTileCreation() {
    getBuilder().toggleTileCreation();
  }

  private void deleteToggle() {
    getBuilder().toggleTileDeletion();
  }

  private void cancelAction() {
    getBuilder().cancelAction();
  }
  private void displayGameInfoForm() {
    ResourceBundle bundle = getResource(myCurrentBundleName);
    myPane.getChildren().clear();
    // thumbnail image, name, description, genre
    Text nameLabel = (Text) makeText("GameNameLabel", bundle);
    TextField nameInput = (TextField) makeTextField("GameNameInput");
    Text descriptionLabel = (Text) makeText("GameDescriptionLabel", bundle);
    TextField descriptionInput = (TextField) makeTextField("GameDescriptionInput");
    Text genreLabel = (Text) makeText("GameGenreLabel", bundle);
    TextField genreInput = (TextField) makeTextField("GameGenreInput");
    Button thumbnailInput = (Button) makeButton("GameThumbnailInput", bundle, e -> System.out.println("Upload Thumbnail Button Clicked."));
    Button saveButton = (Button) makeButton("SaveGameInfoButton", bundle, e -> System.out.println("Save Game Info Button Clicked."));
    myPane.getChildren().add(new HBox(nameLabel, nameInput));
    myPane.getChildren().add(new HBox(descriptionLabel, descriptionInput));
    myPane.getChildren().add(new HBox(genreLabel, genreInput));
    myPane.getChildren().add(thumbnailInput);
    myPane.getChildren().add(saveButton);
  }
}
