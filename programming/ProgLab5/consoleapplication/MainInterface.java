package consoleapplication;

import consoleapplication.workwithroute.ListRouteToFileJSON;
import consoleapplication.workwithroute.CreatingNewInstance;
import route.Route;

import java.io.*;
import java.util.*;

/**
 * A class that implements a console interface for data management
 *
 * @author Nutsalkhan Nutsalkhanov
 * @version 1.0
 */

public class MainInterface {
    static int nestingLevel = 0;

    /**
     * Main constructor
     */
    public MainInterface() {
        nestingLevel++;
    }

    /**
     * The method launches a console interface for data management
     *
     * @param data  - collection of instances of the Route class
     * @param input - lamda-method which implements functional interface Input
     */
    public void startMainInterface(List<Route> data, Input input) {
        if (nestingLevel == 2) {
            System.out.println("Нельзя вызвать один скрипт внутри другого или даже того же скрипта");
            --nestingLevel;
            return;
        }
        String inputLine;
        while (true) {
            try {
                inputLine = input.nextLine();
                if (inputLine == null) {
                    --nestingLevel;
                    return;
                }
            } catch (IOException exio) {
                System.out.println("Такого файла нет");
                --nestingLevel;
                return;
            }

            try {
                execCommand(inputLine, data, input);
            } catch (WrongCommandException exc) {
                System.out.println(exc.getMessage());
            } catch (NumberFormatException exn) {
                System.out.println("В качестве id введите целое число");
            } catch (IOException ioexc) {
                System.out.println("Файл скрипта недоступен.");
            }
        }
    }

    /**
     * The method is responsible for processing and executing commands
     *
     * @param command - command
     * @param data    - collection of instances of the Route class
     * @param input   - lamda-method which implements functional interface Input
     * @throws WrongCommandException - an exception thrown when the command is incorrect
     * @throws NumberFormatException - exception thrown when entering an incorrect number
     * @throws IOException           - an exception thrown if there is no access to the file or there are other I/O errors
     */
    private void execCommand(String command, List<Route> data, Input input) throws WrongCommandException, NumberFormatException, IOException {
        List<String> splittedCommand = new LinkedList<>(Arrays.asList(command.split("\\s+")));
        if (command.equals("") || splittedCommand.size() == 0) {
            throw new WrongCommandException("Введена пустая строка");
        }
        if (splittedCommand.get(0).equals("")) {
            splittedCommand.remove(0);
        }

        switch (splittedCommand.get(0)) {
            case Commands.HELP:
                help();
                break;
            case Commands.INFO:
                info(data);
                break;
            case Commands.SHOW:
                show(data);
                break;
            case Commands.ADD:
                if (splittedCommand.size() >= 2 && splittedCommand.get(1).equals("Route"))
                    add(data, input);
                else
                    throw new WrongCommandException("В коллекцию можно дабовить только объект класса Route");
                break;
            case Commands.UPDATE:
                if (splittedCommand.size() >= 2)
                    update(data, splittedCommand.get(1), input);
                else
                    throw new WrongCommandException();
                break;
            case Commands.REMOVE_BY_ID:
                if (splittedCommand.size() >= 2)
                    removeById(data, splittedCommand.get(1));
                else
                    throw new WrongCommandException();
                break;
            case Commands.CLEAR:
                clear(data);
                break;
            case Commands.SAVE:
                try {
                    save(data, input);
                } catch (IOException exc) {
                    System.out.println("Файл недоступен");
                }
                break;
            case Commands.EXECUTE_SCRIPT:
                if (splittedCommand.size() >= 2)
                    executeScript(data, splittedCommand.get(1));
                else
                    throw new WrongCommandException();
                break;
            case Commands.EXIT:
                System.out.println("Осуществлен выход из программы. Все несохраненные данные утеряны.");
                System.exit(0);
            case Commands.ADD_IF_MAX:
                if (splittedCommand.size() >= 2 && splittedCommand.get(1).equals("Route"))
                    addIfMax(data, input);
                else
                    System.out.println("Программа поддерживает только работу с Route");
                break;
            case Commands.REMOVE_GREATER:
                if (splittedCommand.size() >= 2 && splittedCommand.get(1).equals("Route"))
                    removeGreater(data, input);
                else
                    System.out.println("Программа поддерживает только работу с Route");
                break;
            case Commands.REMOVE_LOWER:
                if (splittedCommand.size() >= 2 && splittedCommand.get(1).equals("Route"))
                    removeLower(data, input);
                else
                    System.out.println("Программа поддерживает только работу с Route");
                break;
            case Commands.REMOVE_ANY_BY_DISTANCE:
                if (splittedCommand.size() >= 2)
                    removeAnyByDistance(data, splittedCommand.get(1));
                else
                    throw new WrongCommandException();
                break;
            case Commands.FILTER_CONTAINS_NAME:
                if (splittedCommand.size() >= 2)
                    filterContainsName(data, splittedCommand.get(1));
                else
                    throw new WrongCommandException();
                break;
            case Commands.FILTER_STARTS_WITH_NAME:
                if (splittedCommand.size() >= 2)
                    filterStartsWithName(data, splittedCommand.get(1));
                else
                    throw new WrongCommandException();
                break;
            default:
                throw new WrongCommandException();
        }
    }

