package oogasalad.model.accesscontrol.dao.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import oogasalad.model.accesscontrol.database.firebase.FirebaseAccessor;
import oogasalad.model.accesscontrol.database.schema.Collections;
import oogasalad.model.accesscontrol.database.schema.GameSchema;
import oogasalad.model.accesscontrol.database.schema.ReviewSchema;
import oogasalad.model.accesscontrol.database.schema.UserSchema;
import oogasalad.model.exception.InvalidDatabaseExecutionException;
import oogasalad.view.builder.BuilderView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class FirebaseAbstractDao {

  protected final FirebaseAccessor firebaseAccessor;
  protected static final Logger LOG = LogManager.getLogger(FirebaseAbstractDao.class);
  protected final Firestore db;
  protected final String USERS_COLLECTION = Collections.USERS.getString();
  protected final String GAMES_COLLECTION = Collections.GAMES.getString();

  //UserSchema
  protected final String USERNAME_KEY = UserSchema.USERNAME.getFieldName();
  protected final String NAME_KEY = UserSchema.NAME.getFieldName();
  protected final String PRONOUNS_KEY = UserSchema.PRONOUNS.getFieldName();
  protected final String EMAIL_KEY = UserSchema.EMAIL.getFieldName();
  protected final String NUMGAMESPLAYED_KEY = UserSchema.NUMGAMESPLAYED.getFieldName();
  protected final String AGE_KEY = UserSchema.AGE.getFieldName();
  protected final String PASSWORD_KEY = UserSchema.PASSWORD.getFieldName();
  protected final String GAMES_KEY = UserSchema.GAMES.getFieldName();
  protected final String DATE_JOINED_KEY = UserSchema.DATE_JOINED.getFieldName();
  protected final String PREF_THEME_KEY = UserSchema.PREFERRED_THEME.getFieldName();
  protected final String PREF_LANG_KEY = UserSchema.PREFERRED_LANGUAGE.getFieldName();

  //GameSchema
  protected final String GAME_AUTHOR_KEY = GameSchema.AUTHOR.getFieldName();
  protected final String DATE_CREATED_KEY = GameSchema.DATE_CREATED.getFieldName();
  protected final String DESCRIPTION_KEY = GameSchema.DESCRIPTION.getFieldName();
  protected final String GAME_DATA_KEY = GameSchema.GAME_DATA.getFieldName();
  protected final String GENRE_KEY = GameSchema.GENRE.getFieldName();
  protected final String NUMBER_OF_PLAYS_KEY = GameSchema.NUMBER_OF_PLAYS.getFieldName();
  protected final String GAME_REVIEWS_KEY = GameSchema.REVIEWS.getFieldName();
  protected final String SUBSCRIPTION_COUNT_KEY = GameSchema.SUBSCRIPTION_COUNT.getFieldName();
  protected final String THUMBNAIL_KEY = GameSchema.THUMBNAIL.getFieldName();
  protected final String TITLE_KEY = GameSchema.TITLE.getFieldName();

  //ReviewsSchema
  protected final String REVIEW_AUTHOR_KEY = ReviewSchema.AUTHOR.getFieldName();
  protected final String REVIEW_DATE_POSTED_KEY = ReviewSchema.DATE_POSTED.getFieldName();
  protected final String REVIEW_KEY = ReviewSchema.REVIEW.getFieldName();

  @Inject
  public FirebaseAbstractDao(FirebaseAccessor firebaseAccessor) {
    this.firebaseAccessor = firebaseAccessor;
    this.db = firebaseAccessor.getFirestoreClientInstance();
  }

  protected Map<String, Object> getDocumentData(String collection, String documentID) {
    DocumentReference docRef = db.collection(collection).document(documentID);
    ApiFuture<DocumentSnapshot> future = docRef.get();

    DocumentSnapshot document;
    try {
      document = future.get();
      if (document.exists()) {
        Map<String, Object> data = document.getData();
        return data;
        // Do something with the data
      } else {
        LOG.debug(String.format(
            "Document with id %s does not exist.", document));
        throw new InvalidDatabaseExecutionException("document doesn't exist");
      }
    } catch (InterruptedException | ExecutionException e) {
      LOG.debug(String.format(
          "Failed to get document for %s collection with %s id", collection, documentID));
      throw new InvalidDatabaseExecutionException("Failed to get document", e);
    }
  }

  protected void updateDocument(String collection, String documentID,
      Map<String, Object> updatedData) {
    DocumentReference docRef = db.collection(collection).document(documentID);
    docRef.update(updatedData);
  }

  protected Map<String, Object> createMap(String key, Object value) {
    Map<String, Object> map = new HashMap<>();
    map.put(key, value);
    return map;
  }

  protected void deleteDocument(String collection, String documentID) {
    DocumentReference docRef = db.collection(collection).document(documentID);
    docRef.delete();
  }

  protected List<String> getAllDocumentsInCollection(String collectionID) {
    CollectionReference collection = db.collection(collectionID);

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
}
