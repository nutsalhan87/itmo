package workwithexternaldata.parsedobjects;

import java.util.HashMap;
import java.util.Map;

/**
 * A special class whose objects store parsing data in the form of dictionaries
 */

public class ParsedMap extends ParsedObject {
    Map<String, ParsedObject> map;

    public ParsedMap() {
        super();
        map = new HashMap<>();
    }

    public void addObject(String key, ParsedObject obj) {
        map.put(key, obj);
    }

    @Override
    public Map<String, ParsedObject> asMap() {
        return map;
    }
}
