package oogasalad.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class FileUploader {

  public static void uploadUserAvatar(String userID) {
    uploadFile(PathFinder.getUserAvatarPath(userID));
  }

  public static void uploadGameThumbnail(String gameID) {
    uploadFile(PathFinder.getGameThumbnailPath(gameID));
  }


  private static void uploadFile(String dirPath) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Upload an Image");

    String initialDir = System.getProperty("user.home") + "/Documents";
    System.out.println(initialDir);
    //    System.out.println(System.getProperty("user.home"));
    // Set the initial directory to the user's home directory
    fileChooser.setInitialDirectory(new File(initialDir));
    // Add a filter to show only image files
    fileChooser.getExtensionFilters().addAll(
        new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
    );
    // Show the dialog box and get the selected file
    File selectedFile = fileChooser.showOpenDialog(null);

    if (selectedFile != null) {
      // Create a directory in the specified directory with the user ID name, if it doesn't already exist
      File directory = new File(dirPath);
      if (!directory.exists()) {
        directory.mkdirs();
      }
      Path userAvatarPath = Paths.get(directory.toPath().toUri());
      // Copy the selected file to the newly created directory
      try {
        Files.copy(selectedFile.toPath(), userAvatarPath, StandardCopyOption.REPLACE_EXISTING);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

}
