package oogasalad.view.builder.popupform;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oogasalad.model.attribute.*;
import oogasalad.model.constructable.GameConstruct;
import oogasalad.view.builder.*;

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
    private List<Attribute> attributes;
    private Map<Class, Class> attributeMap;
    public PopupForm(GameConstruct gameConstruct, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        this.gameConstruct = gameConstruct;
        objectSchema = gameConstruct.getSchema();
        attributes = gameConstruct.getAllAttributes();

        attributeMap = defineStrategies();
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
                map.put(attribute, (ParameterStrategy) attributeMap.get(attribute.getClass()).newInstance());
            } catch (Exception e) {
                new visualization.PopupError(resourceBundle, "FormStrategyNotFound");
            }
        }
        return map;
    }

    private void saveInputToObject() {
        for (Attribute attribute : strategyMap.keySet()) {
            Optional<Metadata> metadata = objectSchema.getMetadata(attribute.getKey());
            if (metadata.isPresent() && !strategyMap.get(attribute).validateInput(metadata.get())) {
                System.out.println(String.format("Input for %s is invalid.", attribute.getKey()));
                new visualization.PopupError(resourceBundle, "InvalidFormData");
                return;
            }
            strategyMap.get(attribute).setValue(attribute);
        }
        stage.close();
    }
    public void displayForm() {
        VBox form = new VBox();
        for (Attribute attribute : strategyMap.keySet()) {
            ParameterStrategy strategy = strategyMap.get(attribute);

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
