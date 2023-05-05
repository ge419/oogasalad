package oogasalad.view.tabexplorer.userpreferences;

import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import oogasalad.model.accesscontrol.authentication.AuthenticationHandler;
import oogasalad.model.accesscontrol.dao.UserDao;
import oogasalad.model.accesscontrol.database.schema.UserSchema;

/**
 * This class serves as a Publisher (based on the observer pattern) to notify subscribers when there
 * are changes made to user preferences (e.g. language, theme).
 *
 * @author cgd19
 */
public class UserPreferences {

  public static String LANGUAGE_PROPERTIES_PATH = "tabexplorer.languages.";
  private final UserDao userDao;
  private final AuthenticationHandler authHandler;
  private final List<Consumer<String>> observers = new ArrayList<>();
  private String preferredLanguage;

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