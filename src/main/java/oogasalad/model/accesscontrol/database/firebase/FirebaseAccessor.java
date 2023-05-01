package oogasalad.model.accesscontrol.database.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.inject.Inject;
import java.io.InputStream;

/**
 * This class provides an interface to the Firebase database using the Firestore client. The
 * FirebaseAccessor class initializes the database client using a service account credential file,
 * which is loaded from the specified file path.
 *
 * @author cgd19
 */
public class FirebaseAccessor {

  private final String serviceCredPath;
  Firestore db;

  @Inject
  public FirebaseAccessor(@ServiceCredPath String serviceCredPath) {
    this.serviceCredPath = serviceCredPath;
    initDB();
  }

  private void initDB() {
    try {
      InputStream serviceAccount = getClass().getResourceAsStream(serviceCredPath);
      //System.out.println(serviceAccount);
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