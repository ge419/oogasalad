package oogasalad.model.accesscontrol.dao.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.inject.Inject;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import oogasalad.model.accesscontrol.dao.UserDao;
import oogasalad.model.accesscontrol.database.firebase.FirebaseAccessor;
import oogasalad.model.exception.InvalidDatabaseExecutionException;
import oogasalad.view.tabexplorer.userpreferences.Languages;
import oogasalad.view.tabexplorer.userpreferences.Theme;

public class FirebaseUserDao extends FirebaseAbstractDao implements UserDao {

  @Inject
  public FirebaseUserDao(FirebaseAccessor firebaseAccessor) {
    super(firebaseAccessor);
  }

  @Override
  public String registerNewUser(String username, String password) {
    Map<String, Object> docData = getDefaultUserEntry(username, password);
    CollectionReference collection = db.collection(USERS_COLLECTION);
    DocumentReference newDocRef;

    try {
      newDocRef = collection.add(docData).get();
      String newDocId = newDocRef.getId();
      LOG.info("Registered new user!");
      return newDocId;
    } catch (InterruptedException | ExecutionException e) {
      LOG.debug("Failed to register new user in UserDAO");
      throw new InvalidDatabaseExecutionException("Failed to register user", e);
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
    ApiFuture<QuerySnapshot> future = db.collection(USERS_COLLECTION)
        .whereEqualTo(USERNAME_KEY, userName).get();
    List<QueryDocumentSnapshot> documents = null;
    try {
      documents = future.get().getDocuments();
      if (documents.size() > 1) {
        throw new InvalidDatabaseExecutionException("Username should be unique!");
      }
      if (documents.isEmpty()) {
        throw new InvalidDatabaseExecutionException("Username does not exist!");
      } else {
        documentId = documents.get(0).getId();
      }
      return documentId;
    } catch (InterruptedException | ExecutionException e) {
      LOG.debug("Failed to get user ID in UserDAO");
      throw new InvalidDatabaseExecutionException("Failed to get user ID", e);
    }
  }

  @Override
  public void setUserFullName(String userID, String newUserFullName) {
    updateDocument(USERS_COLLECTION, userID,
        createMap(NAME_KEY, newUserFullName));
  }


  @Override
  public Map<String, Object> getUserData(String userID) {
    return getDocumentData(USERS_COLLECTION, userID);
  }

  // todo should be moved to
  public boolean isUserRegistered(String username) {
    String documentId = null;
    ApiFuture<QuerySnapshot> future = db.collection(USERS_COLLECTION)
        .whereEqualTo(USERNAME_KEY, username).get();
    List<QueryDocumentSnapshot> documents = null;
    try {
      documents = future.get().getDocuments();
      return !documents.isEmpty();
    } catch (InterruptedException | ExecutionException e) {
      throw new InvalidDatabaseExecutionException("Failed to register user", e);
    }
  }

  @Override
  public void subscribeToGame(String userID, String gameID) {
    updateDocument(USERS_COLLECTION, userID,
        createMap(GAMES_KEY, FieldValue.arrayUnion(gameID)));

    updateDocument(GAMES_COLLECTION, gameID,
        createMap(SUBSCRIPTION_COUNT_KEY, FieldValue.increment(1)));
  }

  @Override
  public void unsubscribeToGame(String userID, String gameID) {
    // remove the game from user games array
    removeGameForUser(userID, gameID);

    //decrement subscription count
    updateDocument(GAMES_COLLECTION, gameID,
        createMap(SUBSCRIPTION_COUNT_KEY, FieldValue.increment(-1)));
  }

  @Override
  public void deleteGame(String gameID) {
    // delete the game document
    deleteDocument(GAMES_COLLECTION, gameID);
    try {
      // now delete all references to this gameID from users
      for (String userID : getAllDocumentsInCollection(USERS_COLLECTION)) {
        removeGameForUser(userID, gameID);
      }
    } catch (Exception e) {
      LOG.debug("Game deletion process ran into errors");
      throw new InvalidDatabaseExecutionException("Could not delete game properly", e);
    }
  }

  @Override
  public void updatePassword(String userID, String newPwd) {
    updateDocument(USERS_COLLECTION, userID,
        createMap(PASSWORD_KEY, newPwd));
  }


  @Override
  public void updateEmailAddress(String userID, String email) {
    updateDocument(USERS_COLLECTION, userID,
        createMap(EMAIL_KEY, email));
  }

  @Override
  public void updateUserPronouns(String userID, String pronouns) {
    updateDocument(USERS_COLLECTION, userID,
        createMap(PRONOUNS_KEY, pronouns));
  }

  @Override
  public void updateAge(String userID, int age) {
    updateDocument(USERS_COLLECTION, userID,
        createMap(AGE_KEY, age));
  }

  @Override
  public void updatePreferredTheme(String userID, String preferredTheme) {
    updateDocument(USERS_COLLECTION, userID,
        createMap(PREF_THEME_KEY, preferredTheme));
  }

  @Override
  public void updatedPreferredLanguage(String userID, String preferredLang) {
    DocumentReference docRef = db.collection(USERS_COLLECTION).document(userID);
    docRef.update(PREF_LANG_KEY, preferredLang);
  }

  private Map<String, Object> getDefaultUserEntry(String username, String password) {
    Map<String, Object> docData = new HashMap<>();
    docData.put(USERNAME_KEY, username);
    docData.put(NAME_KEY, "");
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

  private void removeGameForUser(String userID, String gameID) {
    // Remove the specified value from the array field
    updateDocument(USERS_COLLECTION, userID, createMap(GAMES_KEY, FieldValue.arrayRemove(gameID)));
    LOG.info(String.format("Deleted Game with ID: %s from User with ID: %s list of games", gameID,
        userID));
  }
}


