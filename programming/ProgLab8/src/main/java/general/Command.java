package general;

import server.RouteCollection;

import java.io.Serializable;
import java.util.List;

public interface Command {
    Serializable execute(List<Object> arguments, RouteCollection dataCollection, User user);
}
