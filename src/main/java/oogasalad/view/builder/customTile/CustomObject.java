package oogasalad.view.builder.customTile;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.file.Path;

public interface CustomObject {
    JsonObject save(Path folderPath) throws IOException;
}