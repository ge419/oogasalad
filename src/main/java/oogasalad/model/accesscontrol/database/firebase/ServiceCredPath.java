package oogasalad.model.accesscontrol.database.firebase;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Qualifier;


/**
 * This annotation is used for injecting the service credentials path dependency to the
 * {@link FirebaseAccessor}
 */
@Qualifier
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceCredPath {

}
