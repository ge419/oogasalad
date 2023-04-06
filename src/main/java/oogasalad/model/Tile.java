package oogasalad.model;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 */
public class Tile implements  Constructable {
    int id;
    String tileType;
    Map<String, BAttribute>  attributes;

    public Tile(int id, String tileType){
        this.id = id;
        this.tileType = tileType;
        attributes = new HashMap<String, BAttribute>();
    }

    public void setAttributes(String attribute, List<String> value) {

        this.attributes.put(

        );
    }
}
