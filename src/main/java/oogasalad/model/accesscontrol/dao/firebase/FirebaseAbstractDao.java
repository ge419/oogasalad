package oogasalad.model.accesscontrol.dao.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.inject.Inject;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import oogasalad.model.accesscontrol.database.FirebaseAccessor;

public abstract class FirebaseAbstractDao {

  protected final FirebaseAccessor firebaseAccessor;
  protected final Firestore db;

  @Inject
  public FirebaseAbstractDao(FirebaseAccessor firebaseAccessor){
    this.firebaseAccessor = firebaseAccessor;
    this.db = firebaseAccessor.getFirestoreClientInstance();
  }

  public Map<String, Object> getDocumentData(String collection, String documentID){
    Firestore db = FirestoreClient.getFirestore();
    DocumentReference docRef = db.collection(collection).document(documentID);

    ApiFuture<DocumentSnapshot> future = docRef.get();
    DocumentSnapshot document = null;
    try {
      document = future.get();
      if (document.exists()) {
        Map<String, Object> data = document.getData();
        return data;
        // Do something with the data
      } else {
        // Document doesn't exist
        // todo prob throw exception tryna get data from sumn that dont exist
        throw new RuntimeException("document don't exist");
      }
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
  }


  // todo add generic method to  update document


  // todo add generic method to delete document
}
