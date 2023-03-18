package client;

import client.locales.LocaleManager;
import client.workwithroute.CreatingNewInstance;
import general.CommandList;
import general.Request;
import general.User;
import general.route.Route;

import java.io.*;
import java.nio.channels.SocketChannel;
import java.util.*;

public class CommandWorker {
    private CommandWorker() {}

    public static String executeScript(String destination, User user, SocketChannel socketChannel) throws FileNotFoundException {
        Scanner fileReader = new Scanner(new FileReader(destination));
        String inputLine;
        Request request;
        StringBuilder answer = new StringBuilder();
        while(true) {
            try {
                inputLine = fileReader.nextLine();
            } catch (NoSuchElementException nexc) {
                break;
            }
            List<String> splittedCommand = new LinkedList<>(Arrays.asList(inputLine.split("\\s+")));
            if (splittedCommand.get(0).equals("")) {
                splittedCommand.remove(0);
            }
            if(splittedCommand.get(0).equals("execute_script"))
            {
                answer.append(LocaleManager.getString("noScriptInScript")).append("\n\n");
                continue;
            }
            try {
                request = CommandWorker.createRequest(inputLine, fileReader::nextLine, user, socketChannel);
                if (CommandList.NO_COMMAND.equals(request.getCommand())) {
                    continue;
                }
                SendRequest.sendRequest(request, socketChannel);
                answer.append(LocaleManager.getString((String)GetAnswer.getAnswer(socketChannel).getAnswer())).append("\n\n");
            } catch (WrongCommandException exc) {
                answer.append(LocaleManager.getString(exc.getMessage())).append("\n\n");
            } catch (IOException ioexc) {
                answer.append(LocaleManager.getString("noFileScript")).append("\n\n");
            }
        }

        return answer.toString();
    }

    private static Request createRequest(String command, Input input, User user, SocketChannel socketChannel)
            throws WrongCommandException, IOException {
        List<String> splittedCommand = new LinkedList<>(Arrays.asList(command.split("\\s+")));
        if (command.equals("") || splittedCommand.size() == 0) {
            throw new WrongCommandException("emptyInput");
        }
        if (splittedCommand.get(0).equals("")) {
            splittedCommand.remove(0);
        }

        switch (CommandList.getCommandList(splittedCommand.get(0))) {
            case HELP:
                return new Request(CommandList.HELP, new LinkedList<>(), user);
            case INFO:
                return new Request(CommandList.INFO, new LinkedList<>(), user);
            case SHOW:
                return new Request(CommandList.SHOW, new LinkedList<>(), user);
            case ADD:
                if (splittedCommand.size() >= 2 && splittedCommand.get(1).equals("Route")) {
                    Route route = CreatingNewInstance.createNewRouteInstance(input);
                    return new Request(CommandList.ADD, Arrays.asList(route), user);
                } else
                    throw new WrongCommandException("onlyRoute");
            case UPDATE:
                if (splittedCommand.size() >= 2) {
                    try {
                        Integer id = Integer.parseUnsignedInt(splittedCommand.get(1));
                        Request requestCheckingId = new Request(CommandList.CHECK_ID, Collections.singletonList(id),
                                user);
                        SendRequest.sendRequest(requestCheckingId, socketChannel);
                        String isIdAvaliable = (String) GetAnswer.getAnswer(socketChannel).getAnswer();
                        List<Object> objList = new LinkedList<>();
                        if (isIdAvaliable.equals("TRUE"))
                        {
                            objList.add(CreatingNewInstance.createNewRouteInstance(input));
                            objList.add(id);
                            return new Request(CommandList.UPDATE, objList, user);
                        }
                        else {
                            throw new WrongCommandException("unavailableId");
                        }
                    } catch (NumberFormatException exn) {
                        throw new WrongCommandException("onlyPositiveInt");
                    }
                } else
                    throw new WrongCommandException();
            case REMOVE_BY_ID:
                if (splittedCommand.size() >= 2) {
                    try {
                        Integer.parseUnsignedInt(splittedCommand.get(1));
                        return new Request(CommandList.REMOVE_BY_ID,
                                new LinkedList<>(splittedCommand.subList(1, splittedCommand.size())), user);
                    } catch (NumberFormatException exn) {
                        throw new WrongCommandException("onlyPositiveInt");
                    }
                } else
                    throw new WrongCommandException();
            case CLEAR:
                return new Request(CommandList.CLEAR, new LinkedList<>(), user);
            case EXECUTE_SCRIPT:
                if (splittedCommand.size() >= 2 && new File(splittedCommand.get(1)).exists() && new File(splittedCommand.get(1)).canRead()) {
                    executeScript(splittedCommand.get(1), user, socketChannel);
                    return new Request(CommandList.NO_COMMAND, new LinkedList<>(), user);
                } else
                    throw new WrongCommandException();
            case EXIT:
                throw new WrongCommandException("noExitInScript");
            case ADD_IF_MAX:
                if (splittedCommand.size() >= 2 && splittedCommand.get(1).equals("Route")) {
                    Route route = CreatingNewInstance.createNewRouteInstance(input);
                    return new Request(CommandList.ADD_IF_MAX, Arrays.asList(route), user);
                } else
                    throw new WrongCommandException("onlyRoute");
            case REMOVE_GREATER:
                if (splittedCommand.size() >= 2 && splittedCommand.get(1).equals("Route")) {
                    Route route = CreatingNewInstance.createNewRouteInstance(input);
                    return new Request(CommandList.REMOVE_GREATER, Arrays.asList(route), user);
                } else
                    throw new WrongCommandException("onlyRoute");
            case REMOVE_LOWER:
                if (splittedCommand.size() >= 2 && splittedCommand.get(1).equals("Route")) {
                    Route route = CreatingNewInstance.createNewRouteInstance(input);
                    return new Request(CommandList.REMOVE_LOWER, Arrays.asList(route), user);
                } else
                    throw new WrongCommandException("onlyRoute");
            case REMOVE_ANY_BY_DISTANCE:
                if (splittedCommand.size() >= 2) {
                    try {
                        Double.parseDouble(splittedCommand.get(1));
                        return new Request(CommandList.REMOVE_ANY_BY_DISTANCE,
                                new LinkedList<>(splittedCommand.subList(1, splittedCommand.size())), user);
                    } catch (NumberFormatException exn) {
                        throw new WrongCommandException("correctDistance");
                    }
                } else
                    throw new WrongCommandException();
            case FILTER_CONTAINS_NAME:
                if (splittedCommand.size() >= 2)
                    return new Request(CommandList.FILTER_CONTAINS_NAME,
                            new LinkedList<>(splittedCommand.subList(1, splittedCommand.size())), user);
                else
                    throw new WrongCommandException();
            case FILTER_STARTS_WITH_NAME:
                if (splittedCommand.size() >= 2)
                    return new Request(CommandList.FILTER_STARTS_WITH_NAME,
                            new LinkedList<>(splittedCommand.subList(1, splittedCommand.size())), user);
                else
                    throw new WrongCommandException();
            default:
                throw new WrongCommandException();
        }
    }
}

