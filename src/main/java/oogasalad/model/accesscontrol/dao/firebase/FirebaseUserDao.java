package oogasalad.model.accesscontrol.dao.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.inject.Inject;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import oogasalad.model.accesscontrol.dao.UserDao;
import oogasalad.model.accesscontrol.database.firebase.FirebaseAccessor;

public class FirebaseUserDao extends FirebaseAbstractDao implements UserDao {
  // gets a DB
  @Inject
  public FirebaseUserDao(FirebaseAccessor firebaseAccessor) {
    super(firebaseAccessor);
  }


  @Override
  public String registerNewUser(String username, String password) {

    // todo put in different method
    Map<String, Object> docData = new HashMap<>();
    docData.put("username",  username);
    docData.put("name", "");
    docData.put("pronouns", "");
    docData.put("email", "");
    docData.put("number_of_games_played", 0);
    docData.put("age", 0);
    docData.put("password", password);
    docData.put("games", Arrays.asList());
    docData.put("date_joined", Timestamp.of(new Date()));
    docData.put("preferred_theme", "light");
    docData.put("preferred_language", "en-US");

    CollectionReference collection = db.collection("users");
    DocumentReference newDocRef ;
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
    ApiFuture<QuerySnapshot> future = db.collection("users").whereEqualTo("username", userName).get();
    List<QueryDocumentSnapshot> documents = null;
    try {
      documents = future.get().getDocuments();
      if (documents.size() > 1){
        throw new RuntimeException("Username should be unique!");
      }
      if (documents.isEmpty()){
        throw new RuntimeException("Username does not exist!");
      }else{
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
    DocumentReference docRef = db.collection("users").document(userID);
    docRef.update("name", newUserFullName);

  }


  @Override
  public Map<String, Object> getUserData(String userID) {
    return getDocumentData("users", userID);
  }

  // todo should be moved to
  public boolean isUserRegistered(String username){
    String documentId = null;
    ApiFuture<QuerySnapshot> future = db.collection("users").whereEqualTo("username", username).get();
    List<QueryDocumentSnapshot> documents = null;
    try {
      documents = future.get().getDocuments();
      if (!documents.isEmpty()) {
        return true;
      } else{
        return false;
      }
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void cloneGame(String userID, String gameID) {
    // Get a reference to the document
    DocumentReference userDocRef = db.collection("users").document(userID);
    // Execute the update
    userDocRef.update("games", FieldValue.arrayUnion(gameID));
    //todo add update method to Dao, might be useful for other things

    DocumentReference gameDocRef = db.collection("games").document(gameID);
    gameDocRef.update("subscription_count", FieldValue.increment(1));

  }

  @Override
  public void unCloneGame(String userID, String gameID) {

  }

  @Override
  public void deleteGame(String userID, String gameID) {

  }

  @Override
  public void updatePassword(String userID, String newPwd) {
    DocumentReference docRef = db.collection("users").document(userID);
    docRef.update("password", newPwd);

  }


  @Override
  public void updateEmailAddress(String userID, String email) {
    DocumentReference docRef = db.collection("users").document(userID);
    docRef.update("email", email);

  }

  @Override
  public void updateUserPronouns(String userID, String pronouns) {
    DocumentReference docRef = db.collection("users").document(userID);
    docRef.update("pronouns", pronouns);

  }


  @Override
  public void updateAge(String userID, int age) {
    DocumentReference docRef = db.collection("users").document(userID);
    docRef.update("age", age);
  }

  @Override
  public void updatePreferredTheme(String userID, String preferredTheme) {

  }

  @Override
  public void updatedPreferredLanguage(String userID, String preferredLang ) {
    DocumentReference docRef = db.collection("users").document(userID);
    docRef.update("preferred_language", preferredLang);

  }

}
