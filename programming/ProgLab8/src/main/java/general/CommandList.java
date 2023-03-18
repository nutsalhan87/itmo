package general;

import java.util.Arrays;
import java.util.stream.Stream;

public enum CommandList {
    HELP("help", Commands::help),
    INFO("info", Commands::info),
    SHOW("show", Commands::show),
    ADD("add", Commands::add),
    UPDATE("update", Commands::update),
    REMOVE_BY_ID("remove_by_id", Commands::removeById),
    CLEAR("clear", Commands::clear),
    EXECUTE_SCRIPT("execute_script", Commands::doNothing),
    EXIT("exit", Commands::exit),
    ADD_IF_MAX("add_if_max", Commands::addIfMax),
    REMOVE_GREATER("remove_greater", Commands::removeGreater),
    REMOVE_LOWER("remove_lower", Commands::removeLower),
    REMOVE_ANY_BY_DISTANCE("remove_any_by_distance", Commands::removeAnyByDistance),
    FILTER_CONTAINS_NAME("filter_contains_name", Commands::filterContainsName),
    FILTER_STARTS_WITH_NAME("filter_starts_with_name", Commands::filterStartsWithName),
    NO_COMMAND("no_command", Commands::doNothing),
    NEW_USER("new_user", Commands::doNothing),
    LOG_IN("log_in", Commands::doNothing),
    GET_LAST_UPDATE("", Commands::getLastUpdate),
    CHECK_ID("", Commands::checkId),
    GET_DATA("", Commands::getData);

    private final String name;
    Command command;

    CommandList(String name, Command cmd) {
        this.name = name;
        command = cmd;
    }

    public Command getExecutableCommand() {
        return command;
    }

    public static CommandList getCommandList(String commandName) {
        Stream<CommandList> stream = Arrays.stream(CommandList.class.getEnumConstants());
        return stream.filter((n) -> (commandName.equals(n.name))).findFirst().orElse(CommandList.NO_COMMAND);
    }

    @Override
    public String toString() {
        return name;
    }
}
