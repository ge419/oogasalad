package oogasalad.view.builder.customTile;

import com.google.gson.JsonObject;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Path;

public interface CustomObject{
    JsonObject save(Path folderPath) throws IOException;

    static CustomObject load(JsonObject jsonObject) {
        String type = jsonObject.get("type").getAsString().strip();
        System.out.println("type = " + type);

        switch (type) {
            case "CustomImage":
                System.out.println("jsonObject = " + jsonObject);
                String stringPath = jsonObject.get("filePath").getAsString();
                System.out.println("stringPath = " + stringPath);
                return new CustomImage(jsonObject);

            // add more cases for each type of object you need to support
            default:
                throw new RuntimeException("Unknown object type: " + type);
        }
    }

    VBox getInfo();

    void setLocation();

    int getIndex();


    //TODO Figure out why the CustomImage class isn't found on reflect
//    static CustomObject load(JsonObject jsonObject) {
//        String type = jsonObject.get("type").getAsString().strip();
//        System.out.println("type = " + type);
//        try {
//            Class<?> clazz = Class.forName(type);
//            if (!CustomObject.class.isAssignableFrom(clazz)) {
//                throw new RuntimeException("Class " + type + " does not implement CustomObject interface");
//            }
//            Constructor<?> constructor = clazz.getConstructor(JsonObject.class);
//            return (CustomObject) constructor.newInstance(jsonObject);
//        } catch (Exception e) {
//            throw new RuntimeException("Error loading CustomObject", e);
//        }
//    }

}