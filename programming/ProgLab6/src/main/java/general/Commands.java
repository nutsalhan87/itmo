package general;

import server.workwithexternaldata.ListRouteToFileJSON;
import general.route.Route;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Commands {
    private Commands() {
    }

    public static String help(List<Object> arguments, List<Route> data) {
        return "help: вывести справку по доступным командам\n" +
                "info: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show: вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element}: добавить новый элемент в коллекцию\n" +
                "update id: обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id: удалить элемент из коллекции по его id\n" +
                "clear: очистить коллекцию\n" +
                "execute_script file_name: считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "add_if_max {element}: добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\n" +
                "remove_greater {element}: удалить из коллекции все элементы, превышающие заданный\n" +
                "remove_lower {element}: удалить из коллекции все элементы, меньшие, чем заданный\n" +
                "remove_any_by_distance distance: удалить из коллекции один элемент, значение поля distance которого эквивалентно заданному\n" +
                "filter_contains_name name: вывести элементы, значение поля name которых содержит заданную подстроку\n" +
                "filter_starts_with_name name: вывести элементы, значение поля name которых начинается с заданной подстроки";
    }

    public static String info(List<Object> arguments, List<Route> data) {
        return "Тип коллекции: " + data.getClass().getName() + "\n" +
                "Тип данных, хранимых в коллекции: " + Route.class.getName() + "\n" +
                "Количество элементов в коллекции: " + data.size();
    }

    public static String show(List<Object> arguments, List<Route> data) {
        return data.stream().map(Route::toString).reduce((s1, s2) -> (s1.concat("\n\n").concat(s2))).orElse("");
    }

    public static String add(List<Object> arguments, List<Route> data) {
        Route newRoute = (Route)arguments.get(0);
        data.add(new Route(newRoute.getName(), newRoute.getCoordinates(), newRoute.getFrom(), newRoute.getTo(), newRoute.getDistance()));
        return "Новый экземпляр класса успешно добавлен в коллекцию";
    }

    public static String update(List<Object> arguments, List<Route> data) throws NumberFormatException {
        Route newRoute = (Route) arguments.get(0);
        List<Route> newData = data.stream().map((r) -> {
            if (r.getId().equals(Integer.decode((String) arguments.get(1)))) {
                return newRoute;
            } else
                return r;
        }).collect(Collectors.toList());
        String answer = data.equals(newData) ? "Объекта с таким id нет" : "Объект с id " + (String) arguments.get(1) + " успешно изменен";
        data.clear();
        data.addAll(newData);
        return answer;
    }

    public static String removeById(List<Object> arguments, List<Route> data) throws NumberFormatException {
        List<Route> newData = data.stream().filter((n) -> (!n.getId().equals(Integer.decode((String) arguments.get(0)))))
                .collect(Collectors.toList());
        String answer = data.equals(newData) ? "Объекта с таким id нет" : "Объект с id " + (String) arguments.get(0) + " успешно удален";
        data.clear();
        data.addAll(newData);
        return answer;
    }

    public static String clear(List<Object> arguments, List<Route> data) {
        data.clear();
        return "Коллекция успешно очищена";
    }

    public static String save(List<Object> arguments, List<Route> data) throws IOException {
        new ListRouteToFileJSON().saveInFile(data, new File("Data.json"));
        return "Коллекция сохранена в файл на сервере";
    }

    public static String exit(List<Object> arguments, List<Route> data) throws IOException {
        save(arguments, data);
        System.exit(0);
        return "";
    }

    public static String addIfMax(List<Object> arguments, List<Route> data) {
        Route toAddIfMax = (Route) arguments.get(0);

        if (data.stream().max(Route::compareTo).isPresent() &&
                toAddIfMax.compareTo(data.stream().max(Route::compareTo).get()) > 0) {
            data.add(toAddIfMax);
            return "Новый объект успешно добавлен";
        } else
            return "Новый объект не больше максимального элемента коллекции, потому не был добавлен";
    }

    public static String removeGreater(List<Object> arguments, List<Route> data) {
        Route forComparison = (Route) arguments.get(0);
        List<Route> newData = data.stream().filter((n) -> (forComparison.compareTo(n) > 0)).collect(Collectors.toList());
        data.clear();
        data.addAll(newData);
        return "Элементы коллекции, превышающие заданный, успешно удалены";
    }

    public static String removeLower(List<Object> arguments, List<Route> data) {
        Route forComparison = (Route) arguments.get(0);
        List<Route> newData = data.stream().filter((n) -> (forComparison.compareTo(n) < 0)).collect(Collectors.toList());
        data.clear();
        data.addAll(newData);
        return "Элементы коллекции, которые меньше заданного, успешно удалены";
    }

    public static String removeAnyByDistance(List<Object> arguments, List<Route> data) throws NumberFormatException {
        Predicate<Route> predicate = (n) -> (((Double) n.getDistance()).equals(Double.parseDouble((String) arguments.get(0))));
        List<Route> dataWithoutEqualDistances = data.stream()
                .filter((n) -> (!predicate.test(n))).collect(Collectors.toList());
        List<Route> dataWithEqualDistancesButWithoutFirst = data.stream()
                .filter(predicate).skip(1).collect(Collectors.toList());
        data.clear();
        data.addAll(dataWithoutEqualDistances);
        data.addAll(dataWithEqualDistancesButWithoutFirst);
        List<Route> newData = data.stream().sorted(Comparator.comparingInt(Route::getId)).collect(Collectors.toList());
        data.clear();
        data.addAll(newData);
        return "Первый встречный элемент в коллекции, " +
                "значение distance которого равно заданному, если таковой был найден, удален";
    }

    public static String filterContainsName(List<Object> arguments, List<Route> data) {
        return data.stream().filter((n) -> (n.getName().contains((String) arguments.get(0)))).map(Route::toString)
                .reduce((n1, n2) -> (n1 + "\n\n" + n2)).orElse("");
    }

    public static String filterStartsWithName(List<Object> arguments, List<Route> data) {
        return data.stream().filter((n) -> (n.getName().startsWith((String) arguments.get(0)))).map(Route::toString)
                .reduce((n1, n2) -> (n1 + "\n\n" + n2)).orElse("");
    }

    public static String doNothing(List<Object> arguments, List<Route> data) {
        return "";
    }
}
