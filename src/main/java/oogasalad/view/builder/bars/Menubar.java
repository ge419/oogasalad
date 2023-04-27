package oogasalad.view.builder.bars;

import java.util.ResourceBundle;
import oogasalad.view.builder.BuilderUtility;
import oogasalad.view.builder.BuilderView;
import oogasalad.view.builder.ResourceIterator;

public class Menubar extends AbstractBar implements BuilderUtility {

  public Menubar(ResourceBundle languageResource, String id,
      BuilderView builder) {
    super(languageResource, id, builder);
  }

//  public Menubar()

  @Override
  public void addItems(String functionFileName) {
    //forEachResourceKey();
  }

  @Override
  public void refreshItems(String newFunctionFileName) {

  }

  @Override
  public void updateLanguage(String fileName) {

  }
}
