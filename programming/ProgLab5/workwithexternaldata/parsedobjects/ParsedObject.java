package workwithexternaldata.parsedobjects;

import java.util.List;
import java.util.Map;

/**
 * A special class whose objects store parsing data
 */

public class ParsedObject {
    protected boolean isNull;

    public ParsedObject() {
        isNull = false;
    }

    public List<ParsedObject> asList() {
        return null;
    }

    public Map<String, ParsedObject> asMap() {
        return null;
    }

    public String asString() {
        return null;
    }

    public Double asNumber() {
        return null;
    }

    public boolean isNull() {
        return isNull;
    }
}
