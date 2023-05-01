package oogasalad.view.tabexplorer.userpreferences;

import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import oogasalad.model.accesscontrol.authentication.AuthenticationHandler;
import oogasalad.model.accesscontrol.dao.UserDao;
import oogasalad.model.accesscontrol.database.schema.UserSchema;

public class UserPreferences {

  public static String LANGUAGE_PROPERTIES_PATH = "tabexplorer.languages.";
  private static final String DEFAULT_LANGUAGE_PROPERTY = LANGUAGE_PROPERTIES_PATH + "en_US";

  private final UserDao userDao;
  private final AuthenticationHandler authHandler;
  private String preferredLanguage;
  private final List<Consumer<String>> observers = new ArrayList<>();
  @Inject
  public UserPreferences(UserDao userDao, AuthenticationHandler authHandler) {
    this.userDao = userDao;
    this.authHandler = authHandler;
  }

  public void setPreferredLanguage(String updatedLanguage) {
    String userID = authHandler.getActiveUserID();
    userDao.updatedPreferredLanguage(userID, updatedLanguage);
    notifyObservers(LANGUAGE_PROPERTIES_PATH + updatedLanguage);
  }

  public String getPreferredLanguagePath() {
    String userID = authHandler.getActiveUserID();
    preferredLanguage = (String) userDao.getUserData(userID)
        .get(UserSchema.PREFERRED_LANGUAGE.getFieldName());
    return LANGUAGE_PROPERTIES_PATH + preferredLanguage;
  }

  public void addObserver(Consumer<String> observer) {
    observers.add(observer);
  }

  public void removeObserver(Consumer<String> observer) {
    observers.remove(observer);
  }

  private void notifyObservers(String pathToLanguageBundle) {
    observers.forEach(observer -> observer.accept(pathToLanguageBundle));
  }
}