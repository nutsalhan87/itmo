package general;

import general.route.Route;
import server.DatabaseWorker;
import server.RouteCollection;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class Commands {
    private static final ReentrantLock lock;
    static {
        lock = new ReentrantLock();
    }

    private Commands() {
    }

    public static String help(List<Object> arguments, RouteCollection dataCollection, User user) {
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

    public static String info(List<Object> arguments, RouteCollection dataCollection, User user) {
        return "Тип коллекции: " + dataCollection.getData().getClass().getName() + "\n" +
                "Тип данных, хранимых в коллекции: " + Route.class.getName() + "\n" +
                "Количество элементов в коллекции: " + dataCollection.getData().size();
    }

    public static String show(List<Object> arguments, RouteCollection dataCollection, User user) {
        return dataCollection.getData().stream().map(Route::toString).reduce((s1, s2) -> (s1.concat("\n\n")
                .concat(s2))).orElse("");
    }

    public static String add(List<Object> arguments, RouteCollection dataCollection, User user) {
        lock.lock();
        Route route = (Route) arguments.get(0);
        if(DatabaseWorker.addNewRoute(route, user)) {
            try {
                dataCollection.updateData(DatabaseWorker.getAllRoutes());
            } catch (SQLException ignored) {}
            lock.unlock();
            return "Новый экземпляр класса успешно добавлен в коллекцию";
        }
        else {
            lock.unlock();
            return "Экземпляр класса не удалось добавить в базу данных";
        }
    }

    public static String update(List<Object> arguments, RouteCollection dataCollection, User user)
            throws NumberFormatException {
        lock.lock();
        if (DatabaseWorker.updateById((Route)arguments.get(0), (Integer)arguments.get(1), user)) {
            try {
                dataCollection.updateData(DatabaseWorker.getAllRoutes());
            } catch (SQLException ignored) {}
            lock.unlock();
            return "Объект с таким id успешно обновлен.";
        }
        lock.unlock();
        return "Обновления не произошло.";
    }

    public static String removeById(List<Object> arguments, RouteCollection dataCollection, User user)
            throws NumberFormatException {
        lock.lock();
        if(DatabaseWorker.removeById(Integer.decode((String)arguments.get(0)), user)) {
            try {
                dataCollection.updateData(DatabaseWorker.getAllRoutes());
            } catch (SQLException ignored) {}
            lock.unlock();
            return "Объект с таким id, если таковый был, успешно удален.";
        }
        lock.unlock();
        return "Удаления не произошло.";
    }

    public static String clear(List<Object> arguments, RouteCollection dataCollection, User user) {
        lock.lock();
        if(DatabaseWorker.clear(user)) {
            try {
                dataCollection.updateData(DatabaseWorker.getAllRoutes());
            } catch (SQLException ignored) {}
            lock.unlock();
            return "Ваши данные, если таковые были, удалены из коллекции";
        } else {
            lock.unlock();
            return "Ваши данные удалить не вышло";
        }
    }

    public static String exit(List<Object> arguments, RouteCollection dataCollection, User user) {
        System.exit(0);
        return "";
    }

    public static String addIfMax(List<Object> arguments, RouteCollection dataCollection, User user) {
        Route toAddIfMax = (Route) arguments.get(0);
        lock.lock();
        if (dataCollection.getData().stream().max(Route::compareTo).isPresent() &&
                toAddIfMax.compareTo(dataCollection.getData().stream().max(Route::compareTo).get()) > 0) {
            if (DatabaseWorker.addNewRoute(toAddIfMax, user)) {
                try {
                    dataCollection.updateData(DatabaseWorker.getAllRoutes());
                } catch (SQLException ignored) {}
                lock.unlock();
                return "Новый объект успешно добавлен.";
            } else {
                lock.unlock();
                return "Новый объект добавить не вышло.";
            }
        } else {
            lock.unlock();
            return "Новый объект не больше максимального элемента коллекции, потому не был добавлен";
        }
    }

    public static String removeGreater(List<Object> arguments, RouteCollection dataCollection, User user) {
        Route forComparison = (Route) arguments.get(0);
        List<Route> newData = dataCollection.getData().stream().filter((n) -> (forComparison.compareTo(n) > 0))
                .collect(Collectors.toList());
        lock.lock();
        for (int i = 0; i < newData.size(); ++i) {
            if (DatabaseWorker.removeById(newData.get(i).getId(), user)) {
                try {
                    dataCollection.updateData(DatabaseWorker.getAllRoutes());
                } catch (SQLException ignored) {}
            }
        }
        lock.unlock();
        return "Элементы коллекции, превышающие заданный, успешно удалены";
    }

    public static String removeLower(List<Object> arguments, RouteCollection dataCollection, User user) {
        Route forComparison = (Route) arguments.get(0);
        List<Route> newData = dataCollection.getData().stream().filter((n) -> (forComparison.compareTo(n) < 0))
                .collect(Collectors.toList());
        lock.lock();
        for (int i = 0; i < newData.size(); ++i) {
            if (DatabaseWorker.removeById(newData.get(i).getId(), user)) {
                try {
                    dataCollection.updateData(DatabaseWorker.getAllRoutes());
                } catch (SQLException ignored) {}
            }
        }
        lock.unlock();
        return "Элементы коллекции, которые меньше заданного, успешно удалены";
    }

    public static String removeAnyByDistance(List<Object> arguments, RouteCollection dataCollection, User user)
            throws NumberFormatException {
        Double distance = Double.parseDouble((String) arguments.get(0));
        Route toRemove = dataCollection.getData().stream().filter((n) -> (distance.equals(n.getDistance()))).findAny()
                .orElse(null);
        lock.lock();
        if(toRemove != null) {
            if (DatabaseWorker.removeById(toRemove.getId(), user)) {
                try {
                    dataCollection.updateData(DatabaseWorker.getAllRoutes());
                } catch (SQLException ignored) {}
                lock.unlock();
                return "Первый встречный элемент в коллекции, " +
                        "значение distance которого равно заданному, если таковой был найден, удален.";
            } else {
                lock.unlock();
                return "Удаления не призошло.";
            }
        } else {
            lock.unlock();
            return "Такого элемента не было найдено.";
        }
    }

    public static String filterContainsName(List<Object> arguments, RouteCollection dataCollection, User user) {
        return dataCollection.getData().stream().filter((n) -> ((n).getName().contains((String) arguments.get(0))))
                .map(Route::toString).reduce((n1, n2) -> (n1 + "\n\n" + n2)).orElse("");
    }

    public static String filterStartsWithName(List<Object> arguments, RouteCollection dataCollection, User user) {
        return dataCollection.getData().stream().filter((n) -> ((n).getName().startsWith((String) arguments.get(0))))
                .map(Route::toString).reduce((n1, n2) -> (n1 + "\n\n" + n2)).orElse("");
    }

    public static String doNothing(List<Object> arguments, RouteCollection dataCollection, User user) {
        return "";
    }

    public static String checkId(List<Object> arguments, RouteCollection dataCollection, User user) {
        long count = dataCollection.getData().stream()
                .filter(n -> n.getId().equals(arguments.get(0)) && n.getOwner().equals(user.getUser())).count();
        return count == 0 ? "FALSE" : "TRUE";
    }
}
