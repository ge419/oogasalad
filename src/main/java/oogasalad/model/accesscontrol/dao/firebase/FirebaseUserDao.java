package oogasalad.model.accesscontrol.dao.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.inject.Inject;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import oogasalad.model.accesscontrol.dao.UserDao;
import oogasalad.model.accesscontrol.database.firebase.FirebaseAccessor;
import oogasalad.model.accesscontrol.database.schema.Collections;
import oogasalad.model.accesscontrol.database.schema.GameSchema;
import oogasalad.model.accesscontrol.database.schema.UserSchema;
import oogasalad.view.tabexplorer.userpreferences.Languages;
import oogasalad.view.tabexplorer.userpreferences.Theme;

public class FirebaseUserDao extends FirebaseAbstractDao implements UserDao {
  private final String USERNAME_KEY = UserSchema.USERNAME.getFieldName();
  private final String GAME_KEY = UserSchema.NAME.getFieldName();
  private final String PRONOUNS_KEY = UserSchema.PRONOUNS.getFieldName();
  private final String EMAIL_KEY = UserSchema.EMAIL.getFieldName();
  private final String NUMGAMESPLAYED_KEY = UserSchema.NUMGAMESPLAYED.getFieldName();
  private final String AGE_KEY = UserSchema.AGE.getFieldName();
  private final String PASSWORD_KEY = UserSchema.PASSWORD.getFieldName();
  private final String GAMES_KEY = UserSchema.GAMES.getFieldName();
  private final String DATE_JOINED_KEY = UserSchema.DATE_JOINED.getFieldName();
  private final String PREF_THEME_KEY = UserSchema.PREFERRED_THEME.getFieldName();
  private final String PREF_LANG_KEY = UserSchema.PREFERRED_LANGUAGE.getFieldName();

  private final String USER_COLLECTIONS = Collections.USERS.getString();
  private final String GAME_COLLECTIONS = Collections.GAMES.getString();

  @Inject
  public FirebaseUserDao(FirebaseAccessor firebaseAccessor) {
    super(firebaseAccessor);
  }
  @Override
  public String registerNewUser(String username, String password) {
    Map<String, Object> docData = getDefaultUserEntry(username, password);
    CollectionReference collection = db.collection(USER_COLLECTIONS);
    DocumentReference newDocRef;

    try {
      newDocRef = collection.add(docData).get();
      String newDocId = newDocRef.getId();
      //System.out.println("Auto-generated ID for new document: " + newDocId);
      return newDocId;
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void incrementNumberOfGamesPlayed(String userID) {
  }

  @Override
  public void setUserName(String userID, String newUsername) {
  }

  @Override
  public String getUserID(String userName) {
    String documentId;
    ApiFuture<QuerySnapshot> future = db.collection(USER_COLLECTIONS)
        .whereEqualTo(USERNAME_KEY, userName).get();
    List<QueryDocumentSnapshot> documents = null;
    try {
      documents = future.get().getDocuments();
      if (documents.size() > 1) {
        throw new RuntimeException("Username should be unique!");
      }
      if (documents.isEmpty()) {
        throw new RuntimeException("Username does not exist!");
      } else {
        documentId = documents.get(0).getId();
      }
      return documentId;
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void setUserFullName(String userID, String newUserFullName) {
    updateDocument(USER_COLLECTIONS, userID,
        createMap(GAME_KEY, newUserFullName));
  }


  @Override
  public Map<String, Object> getUserData(String userID) {
    return getDocumentData(USER_COLLECTIONS, userID);
  }

  // todo should be moved to
  public boolean isUserRegistered(String username) {
    String documentId = null;
    ApiFuture<QuerySnapshot> future = db.collection(USER_COLLECTIONS)
        .whereEqualTo(USERNAME_KEY, username).get();
    List<QueryDocumentSnapshot> documents = null;
    try {
      documents = future.get().getDocuments();
      return !documents.isEmpty();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void cloneGame(String userID, String gameID) {
    updateDocument(USER_COLLECTIONS, userID,
        createMap(GAMES_KEY, FieldValue.arrayUnion(gameID)));

    updateDocument(GAME_COLLECTIONS, gameID,
        createMap(GameSchema.SUBSCRIPTION_COUNT.getFieldName(), FieldValue.increment(1)));
  }

  @Override
  public void unCloneGame(String userID, String gameID) {

  }

  @Override
  public void deleteGame(String userID, String gameID) {

  }

  @Override
  public void updatePassword(String userID, String newPwd) {
    updateDocument(USER_COLLECTIONS, userID,
        createMap(PASSWORD_KEY, newPwd));
  }


  @Override
  public void updateEmailAddress(String userID, String email) {
    updateDocument(USER_COLLECTIONS, userID,
        createMap(EMAIL_KEY, email));
  }

  @Override
  public void updateUserPronouns(String userID, String pronouns) {
    updateDocument(USER_COLLECTIONS, userID,
        createMap(PRONOUNS_KEY, pronouns));
  }

  @Override
  public void updateAge(String userID, int age) {
    updateDocument(USER_COLLECTIONS, userID,
        createMap(AGE_KEY, age));
  }

  @Override
  public void updatePreferredTheme(String userID, String preferredTheme) {
    updateDocument(USER_COLLECTIONS, userID,
        createMap(PREF_THEME_KEY, preferredTheme));
  }

  @Override
  public void updatedPreferredLanguage(String userID, String preferredLang) {
    DocumentReference docRef = db.collection(USER_COLLECTIONS).document(userID);
    docRef.update(PREF_LANG_KEY, preferredLang);
  }
  private Map<String, Object> createMap(String key, Object value) {
    Map<String, Object> map = new HashMap<>();
    map.put(key, value);
    return map;
  }

  private Map<String, Object> getDefaultUserEntry(String username, String password){
    Map<String, Object> docData = new HashMap<>();
    docData.put(USERNAME_KEY, username);
    docData.put(GAME_KEY, "");
    docData.put(PRONOUNS_KEY, "");
    docData.put(EMAIL_KEY, "");
    docData.put(NUMGAMESPLAYED_KEY, 0);
    docData.put(AGE_KEY, 0);
    docData.put(PASSWORD_KEY, password);
    docData.put(GAMES_KEY, List.of());
    docData.put(DATE_JOINED_KEY, Timestamp.of(new Date()));
    docData.put(PREF_THEME_KEY, Theme.LIGHT.getThemeValue());
    docData.put(PREF_LANG_KEY, Languages.ENGLISH.getLocaleStr());

    return docData;
  }
}
