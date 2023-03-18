package general;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Request implements Serializable {
    private final CommandList command;
    private final List<Object> arguments;
    private final User user;

    public Request(CommandList cmd, List<Object> args, User user) {
        command = cmd;
        arguments = new LinkedList<>(args);
        this.user = user;
    }

    public CommandList getCommand() {
        return command;
    }

    public List<Object> getArguments() {
        return arguments;
    }

    public User getUser() {
        return user;
    }
}
