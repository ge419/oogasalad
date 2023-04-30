package oogasalad.util;

public class PathFinder {
  public static String getGameThumbnail(String gameID) {
    String basePath = "file:src/main/resources/"+"0hbvOqXKOQdhpgu3aLIO"+"/"; // Replace with the base path to your files

    // Replace "Object1" with the name of your specific object
    String objectPath = basePath + "thumbnail";

    // Replace with the file extension of your object
    String fileExtension = ".png";

    // Append the file extension to the object path
    objectPath += fileExtension;

//    System.out.println("Object path: "+objectPath);
    // Return the full file path
    return objectPath;
  }

  public static String getUserAvatar(String userID){
    return "file:data/users/"+userID+"/avatar.jpeg";
  }

  public static String getGameDataPath(String gameID){
    return "data/"+gameID;
  }
}