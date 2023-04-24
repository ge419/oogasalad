package oogasalad.view.builder.customTile;

import com.google.gson.JsonObject;

import java.nio.file.Path;

public interface CustomObject {
    JsonObject save(Path folderPath);
}