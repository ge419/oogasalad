package oogasalad.view.builder.popupform;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oogasalad.model.attribute.Attribute;
import oogasalad.model.attribute.DoubleMetadata;
import oogasalad.model.attribute.IntMetadata;
import oogasalad.model.attribute.Metadata;
import oogasalad.model.attribute.ObjectSchema;
import oogasalad.model.attribute.StringMetadata;
import oogasalad.model.constructable.GameConstruct;
import oogasalad.view.builder.BuilderUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PopupForm implements BuilderUtility {

    private static final double POPUP_WIDTH = 300;
    private static final double POPUP_HEIGHT = 300;
    private static final Logger LOGGER = LogManager.getLogger(PopupForm.class);
    private final ParameterStrategyFactory factory;
    private final ResourceBundle resourceBundle;
    private Stage stage;
    private final GameConstruct gameConstruct;
    private final VBox form;
    private final Map<Class<? extends Metadata>, ParameterStrategyCreator> strategyMap;
    private final List<ParameterStrategy> currentParameters;

    public PopupForm(GameConstruct gameConstruct, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        this.gameConstruct = gameConstruct;
        form = new VBox();
        // TODO: Create injector in controller
        Injector injector = Guice.createInjector(new PopupFormModule());
        this.factory = injector.getInstance(ParameterStrategyFactory.class);
        strategyMap = createStrategyMap();
        currentParameters = new ArrayList<>();

        createFormFields();
        this.gameConstruct.getSchemaProperty().addListener(
            (observable, oldValue, newValue) -> createFormFields());
    }

    private Map<Class<? extends Metadata>, ParameterStrategyCreator> createStrategyMap() {
        return Map.of(
            StringMetadata.class, factory::buildTextParameter,
            IntMetadata.class, factory::buildIntegerParameter,
            DoubleMetadata.class, factory::buildDoubleParameter
        );
    }

    private void saveInputToObject() {
        for (ParameterStrategy parameter : currentParameters) {
            if (!parameter.isInputValid()) {
                LOGGER.info("Input for {} is invalid", parameter.getMetadata().getName());
                new Alert(Alert.AlertType.ERROR, resourceBundle.getString("InvalidFormData"));
                return;
            }
        }

        for (ParameterStrategy parameter : currentParameters) {
            parameter.saveInput();
        }

        stage.close();
    }

    private Optional<ParameterStrategy> createStrategy(Metadata metadata) {
        ParameterStrategyCreator creator = strategyMap.get(metadata.getClass());
        if (creator == null) {
            // TODO: use bundle
            LOGGER.error("No available strategy for {}", metadata.getClass());
            return Optional.empty();
        }

        Attribute attribute = gameConstruct.getAttribute(metadata.getKey());
        return Optional.of(creator.build(attribute, metadata));
    }

    private void createFormFields() {
        ObjectSchema schema = gameConstruct.getSchema();
        form.getChildren().clear();
        currentParameters.clear();

        for (Metadata metadata : schema.getAllMetadata()) {
            if (!metadata.isViewable()) {
                continue;
            }

            Optional<ParameterStrategy> parameterStrategy = createStrategy(metadata);
            if (parameterStrategy.isEmpty()) {
                continue;
            }

            currentParameters.add(parameterStrategy.get());
            form.getChildren().add(parameterStrategy.get().renderInput(resourceBundle));
        }

        form.getChildren().add(makeButton("SubmitForm", resourceBundle, e -> saveInputToObject()));
    }

    public void displayForm() {
        Scene scene = new Scene(form, POPUP_WIDTH, POPUP_HEIGHT);
        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(resourceBundle.getString("PopupFormTitle"));
        stage.show();
    }

    @FunctionalInterface
    private interface ParameterStrategyCreator {

        ParameterStrategy build(Attribute attribute, Metadata metadata);
    }
}
