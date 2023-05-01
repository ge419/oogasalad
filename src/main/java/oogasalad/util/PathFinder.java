package oogasalad.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
     return FILE_PREFIX + getGameThumbnailPath(gameID);
  }

  public static String getGameDataPath(String gameID){
    return GAME_DIR_PATH+gameID;
  }
}