package oogasalad.view.builder;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PopupForm<T> implements BuilderUtility {

  private static final double POPUP_WIDTH = 300;
  private static final double POPUP_HEIGHT = 300;
  private final Class obj;
  private final ResourceBundle resourceBundle;
  private T generatedObject;
  private Stage stage;

  public PopupForm(Class obj, ResourceBundle resourceBundle) {
    this.obj = obj;
    this.resourceBundle = resourceBundle;
  }

  //    public boolean validateInput(){
//        return true;
//    }
  public Object getObject() {
    return generatedObject;
  }

  private List<Parameter> getObjectFields() {
    Class<?> clazz = obj;
    Constructor<?>[] constructors = obj.getDeclaredConstructors();
    //Class<?>[] parameterTypes = constructors[0].getParameterTypes();
    return List.of(constructors[0].getParameters());
  }

  private void saveInputToObject(Map<String, ParameterStrategy> fieldMap) {
    try {
      Constructor<?> constructor = obj.getDeclaredConstructors()[0];
      List<Parameter> parameters = List.of(constructor.getParameters());
      Object[] args = parameters.stream()
          .map(param -> fieldMap.get(param.getName()).getValue())
          .toArray();

      generatedObject = (T) constructor.newInstance(args);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Unable to save form to object");
    }
    stage.close();
  }

  public void displayForm() {
    Map<Class, Class> strategies = new HashMap<>();
    strategies.put(String.class, TextParameterStrategy.class);
    strategies.put(int.class, IntegerParameterStrategy.class);
    strategies.put(double.class, DoubleParameterStrategy.class);
    strategies.put(Color.class, ColorParameterStrategy.class);
    strategies.put(File.class, FileParameterStrategy.class);
    strategies.put(Image.class, ImageParameterStrategy.class);

    List<Parameter> fields = getObjectFields();
    Map<String, ParameterStrategy> fieldMap = new HashMap<>();

    for (Parameter param : fields) {
      try {
        fieldMap.put(param.getName(),
            (ParameterStrategy) strategies.get(param.getType()).newInstance());
      } catch (InstantiationException e) {
        throw new RuntimeException(e);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }

    }
    VBox form = new VBox();
    for (String name : fieldMap.keySet()) {
      form.getChildren().add(fieldMap.get(name).renderInput(name, resourceBundle));
    }
    form.getChildren()
        .add(makeButton("SubmitForm", resourceBundle, e -> saveInputToObject(fieldMap)));

    Scene scene = new Scene(form, POPUP_WIDTH, POPUP_HEIGHT);
    stage = new Stage();
    stage.setScene(scene);
    stage.setTitle(resourceBundle.getString("PopupFormTitle"));
    stage.show();
  }
}
