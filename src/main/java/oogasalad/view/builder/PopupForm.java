package oogasalad.view.builder;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import oogasalad.model.attribute.Metadata;
import oogasalad.model.attribute.ObjectSchema;
import oogasalad.model.constructable.GameConstruct;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.List;

public class PopupForm<T> implements BuilderUtility {
    private static final double POPUP_WIDTH = 300;
    private static final double POPUP_HEIGHT = 300;
    private ResourceBundle resourceBundle;
    private Stage stage;
    private GameConstruct gameConstruct;
    private ObjectSchema objectSchema;
    private Map<String, ParameterStrategy> strategyMap;
    private Map<String, Metadata> fieldMap;
    private Map<Class, Class> strategies;
    public PopupForm(GameConstruct gameConstruct, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        this.gameConstruct = gameConstruct;
        objectSchema = gameConstruct.getSchema();

        strategies = defineStrategies();
        fieldMap = createFieldMap();
        strategyMap = createStrategyMap();

    }

    private Map<Class, Class> defineStrategies() {
        Map<Class, Class> map = new HashMap<>();
        map.put(String.class, TextParameterStrategy.class);
        map.put(int.class, IntegerParameterStrategy.class);
        map.put(double.class, DoubleParameterStrategy.class);
        map.put(Color.class, ColorParameterStrategy.class);
        map.put(File.class, FileParameterStrategy.class);
        map.put(Image.class, ImageParameterStrategy.class);
        return map;
    }

    private Map<String, Metadata> createFieldMap() {
        Map<String, Metadata> map = new HashMap<>();
        for (Metadata metadata : objectSchema.getAllMetadata()) {
            if (metadata.isEditable()) {
                map.put(metadata.getName(), metadata);
            }
        }
        return map;
    }

    private Map<String, ParameterStrategy> createStrategyMap() {
        Map<String, ParameterStrategy> map = new HashMap<>();
        for (String fieldName : fieldMap.keySet()) {
            try {
                map.put(fieldName, (ParameterStrategy) strategies.get(fieldMap.get(fieldName).getClass()).newInstance());
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return map;
    }

    private void saveInputToObject() {
        // iterate over strategyMap
        // get value of strategy
        // set the value of the field with a setter on the object

        for (String fieldName : strategyMap.keySet()) {
            (gameConstruct.getAttribute(fieldName))
        }

        stage.close();
    }
    public void displayForm() {

        VBox form = new VBox();
        for (String name : strategyMap.keySet()) {
            form.getChildren().add(strategyMap.get(name).renderInput(name, resourceBundle));
        }
        form.getChildren().add(makeButton("SubmitForm", resourceBundle, e->saveInputToObject()));

        Scene scene = new Scene(form, POPUP_WIDTH, POPUP_HEIGHT);
        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(resourceBundle.getString("PopupFormTitle"));
        stage.show();
    }
}
