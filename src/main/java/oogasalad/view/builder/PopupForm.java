package oogasalad.view.builder;

import com.google.inject.internal.asm.$Attribute;
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
import oogasalad.model.attribute.*;
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
    private Map<Attribute, ParameterStrategy> strategyMap;
    private Map<String, Metadata> fieldMap;
    private List<Attribute> attributes;
    private Map<Class, Class> strategies;
    public PopupForm(GameConstruct gameConstruct, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        this.gameConstruct = gameConstruct;
        objectSchema = gameConstruct.getSchema();
        attributes = gameConstruct.getAllAttributes();

        strategies = defineStrategies();
        strategyMap = createStrategyMap();

    }

    private Map<Class, Class> defineStrategies() {
        Map<Class, Class> map = new HashMap<>();
        map.put(StringAttribute.class, TextParameterStrategy.class);
        map.put(IntAttribute.class, IntegerParameterStrategy.class);
        map.put(DoubleAttribute.class, DoubleParameterStrategy.class);
        //map.put(Color.class, ColorParameterStrategy.class);
        //map.put(File.class, FileParameterStrategy.class);
        //map.put(Image.class, ImageParameterStrategy.class);
        return map;
    }

    private Map<Attribute, ParameterStrategy> createStrategyMap() {
        Map<Attribute, ParameterStrategy> map = new HashMap<>();
        for (Attribute attribute : attributes) {
            try {
                map.put(attribute, (ParameterStrategy) strategies.get(attribute.getClass()).newInstance());
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return map;
    }

    private void saveInputToObject() {
        for (Attribute attribute : strategyMap.keySet()) {
            if (attribute.getClass().equals(DoubleAttribute.class)) {
                DoubleAttribute.from(gameConstruct.getAttribute(attribute.getKey())).setValue((double) strategyMap.get(attribute).getValue());
            } else if (attribute.getClass().equals(IntAttribute.class)) {
                IntAttribute.from(gameConstruct.getAttribute(attribute.getKey())).setValue((int) strategyMap.get(attribute).getValue());
            } else if (attribute.getClass().equals(StringAttribute.class)) {
                StringAttribute.from(gameConstruct.getAttribute(attribute.getKey())).setValue((String) strategyMap.get(attribute).getValue());
            }
        }

        stage.close();
    }
    public void displayForm() {

        VBox form = new VBox();
        for (Attribute attribute : strategyMap.keySet()) {
            ParameterStrategy strategy = strategyMap.get(attribute);
            /*
            use schema to check metadata for min/max, default
            strategy.setDefault(metadata result);
            assert min/max??
             */

            form.getChildren().add(strategy.renderInput(attribute.getKey(), resourceBundle));
        }
        form.getChildren().add(makeButton("SubmitForm", resourceBundle, e->saveInputToObject()));

        Scene scene = new Scene(form, POPUP_WIDTH, POPUP_HEIGHT);
        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(resourceBundle.getString("PopupFormTitle"));
        stage.show();
    }
}