    /**
     * Выводит справку по доступным командам
     */
    private void help() {
        System.out.println("help: вывести справку по доступным командам\n" +
                "info: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show: вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element}: добавить новый элемент в коллекцию\n" +
                "update id: обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id: удалить элемент из коллекции по его id\n" +
                "clear: очистить коллекцию\n" +
                "save: сохранить коллекцию в файл\n" +
                "execute_script file_name: считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit: завершить программу (без сохранения в файл)\n" +
                "add_if_max {element}: добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\n" +
                "remove_greater {element}: удалить из коллекции все элементы, превышающие заданный\n" +
                "remove_lower {element}: удалить из коллекции все элементы, меньшие, чем заданный\n" +
                "remove_any_by_distance distance: удалить из коллекции один элемент, значение поля distance которого эквивалентно заданному\n" +
                "filter_contains_name name: вывести элементы, значение поля name которых содержит заданную подстроку\n" +
                "filter_starts_with_name name: вывести элементы, значение поля name которых начинается с заданной подстроки");
    }

    /**
     * Вывод информации о коллекции
     */
    private void info(List<Route> data) {
        System.out.println("Тип коллекции: " + data.getClass().getName() + "\n" +
                "Тип данных, хранимых в коллекции: " + Route.class.getName() + "\n" +
                "Количество элементов в коллекции: " + data.size());
    }

    /**
     * Вывод всех элементов коллекции в строковм представлении
     */
    private void show(List<Route> data) {
        for (int i = 0; i < data.size(); i++) {
            System.out.println(data.get(i) + "\n");
        }
    }

    /**
     * Добавление нового элемента в коллекцию
     */
    private void add(List<Route> data, Input input) throws IOException {
        data.add(CreatingNewInstance.createNewRouteInstance(input));
        System.out.println("Новый экземпляр класса успешно добавлен в коллекцию");
    }

