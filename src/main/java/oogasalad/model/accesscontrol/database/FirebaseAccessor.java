package oogasalad.model.accesscontrol.database;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.inject.Inject;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


public class FirebaseAccessor implements DatabaseAccessor{
  Firestore db;
  private final String SERVICE_ACCOUNT_CRED_PATH = "/accesscontrol/service_account.json";
  @Inject
  public FirebaseAccessor(){
    initDB();
  }
  private void initDB(){
    try{
      InputStream serviceAccount = getClass().getResourceAsStream(SERVICE_ACCOUNT_CRED_PATH);
      System.out.println(serviceAccount);
      GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
      FirebaseOptions options = new FirebaseOptions.Builder()
          .setCredentials(credentials)
          .build();
      FirebaseApp.initializeApp(options);
      db = FirestoreClient.getFirestore();

    }catch (Exception e){
      e.printStackTrace();
    }
  }
  @Override
  public void modifyUser(Map<String, Object> userData) {
    //todo use properties file
    String username = (String) userData.get("username");
    db.collection("users").document(username).set(userData);
  }

  public boolean isUserRegistered(String userID){
    DocumentReference docRef = db.collection("users").document(userID);
    ApiFuture<DocumentSnapshot> future = docRef.get();
    try{
      DocumentSnapshot document = future.get();
      if (document.exists()) {
        return true;
      } else {
        return false;
      }
    }catch (Exception e){
      e.printStackTrace();
      return false;
    }
  }
  @Override
  public void createGame(String userID, Map<String, Object> game) {
    DocumentReference userRef = db.collection("users").document(userID);
    ApiFuture<WriteResult> arrayUnion =
        userRef.update("games", FieldValue.arrayUnion(game));
  }
  @Override
  public void editGame(String gameID, Map<String, Object> game) {

  }
  @Override
  public List<Map<String, Object>> getGamesForUser(String userID) {
    DocumentReference userRef = db.collection("users").document(userID);
    ApiFuture<DocumentSnapshot> future = userRef.get();
    try{
      DocumentSnapshot document = future.get();
      List<Map<String, Object>> groups = (List<Map<String, Object>>) document.get("games");
//      System.out.println(groups.get(0).get("name"));
      return groups;
    }catch (Exception e){
      e.printStackTrace();
    }
    return null;
  }
  @Override
  public List<Map<String, Object>> GetAllGames(String userID) {
    return null;
  }
  @Override
  public List<Map<String, Object>> getGameReviewS(String gameID) {
    return null;
  }
  @Override
  public void postGameReview(String review, String gameID, String userID) {

  }
  @Override
  public void cloneGame(String gameID, String userID) {

  }
}