package oogasalad.view.builder.popupform;

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import oogasalad.model.attribute.*;
import oogasalad.model.constructable.GameConstruct;
import oogasalad.view.builder.BuilderUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PopupForm implements BuilderUtility {
    private static final Logger LOGGER = LogManager.getLogger(PopupForm.class);
    private final ParameterStrategyFactory factory;
    private final ResourceBundle resourceBundle;
    private final GameConstruct gameConstruct;
    private final Pane form;
    private final Map<Class<? extends Metadata>, ParameterStrategyCreator> strategyMap;
    private final List<ParameterStrategy> currentParameters;

    public PopupForm(GameConstruct gameConstruct, ResourceBundle resourceBundle, Pane form) {
        this.resourceBundle = resourceBundle;
        this.gameConstruct = gameConstruct;
        this.form = form;
        // TODO: Create injector in controller
        Injector injector = Guice.createInjector(new PopupFormModule());
        this.factory = injector.getInstance(ParameterStrategyFactory.class);
        strategyMap = createStrategyMap();
        currentParameters = new ArrayList<>();

        createFormFields();
        this.gameConstruct.schemaProperty().addListener(
            (observable, oldValue, newValue) -> createFormFields());
    }

    private Map<Class<? extends Metadata>, ParameterStrategyCreator> createStrategyMap() {
        return Map.of(
            StringMetadata.class, factory::buildTextParameter,
            IntMetadata.class, factory::buildIntegerParameter,
            DoubleMetadata.class, factory::buildDoubleParameter,
            PositionMetadata.class, factory::buildPositionParameter,
            TileMetadata.class, factory::buildTileParameter,
            TileListMetadata.class, factory::buildTileListParameter
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
    @FunctionalInterface
    private interface ParameterStrategyCreator {
        ParameterStrategy build(Attribute attribute, Metadata metadata);
    }
}
