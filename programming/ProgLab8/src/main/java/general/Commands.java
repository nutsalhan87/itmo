package general;

import general.route.Route;
import server.DatabaseWorker;
import server.RouteCollection;

import java.io.Serializable;
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
        return "help";
    }

    public static String info(List<Object> arguments, RouteCollection dataCollection, User user) {
        return "Collection type: " + dataCollection.getData().getClass().getName() + "\n" +
                "Data type: " + Route.class.getName() + "\n" +
                "Number of items in the collection: " + dataCollection.getData().size();
    }

    public static String show(List<Object> arguments, RouteCollection dataCollection, User user) {
        String answer = dataCollection.getData().stream().map(Route::toString).reduce((s1, s2) -> (s1.concat("\n\n")
                .concat(s2))).orElse("");
        if (answer.endsWith("\n\n")) {
            return answer.substring(0, answer.length() - 4);
        } else {
            return answer;
        }
    }

    public static String add(List<Object> arguments, RouteCollection dataCollection, User user) {
        lock.lock();
        Route route = (Route) arguments.get(0);
        if(DatabaseWorker.addNewRoute(route, user)) {
            try {
                dataCollection.updateData(DatabaseWorker.getAllRoutes());
            } catch (SQLException ignored) {}
            lock.unlock();
            return "addSuccessful";
        }
        else {
            lock.unlock();
            return "addUnsuccessful";
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
            return "updateIdSuccessful";
        }
        lock.unlock();
        return "updateIdUnsuccessful";
    }

    public static String removeById(List<Object> arguments, RouteCollection dataCollection, User user)
            throws NumberFormatException {
        lock.lock();
        if(DatabaseWorker.removeById(Integer.decode((String)arguments.get(0)), user)) {
            try {
                dataCollection.updateData(DatabaseWorker.getAllRoutes());
            } catch (SQLException ignored) {}
            lock.unlock();
            return "removeIdSuccessful";
        }
        lock.unlock();
        return "removeUnsuccessful";
    }

    public static String clear(List<Object> arguments, RouteCollection dataCollection, User user) {
        lock.lock();
        if(DatabaseWorker.clear(user)) {
            try {
                dataCollection.updateData(DatabaseWorker.getAllRoutes());
            } catch (SQLException ignored) {}
            lock.unlock();
            return "yourDataRemoved";
        } else {
            lock.unlock();
            return "removeUnsuccessful";
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
                return "addSuccessful";
            } else {
                lock.unlock();
                return "addUnsuccessful";
            }
        } else {
            lock.unlock();
            return "isNotMax";
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
        return "removedGreater";
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
        return "removedLower";
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
                return "removeDistanceSuccessful";
            } else {
                lock.unlock();
                return "removeUnsuccessful";
            }
        } else {
            lock.unlock();
            return "elementNotFound";
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

    public static Serializable getData(List<Object> arguments, RouteCollection dataCollection, User user) {
        return (Serializable) dataCollection.getData();
    }

    public static Serializable getLastUpdate(List<Object> arguments, RouteCollection dataCollection, User user) {
        return dataCollection.getLastUpdate();
    }
}
