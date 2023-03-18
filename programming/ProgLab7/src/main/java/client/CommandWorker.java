package client;

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

    public static Request createRequest(String command, Input input, User user, SocketChannel socketChannel) throws WrongCommandException, IOException {
        List<String> splittedCommand = new LinkedList<>(Arrays.asList(command.split("\\s+")));
        if (command.equals("") || splittedCommand.size() == 0) {
            throw new WrongCommandException("Введена пустая строка");
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
                    throw new WrongCommandException("В коллекцию можно добавить только объект класса Route");
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
                            System.out.println("Объект с таким id недосутпен для обновления.");
                            return new Request(CommandList.NO_COMMAND, new LinkedList<>(), user);
                        }
                    } catch (NumberFormatException exn) {
                        throw new WrongCommandException("В качестве id должно быть введено целое положительное число");
                    } catch (ClassNotFoundException clexc) {
                        System.out.println(clexc.getMessage());
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
                        throw new WrongCommandException("В качестве id должно быть введено целое положительное число");
                    }
                } else
                    throw new WrongCommandException();
            case CLEAR:
                return new Request(CommandList.CLEAR, new LinkedList<>(), user);
            case EXECUTE_SCRIPT:
                if (splittedCommand.size() >= 2 && new File(splittedCommand.get(1)).exists() && new File(splittedCommand.get(1)).canRead()) {
                    execute_script(splittedCommand.get(1), user, socketChannel);
                    return new Request(CommandList.NO_COMMAND, new LinkedList<>(), user);
                } else
                    throw new WrongCommandException();
            case EXIT:
                System.out.println("Осуществлен выход из программы.");
                System.exit(0);
            case ADD_IF_MAX:
                if (splittedCommand.size() >= 2 && splittedCommand.get(1).equals("Route")) {
                    Route route = CreatingNewInstance.createNewRouteInstance(input);
                    return new Request(CommandList.ADD_IF_MAX, Arrays.asList(route), user);
                } else
                    throw new WrongCommandException("Программа поддерживает только работу с Route");
            case REMOVE_GREATER:
                if (splittedCommand.size() >= 2 && splittedCommand.get(1).equals("Route")) {
                    Route route = CreatingNewInstance.createNewRouteInstance(input);
                    return new Request(CommandList.REMOVE_GREATER, Arrays.asList(route), user);
                } else
                    throw new WrongCommandException("Программа поддерживает только работу с Route");
            case REMOVE_LOWER:
                if (splittedCommand.size() >= 2 && splittedCommand.get(1).equals("Route")) {
                    Route route = CreatingNewInstance.createNewRouteInstance(input);
                    return new Request(CommandList.REMOVE_LOWER, Arrays.asList(route), user);
                } else
                    throw new WrongCommandException("Программа поддерживает только работу с Route");
            case REMOVE_ANY_BY_DISTANCE:
                if (splittedCommand.size() >= 2) {
                    try {
                        Double.parseDouble(splittedCommand.get(1));
                        return new Request(CommandList.REMOVE_ANY_BY_DISTANCE,
                                new LinkedList<>(splittedCommand.subList(1, splittedCommand.size())), user);
                    } catch (NumberFormatException exn) {
                        throw new WrongCommandException("Введите корректную дистанцию в виде вещественного числа");
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

    private static void execute_script(String destination, User user, SocketChannel socketChannel) throws FileNotFoundException {
        Scanner fileReader = new Scanner(new FileReader(destination));
        String inputLine;
        Request request;
        while(true) {
            try {
                inputLine = fileReader.nextLine();
            } catch (NoSuchElementException nexc) {
                return;
            }
            List<String> splittedCommand = new LinkedList<>(Arrays.asList(inputLine.split("\\s+")));
            if (splittedCommand.get(0).equals("")) {
                splittedCommand.remove(0);
            }
            if(splittedCommand.get(0).equals("execute_script"))
            {
                System.out.println("Нельзя вызвать 'execute_script' внутри такой скрипта.");
                continue;
            }
            try {
                request = CommandWorker.createRequest(inputLine, fileReader::nextLine, user, socketChannel);
                if (CommandList.NO_COMMAND.equals(request.getCommand())) {
                    continue;
                }
                SendRequest.sendRequest(request, socketChannel);
                System.out.println((String)GetAnswer.getAnswer(socketChannel).getAnswer());
            } catch (WrongCommandException exc) {
                System.out.println(exc.getMessage());
            } catch (IOException ioexc) {
                System.out.println("Файл скрипта недоступен.");
            } catch (ClassNotFoundException ignored) {
                System.out.println("Такое не должно произойти.");
            }
        }
    }
}

