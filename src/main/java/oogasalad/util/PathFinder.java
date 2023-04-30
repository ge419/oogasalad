package oogasalad.util;

import java.util.ResourceBundle;

public class PathFinder {
  private static String FILE_PROPERTIES_PATH;
  private static ResourceBundle fileResourceBundle;
  private static final String FILE_PREFIX;
  private static final String BACKSLASH;
  private static final String USER_DIR_PATH;
  private static final String GAME_DIR_PATH;
  private static final String USER_AVATAR;
  private static final String GAME_THUMBNAIL;

  static{
    FILE_PROPERTIES_PATH = "FileLocation.properties";
    FILE_PREFIX ="file:";
    BACKSLASH = "/";
    USER_DIR_PATH = "data/users/";
    GAME_DIR_PATH = "data/games/";
    USER_AVATAR = "avatar.jpeg";
    GAME_THUMBNAIL = "thumbnail.jpeg";
  }

  public static String getUserAvatarPath(String userID){
    return USER_DIR_PATH + userID + BACKSLASH + USER_AVATAR;
  }
  public static String getUserAvatar(String userID){
    return FILE_PREFIX + getUserAvatarPath(userID);
  }
  public static String getGameThumbnailPath(String gameID) {
    return getGameDataPath(gameID) + BACKSLASH + GAME_THUMBNAIL;
  }

  public static String getGameThumbnail(String gameID) {
//    String basePath = "file:src/main/resources/"+"0hbvOqXKOQdhpgu3aLIO"+"/"; // Replace with the base path to your files
//
//    // Replace "Object1" with the name of your specific object
//    String objectPath = basePath + "thumbnail";
//
//    // Replace with the file extension of your object
//    String fileExtension = ".jpeg";
//
//    // Append the file extension to the object path
//    objectPath += fileExtension;
//
////    System.out.println("Object path: "+objectPath);
//    // Return the full file path
//
//    // todo eventually
     return FILE_PREFIX + getGameThumbnailPath(gameID);
//    return objectPath;
  }

  public static String getGameDataPath(String gameID){
    return GAME_DIR_PATH+gameID;
  }
}