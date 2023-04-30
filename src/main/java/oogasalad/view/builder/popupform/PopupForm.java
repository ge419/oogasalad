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
import javafx.stage.Stage;
import oogasalad.model.attribute.*;
import oogasalad.model.constructable.GameConstruct;
import oogasalad.view.builder.BuilderUtility;
import oogasalad.view.builder.ErrorHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Creates a form for user input for any game construct. Uses the attributes
 * of the constructable object to select a strategy for displaying each
 * form element and uses the schema's metadata to validate data.
 * Submitting the form saves the form data to the game construct in-place.
 * Example usage: The example code adds a form for a tile to the root VBox
 * VBox root = new VBox();
 * new PopupForm(new Tile(), myResourceBundle, root);
 * Scene myScene = new Scene(root);
 * Stage myStage = new Stage();
 * myStage.setScene(myScene);
 * myStage.show();
 * @author Jason Fitzpatrick
 * @author Dominic Martinez
 */
public class PopupForm implements BuilderUtility {
    private static final Logger LOGGER = LogManager.getLogger(PopupForm.class);
    private final ParameterStrategyFactory factory;
    private final ResourceBundle resourceBundle;
    private final GameConstruct gameConstruct;
    private final Pane form;
    private final Map<Class<? extends Metadata>, ParameterStrategyCreator> strategyMap;
    private final List<ParameterStrategy> currentParameters;
    private String objectID;

    /**
     * Initializes the popupform and populates the given Pane with the form content.
     * @param gameConstruct an object that extends the GameConstruct class
     * @param resourceBundle a resource bundle used to provide access to error strings and labels
     * @param form a pane intended to contain the form contents
     */
    public PopupForm(GameConstruct gameConstruct, ResourceBundle resourceBundle, Pane form, Injector outsideInjector) {
        this.resourceBundle = resourceBundle;
        this.gameConstruct = gameConstruct;
        this.form = form;
        this.objectID = gameConstruct.getId();
        // TODO: Create injector in controller
        Injector injector = outsideInjector.createChildInjector(new PopupFormModule());
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
            TileListMetadata.class, factory::buildTileListParameter,
            ColorMetadata.class, factory::buildColorParameter,
            ImageMetadata.class, factory::buildImageParameter
        );
    }

    private void saveInputToObject() {
        for (ParameterStrategy parameter : currentParameters) {
            if (!parameter.isInputValid()) {
                LOGGER.error("Input for {} is invalid", parameter.getMetadata().getName());
                ErrorHandler.displayError(resourceBundle.getString("InvalidFormData"));
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

        Attribute attribute = gameConstruct.getAttribute(metadata.getKey()).get();
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
            form.getChildren().add(parameterStrategy.get().renderInput(resourceBundle, form, objectID));
        }

        form.getChildren().add(makeButton("SubmitForm", resourceBundle, e -> saveInputToObject()));
    }
    @FunctionalInterface
    private interface ParameterStrategyCreator {
        ParameterStrategy build(Attribute attribute, Metadata metadata);
    }
}
