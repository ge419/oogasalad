package oogasalad.model.accesscontrol.dao.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import oogasalad.model.accesscontrol.dao.GameDao;
import oogasalad.model.accesscontrol.database.firebase.FirebaseAccessor;
import oogasalad.model.exception.InvalidDatabaseExecutionException;

public class FirebaseGameDao extends FirebaseAbstractDao implements GameDao {

  @Inject
  public FirebaseGameDao(FirebaseAccessor firebaseAccessor) {
    super(firebaseAccessor);
  }

  @Override
  public Map<String, Object> getGameData(String gameID) {
    return getDocumentData("games", gameID);
  }

  @Override
  public String createGame(String userID) {
    CollectionReference collection = db.collection(GAMES_COLLECTION);
    DocumentReference newDocRef;

    Map<String, Object> gameMetaData = getDefaultGameEntry(userID);

    try {
      newDocRef = collection.add(gameMetaData).get();
      String gameID = newDocRef.getId();
      DocumentReference userRef = db.collection(USERS_COLLECTION).document(userID);
      userRef.update(GAMES_KEY, FieldValue.arrayUnion(gameID));
      return gameID;
    } catch (InterruptedException | ExecutionException e) {
      LOG.debug("failed to create game in createGame method");
      throw new InvalidDatabaseExecutionException("Failed to create game!", e);
    }
  }

  @Override
  public void updateGame(String gameID, Map<String, Object> gameUpdate) {
    updateDocument(GAMES_COLLECTION, gameID, gameUpdate);
  }

  @Override
  public List<String> getAllGames() {
    return getAllDocumentsInCollection(GAMES_COLLECTION);
  }

  @Override
  public void postGameReview(String review, String gameID, String userID) {
    Map<String, Object> docData = new HashMap<>();
    docData.put(REVIEW_AUTHOR_KEY, userID);
    docData.put(REVIEW_DATE_POSTED_KEY, Timestamp.of(new Date()));
    docData.put(REVIEW_KEY, review);

    updateDocument(GAMES_COLLECTION, gameID,
        createMap(GAME_REVIEWS_KEY, FieldValue.arrayUnion(docData)));
  }

  private Map<String, Object> getDefaultGameEntry(String userID) {
    Map<String, Object> gameMetaData = new HashMap<>();
    gameMetaData.put(TITLE_KEY, "");
    gameMetaData.put(DESCRIPTION_KEY, "");
    gameMetaData.put(GENRE_KEY, "");
    gameMetaData.put(GAME_AUTHOR_KEY, userID);
    gameMetaData.put(SUBSCRIPTION_COUNT_KEY, 1);
    gameMetaData.put(NUMBER_OF_PLAYS_KEY, 0);
    gameMetaData.put(THUMBNAIL_KEY, userID); //simplifying assumption, stored data folder
    gameMetaData.put(DATE_CREATED_KEY, Timestamp.of(new Date()));
    gameMetaData.put(GAME_DATA_KEY, "");
    gameMetaData.put(GAME_REVIEWS_KEY, Arrays.asList());

    return gameMetaData;
  }
}
