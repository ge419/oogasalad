package oogasalad.view.builder.customTile;

import com.google.gson.JsonObject;
import javafx.scene.layout.VBox;
import oogasalad.model.attribute.Metadata;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Path;

public interface CustomElement {
    JsonObject save(Path folderPath) throws IOException;

    static CustomElement load(JsonObject jsonObject) {
        String type = jsonObject.get("type").getAsString().strip();
        System.out.println("jsonObject = " + jsonObject);
        switch (type) {
            case "CustomImage":
                return new CustomImage(jsonObject);
            case "CustomText":
                return new CustomText(jsonObject);
            case "CustomColorBox":
                return new CustomColorBox(jsonObject);


            // add more cases for each type of object you need to support
            default:
                throw new RuntimeException("Unknown object type: " + type);
        }
    }

    VBox getInfo();

    void setLocation();

    int getIndex();

    String getName();

    void setName(String text);

    boolean isEditable();

    void setEditable(boolean selected);

    Metadata getMetaData();




    //TODO Figure out why the CustomImage class isn't found on reflect
//    static CustomElement load(JsonObject jsonObject) {
//        String type = jsonObject.get("type").getAsString().strip();
//        System.out.println("type = " + type);
//        try {
////            Class<?> clazz = Class.forName(type);
//            Class<?> clazz = Class.forName("CustomImage");
//
//            if (!CustomElement.class.isAssignableFrom(clazz)) {
//                throw new RuntimeException("Class " + type + " does not implement CustomElement interface");
//            }
//            Constructor<?> constructor = clazz.getConstructor(JsonObject.class);
//            return (CustomElement) constructor.newInstance(jsonObject);
//        } catch (Exception e) {
//            throw new RuntimeException("Error loading CustomElement", e);
//        }
//    }

}