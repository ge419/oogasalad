package oogasalad.view.builder.customTile;

import oogasalad.model.attribute.SchemaDatabase;

public interface CustomObjectFactory {
    CustomObject create(SchemaDatabase schemaDatabase);
}
