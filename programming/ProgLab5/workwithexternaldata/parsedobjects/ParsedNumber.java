package workwithexternaldata.parsedobjects;

/**
 * A special class whose objects store parsing data in the form of numbers
 */

public class ParsedNumber extends ParsedObject {
    Double number;

    public ParsedNumber(Double num) {
        super();
        number = num;
    }

    @Override
    public Double asNumber() {
        return number;
    }
}
