### Attribute Overview
```
Model/
├─ Attribute/
│  ├─ IntAttribute
│  ├─ TileAttribute
│  ├─ Schema/
│  ├──── ObjectSchema
│  ├──── SchemaDatabase
│  ├─ MetaData/
│  ├──── Metadata
│  ├──── IntMetadata
│  
├─ Constructable/
│  ├─ GameConstruct
│  ├─ Player
│  ├─ Tile
├─ Engine/
...

```
`IntAttribute`, `DoubleAttribute`, `BooleanAttribute`, `TileAttribute`, `StringAttribute`, `PositionAttribute`, `TileListAttribute`


### Schema

[ex] Basic Tile Schema
```
"name": "basicTile",
"fields": [
    {
      "@class": "oogasalad.model.attribute.DoubleMetadata",
      "key": "width",
      "name": "Width",
      "description": "Width of the tile.",
      "editable": true,
      "viewable": true,
      "defaultValue": 50,
      "minValue": -2147483648,
      "maxValue": 2147483647
    },
    { ...
```
`position`, `ownStatus`, `width`, `height`, `next` attributes


### API

[ex] Object Schema class `makeAllAttributes`
```
  public List<Attribute> makeAllAttributes() {
    List<Attribute> list = new ArrayList<>();
    for (Metadata metadata : this.getAllMetadata()) {
      Attribute attr = metadata.makeAttribute();
      Objects.requireNonNull(attr);
      list.add(attr);
    }
    return list;
  }

```

```
  @JsonIgnore
  public void loadSchema(String newSchemaName) {
    if (!database.containsSchema(newSchemaName)) {
      LOGGER.error("schema does not exist {}", newSchemaName);
      throw new IllegalArgumentException("invalid schema");
    }
    this.schemaName = newSchemaName;
    ObjectSchema newSchema = database.getSchema(schemaName).get();
    
    setAllAttributes(newSchema.makeAllAttributes());
  
```

### Example Attribute
```
public class IntAttribute extends Attribute {

  private final IntegerProperty value;

  @JsonCreator
  public IntAttribute(
  @JsonProperty("key") String key,
  @JsonProperty("value") int value) {
    super(key);
    this.value = new SimpleIntegerProperty(value);
  }

  public static IntAttribute from(Attribute attr) {
    return Attribute.getAs(attr, IntAttribute.class);
  }

  public int getValue() {
    return value.get();
  }

  public void setValue(int value) {
    this.value.set(value);
  }
```