    /**
     * Обновление элемента коллекции по id
     */
    private void update(List<Route> data, String id, Input input) throws NumberFormatException, IOException {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId().equals(Integer.decode(id))) {
                data.get(i).updateValues(CreatingNewInstance.createNewRouteInstance(input));
                System.out.println("Объект с id " + id + " успешно изменен");
                return;
            }
        }
        System.out.println("Объекта с таким id нет");
    }

    /**
     * Удаление элемента коллекции по id
     */
    private void removeById(List<Route> data, String id) throws NumberFormatException {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId().equals(Integer.decode(id))) {
                data.remove(i);
                System.out.println("Объект с id " + id + " успешно удален");
                return;
            }
        }
        System.out.println("Объекта с таким id нет");
    }

    /**
     * Очистка коллекции
     */
    private void clear(List<Route> data) {
        data.clear();
        System.out.println("Коллекция успешно очищена");
    }

    /**
     * Сохранение элементов коллекции в файл в виде данных в формате JSON
     */
    private void save(List<Route> data, Input input) throws IOException {
        try {
            new ListRouteToFileJSON().saveInFile(data, new File(System.getenv("Lab5Data")));
        } catch (NullPointerException | IOException exnp) {
            File path = new File("");
            do {
                System.out.println("Введите путь до директории, где будет храниться Data.json или где уже хранится таковая:");
                path = new File(input.nextLine());
                if (!path.exists()) {
                    System.out.println("Такой директории не существует");
                    continue;
                }
                path = new File(path.getAbsolutePath() + "/Data.json");
                path.createNewFile();
                if (!path.canWrite())
                    System.out.println("В данной директории невозможно произвести запись в файл");
            } while (!path.canWrite());
            new ListRouteToFileJSON().saveInFile(data, path);
        }
        System.out.println("Коллекция сохранена в файл");
    }

    /**
     * Выполнение команд из внешнего файла
     */
    private void executeScript(List<Route> data, String fileName) throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        new MainInterface().startMainInterface(data, bufferedReader::readLine);
    }

    /**
     * Добавление нового элемента в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
     */
    private void addIfMax(List<Route> data, Input input) throws IOException {
        Route toAddIfMax = CreatingNewInstance.createNewRouteInstance(input);

        if (toAddIfMax.compareTo(Collections.max(data)) > 0) {
            data.add(toAddIfMax);
            System.out.println("Новый объект успешно добавлен");
        } else
            System.out.println("Новый объект не больше максимального элемента коллекции, потому не был добавлен");
    }

    /**
     * Удаление из коллекции всех элементов, превышающих заданный
     */
    private void removeGreater(List<Route> data, Input input) throws IOException {
        Route forComparison = CreatingNewInstance.createNewRouteInstance(input);

        for (int i = data.size() - 1; i >= 0; i--) {
            if (forComparison.compareTo(data.get(i)) < 0)
                data.remove(i);
        }

        System.out.println("Элементы коллекции, превышающие заданный, успешно удалены");
    }

    /**
     * Удаление из коллекции всех элементов, которые меньше заданного
     */
    private void removeLower(List<Route> data, Input input) throws IOException {
        Route forComparison = CreatingNewInstance.createNewRouteInstance(input);

        for (int i = data.size() - 1; i >= 0; i--) {
            if (forComparison.compareTo(data.get(i)) > 0)
                data.remove(i);
        }

        System.out.println("Элементы коллекции, которые меньше заданного, успешно удалены");
    }

    /**
     * Удаление из коллекции всех элементов, значение расстояния которого равно заданному
     */
    private void removeAnyByDistance(List<Route> data, String dist) throws NumberFormatException {
        boolean isFound = false;
        for (int i = 0; i < data.size(); i++) {
            if (Math.abs(data.get(i).getDistance() - Double.parseDouble(dist)) < 0.00000001d) {
                data.remove(i);
                isFound = true;
                break;
            }
        }
        if (isFound)
            System.out.println("Первый встречный элемент в коллекции, значение distance которого равно заданному, удален");
        else
            System.out.println("Элемент, значение distance которого равно заданному, не найден");
    }

    /**
     * Вывод элементов, значение поля name которых содержит заданную подстроку
     */
    private void filterContainsName(List<Route> data, String name) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getName().contains(name))
                System.out.println(data.get(i) + "\n");
        }
    }

    /**
     * Вывод элементов, значение поля name которых начинается с заданной подстроки
     */
    private void filterStartsWithName(List<Route> data, String name) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getName().startsWith(name))
                System.out.println(data.get(i) + "\n");
        }
    }
}
