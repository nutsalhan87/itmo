package workwithexternaldata.parsedobjects;

/**
 * A special class whose objects store parsing data in the form of null
 */

public class ParsedNull extends ParsedObject {
    public ParsedNull() {
        super();
        isNull = true;
    }
}
