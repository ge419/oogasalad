package oogasalad.model.attribute;

import com.google.inject.AbstractModule;

public class AttributeModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(SchemaDatabase.class).to(SimpleSchemaDatabase.class);
  }
}
