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

public class FirebaseGameDao extends FirebaseAbstractDao implements GameDao {

  @Inject
  public FirebaseGameDao(FirebaseAccessor firebaseAccessor){
    super(firebaseAccessor);
  }

  @Override
  public Map<String, Object> getGameData(String gameID) {
    return getDocumentData("games", gameID);
  }

  @Override
  public String createGame(String userID) {
    CollectionReference collection = db.collection("games");
    DocumentReference newDocRef;

    Map<String, Object> gameMetaData = new HashMap<>();

    gameMetaData.put("title", "");
    gameMetaData.put("description", "");
    gameMetaData.put("genre", "");
    gameMetaData.put("author", userID);
    gameMetaData.put("subscription_count", 1);
    gameMetaData.put("number_of_plays", 0);
    gameMetaData.put("thumbnail", userID); //simplifying assumption, stored data folder
    gameMetaData.put("date_created", Timestamp.of(new Date()));
    gameMetaData.put("game_data", "");
    gameMetaData.put("reviews", Arrays.asList());

    try {
      newDocRef = collection.add(gameMetaData).get();
      String gameID = newDocRef.getId();

      //System.out.println("Auto-generated ID for new document: " + gameID);

      DocumentReference userRef = db.collection("users").document(userID);
      userRef.update("games", FieldValue.arrayUnion(gameID));

      return gameID;
    } catch (InterruptedException e) {
      // TODO use logger and add custom exception class
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void updateGame (String gameID, Map<String, Object> gameUpdate){
    DocumentReference docRef = db.collection("games").document(gameID);
    docRef.update(gameUpdate);
  }

  @Override
  public List<String> getAllGames () {
    CollectionReference collection = db.collection("games");

// retrieve all document snapshots
    ApiFuture<QuerySnapshot> querySnapshotFuture = collection.get();
    QuerySnapshot querySnapshot = null;
    try {
      querySnapshot = querySnapshotFuture.get();
      // loop through the document snapshots and get their IDs
      List<String> documentIds = new ArrayList<>();
      for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
        documentIds.add(documentSnapshot.getId());
      }

      return documentIds;

    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public void postGameReview (String review, String gameID, String userID){
    Map<String, Object> docData = new HashMap<>();
    docData.put("author",  userID);
    docData.put("date_posted", Timestamp.of(new Date()));
    docData.put("review", review);
    // Get a reference to the document
    DocumentReference userDocRef = db.collection("games").document(gameID);
    // Execute the update
    userDocRef.update("reviews", FieldValue.arrayUnion(docData));

//      //todo add update method to Dao, might be useful for other things
//
  }
}
