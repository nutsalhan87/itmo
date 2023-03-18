package server;

import general.Answer;
import general.Command;
import general.User;

import java.util.List;
import java.util.concurrent.Callable;

public class ResponseToRequest implements Callable<Answer> {
    private final Command command;
    List<Object> arguments;
    RouteCollection dataCollection;
    User user;
    public ResponseToRequest(Command command, List<Object> arguments, RouteCollection dataCollection, User user) {
        this.command = command;
        this.arguments = arguments;
        this.dataCollection = dataCollection;
        this.user = user;
    }

    @Override
    public Answer call() {
        return new Answer(command.execute(arguments, dataCollection, user));
    }
}
