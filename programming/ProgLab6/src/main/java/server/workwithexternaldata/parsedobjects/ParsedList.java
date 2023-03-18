package server.workwithexternaldata.parsedobjects;

import java.util.LinkedList;
import java.util.List;

/**
 * A special class whose objects store parsing data in the form of lists
 */

public class ParsedList extends ParsedObject {
    List<ParsedObject> list;

    public ParsedList() {
        super();
        list = new LinkedList<>();
    }

    public void addObject(ParsedObject obj) {
        list.add(obj);
    }

    @Override
    public List<ParsedObject> asList() {
        return list;
    }
}
