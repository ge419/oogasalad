package oogasalad.view.builder;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.List;

public class PopupForm<T> implements BuilderUtility {
    private static final double POPUP_WIDTH = 300;
    private static final double POPUP_HEIGHT = 300;
    private static final Logger LOGGER = LogManager.getLogger(PopupForm.class);
    private Class<T> objectClass;
    private ResourceBundle resourceBundle;
    private T generatedObject;
    private Stage stage;
    public PopupForm(Class objectClass, ResourceBundle resourceBundle) {
        this.objectClass = objectClass;
        this.resourceBundle = resourceBundle;
        this.generatedObject = null;

        displayForm();
    }
    private List<Parameter> getObjectFields() {
        return List.of(objectClass.getDeclaredConstructors()[0].getParameters());
    }

    private void saveInputToObject(Map<String, ParameterStrategy> fieldMap) {
        try {
            Constructor<?> constructor = objectClass.getDeclaredConstructors()[0];
            List<Parameter> parameters = List.of(constructor.getParameters());
            Object[] args = parameters.stream()
                    .map(param -> fieldMap.get(param.getName()).getValue())
                    .toArray();

            generatedObject = (T) constructor.newInstance(args);
        } catch (Exception e) {
            LOGGER.error("Failed to save form to object.");
        }
        stage.close();
    }
    public Object displayForm() {
        Map<Class, Class> strategies = new HashMap<>();
        strategies.put(String.class, TextParameterStrategy.class);
        strategies.put(int.class, IntegerParameterStrategy.class);
        strategies.put(double.class, DoubleParameterStrategy.class);
        strategies.put(Color.class, ColorParameterStrategy.class);
        strategies.put(File.class, FileParameterStrategy.class);
        strategies.put(Image.class, ImageParameterStrategy.class);

        Map<String, ParameterStrategy> fieldMap = createFieldMap(strategies);

        VBox form = createForm(fieldMap);
        form.getChildren().add(makeButton("SubmitForm", resourceBundle, e->saveInputToObject(fieldMap)));

        Scene scene = createScene(form);
        stage = createStage(scene);

        stage.showAndWait();

        return generatedObject;
    }

    private Map<String, ParameterStrategy> createFieldMap(Map<Class, Class> strategies) {
        Map<String, ParameterStrategy> fieldMap = new HashMap<>();
        List<Parameter> fields = getObjectFields();

        for (Parameter param : fields) {
            Class<? extends ParameterStrategy> strategyClass = strategies.get(param.getType());
            if (strategyClass == null) {
                LOGGER.error("No parameter strategy found for type {}", param.getType());
            } else {
                try {
                    fieldMap.put(param.getName(), strategyClass.getDeclaredConstructor().newInstance());
                } catch (Exception e) {
                    LOGGER.error("Failed to create parameter strategy for type {}", param.getType());
                }
            }
        }
        return fieldMap;
    }
    private VBox createForm(Map<String, ParameterStrategy> fieldMap) {
        VBox form = new VBox();

        for (String name : fieldMap.keySet()) {
            form.getChildren().add(fieldMap.get(name).renderInput(name, resourceBundle));
        }
        return form;
    }
    private Scene createScene(VBox form) {
        Scene scene = new Scene(form, POPUP_WIDTH, POPUP_HEIGHT);
        return scene;
    }
    private Stage createStage(Scene scene) {
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(resourceBundle.getString("PopupFormTitle"));
        return stage;
    }
}
