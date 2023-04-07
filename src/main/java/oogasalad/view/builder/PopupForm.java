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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.List;

public class PopupForm<T> implements BuilderUtility {
    private static final double POPUP_WIDTH = 300;
    private static final double POPUP_HEIGHT = 300;
    private T obj;
    private ResourceBundle resourceBundle;
    private T generatedObject;
    public PopupForm(T obj, ResourceBundle resourceBundle) {
        this.obj = obj;
        this.resourceBundle = resourceBundle;
    }
    public boolean validateInput(){
        return true;
    }
    public Object getObject() {
        return generatedObject;
    }
    private List<Field> getObjectFields() {
        Class<?> clazz = obj.getClass();
        return Arrays.asList(clazz.getDeclaredFields());
    }

    private void saveInputToObject(Map<String, ParameterStrategy> fieldMap) {
        try {
            generatedObject = (T) obj.getClass().getDeclaredConstructor().newInstance();
            for (String key : fieldMap.keySet()) {
                Field field = obj.getClass().getDeclaredField(key);
                field.setAccessible(true);
                field.set(generatedObject, fieldMap.get(key).getValue());
            }
        } catch (Exception e) {
            System.out.println("Unable to save form to object");
        }

    }
    public void displayForm(T obj) {
        Map<Class, Class> strategies = new HashMap<>();
        strategies.put(String.class, TextParameterStrategy.class);
        strategies.put(Integer.class, IntegerParameterStrategy.class);
        strategies.put(Double.class, DoubleParameterStrategy.class);
        strategies.put(Color.class, ColorParameterStrategy.class);
        strategies.put(File.class, FileParameterStrategy.class);
        strategies.put(Image.class, ImageParameterStrategy.class);

        List<Field> fields = getObjectFields();
        Map<String, ParameterStrategy> fieldMap = new HashMap<>();

        for (Field field : fields) {
            try {
                fieldMap.put(field.getName(), (ParameterStrategy) strategies.get(field.getClass()).newInstance());
            } catch (Exception e) {
                System.out.println("Can't initialize field strategy instance");
            }
        }
        VBox form = new VBox();
        for (String name : fieldMap.keySet()) {
            form.getChildren().add(fieldMap.get(name).renderInput(name));
        }
        form.getChildren().add(makeButton("SubmitForm", resourceBundle, e->saveInputToObject(fieldMap)));

        Scene scene = new Scene(form, POPUP_WIDTH, POPUP_HEIGHT);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(resourceBundle.getString("PopupFormTitle"));
        stage.show();
    }
    interface ParameterStrategy {
        Node renderInput(String name);
        Object getValue();
    }
    class TextParameterStrategy implements ParameterStrategy {
        private TextField element = new TextField();
        @Override
        public Node renderInput(String name) {
            Node textLabel = makeText(name, resourceBundle);
            element = (TextField) makeTextField(name);
            return makeHBox(String.format("%sTextInput", name), textLabel, element);
        }
        @Override
        public String getValue() {
            return element.getText();
        }
    }
    class IntegerParameterStrategy implements ParameterStrategy {
        private TextField element = new TextField();
        @Override
        public Node renderInput(String name) {
            Node textLabel = makeText(name, resourceBundle);
            element = (TextField) makeTextField(name);
            return makeHBox(String.format("%sIntegerInput", name), textLabel, element);
        }
        @Override
        public Integer getValue() {
            try {
                int num = Integer.parseInt(element.getText());
                return num;
            } catch (NumberFormatException nfe) {
                System.out.println("Integer not provided in integer input");
            }
            return 0;
        }
    }
    class DoubleParameterStrategy implements ParameterStrategy {
        private TextField element = new TextField();
        @Override
        public Node renderInput(String name) {
            Node textLabel = makeText(name, resourceBundle);
            element = (TextField) makeTextField(name);
            return makeHBox(String.format("%sDoubleInput", name), textLabel, element);
        }
        @Override
        public Double getValue() {
            try {
                double num = Double.parseDouble(element.getText());
                return num;
            } catch (NumberFormatException nfe) {
                System.out.println("Double not provided in double input");
            }
            return 0.0;
        }
    }
    class ColorParameterStrategy implements ParameterStrategy {
        private ColorPicker element = new ColorPicker();
        @Override
        public Node renderInput(String name) {
            Node textLabel = makeText(name, resourceBundle);
            element = (ColorPicker) makeColorPicker(name);
            return makeHBox(String.format("%sColorInput", name), textLabel, element);
        }
        @Override
        public Color getValue() {
            return element.getValue();
        }
    }
    class FileParameterStrategy implements ParameterStrategy {
        private Optional<File> file;
        @Override
        public Node renderInput(String name) {
            Node textLabel = makeText(name, resourceBundle);
            Node element = makeButton("UploadFileTitle", resourceBundle, new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    file = loadFile(resourceBundle, "UploadFileTitle");
                }
            });
            return makeHBox(String.format("%sFileInput", name), textLabel, element);
        }
        @Override
        public File getValue() {
            return file.get();
        }
    }
    class ImageParameterStrategy implements ParameterStrategy {
        private Image image;
        @Override
        public Node renderInput(String name) {
            Node textLabel = makeText(name, resourceBundle);
            Node element = makeButton("UploadFileTitle", resourceBundle, new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Optional<File> file = loadFile(resourceBundle, "UploadFileTitle");
                    try {
                        image = new Image(new FileInputStream(file.get().getPath()));
                    } catch (FileNotFoundException e) {
                        System.out.println("File for image not found");
                    }
                }
            });
            return makeHBox(String.format("%sImageInput", name), textLabel, element);
        }
        @Override
        public Image getValue() {
            return image;
        }
    }
    // Don't know the best way to implement this yet
    class ChoiceParameterStrategy implements ParameterStrategy {
        @Override
        public Node renderInput(String name) {
            return null;
        }
        @Override
        public Object getValue() {
            return null;
        }
    }
}
