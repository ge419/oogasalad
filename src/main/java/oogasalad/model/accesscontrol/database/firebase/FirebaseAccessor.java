package oogasalad.model.accesscontrol.database.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.inject.Inject;
import java.io.InputStream;


public class FirebaseAccessor {

  private final String SERVICE_ACCOUNT_CRED_PATH = "/accesscontrol/service_account.json";
  Firestore db;

  @Inject
  public FirebaseAccessor() {
    initDB();
  }

  private void initDB() {
    try {
      InputStream serviceAccount = getClass().getResourceAsStream(SERVICE_ACCOUNT_CRED_PATH);
      GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
      FirebaseOptions options = new FirebaseOptions.Builder()
          .setCredentials(credentials)
          .build();
      FirebaseApp.initializeApp(options);
      db = FirestoreClient.getFirestore();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Firestore getFirestoreClientInstance() {
    return db;
  }
}