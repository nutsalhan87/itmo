package general;

import general.route.Route;

import java.io.IOException;
import java.util.List;

public interface Command {
    String execute(List<Object> arguments, List<Route> data) throws IOException, ClassNotFoundException;
}
