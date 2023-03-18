package general;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Request implements Serializable {
    private final CommandList command;
    private final List<Object> arguments;

    public Request (CommandList cmd, List<Object> args) {
        command = cmd;
        arguments = new LinkedList<>(args);
    }

    public CommandList getCommand() {
        return command;
    }

    public List<Object> getArguments() {
        return arguments;
    }
}
