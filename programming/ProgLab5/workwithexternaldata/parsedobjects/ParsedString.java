package workwithexternaldata.parsedobjects;

/**
 * A special class whose objects store parsing data in the form of strings
 */

public class ParsedString extends ParsedObject {
    String string;

    public ParsedString(String str) {
        super();
        string = str;
    }

    @Override
    public String asString() {
        return string;
    }
}
