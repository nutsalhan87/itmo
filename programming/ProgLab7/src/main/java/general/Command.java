package general;

import server.RouteCollection;

import java.util.List;

public interface Command {
    String execute(List<Object> arguments, RouteCollection dataCollection, User user);
}
