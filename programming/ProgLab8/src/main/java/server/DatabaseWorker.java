package server;

import general.User;
import general.route.Coordinates;
import general.route.Route;
import general.route.location.first.Location;

import java.io.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DatabaseWorker {
    private static Connection connection;
    private static final org.apache.logging.log4j.Logger logger;
    static {
        logger = org.apache.logging.log4j.LogManager.getLogger();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("database.txt"))) {
            String url = bufferedReader.readLine();
            String user = bufferedReader.readLine();
            String password = bufferedReader.readLine();
            connection = DriverManager.getConnection(url, user, password);
        } catch (IOException | SQLException exc) {
            logger.error(exc.getMessage());
            logger.error("Невозможно подключиться к базе данных. Без нее серверу капут.");
            System.exit(-1);
        }
    }

    private DatabaseWorker() {}

    public static List<Route> getAllRoutes() throws SQLException {
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM routes");

        return getRoutes(resultSet);
    }

    private static List<Route> getRoutes(ResultSet resultSet) throws SQLException {
        List<Route> data = new LinkedList<>();
        while (resultSet.next()) {
            data.add(new Route(
                    resultSet.getString("name"), resultSet.getDate("creation_date"),
                    new Coordinates(resultSet.getDouble("c_x"), resultSet.getInt("c_y")),
                    new Location(resultSet.getDouble("from_x"),
                            resultSet.getLong("from_y"), resultSet.getLong("from_z"),
                            resultSet.getString("from_name")),
                    new general.route.location.second.Location(resultSet.getInt("to_x"),
                            resultSet.getInt("to_y"), resultSet.getLong("to_z")),
                    resultSet.getDouble("distance"), resultSet.getInt("id"),
                    resultSet.getString("owner")
            ));
        }
        return  data;
    }

    public static boolean addNewRoute(Route route, User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO routes " +
                    "values((select nextval('serial')), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            statement.setString(1, route.getName());
            statement.setDouble(2, route.getCoordinates().getX());
            statement.setInt(3, route.getCoordinates().getY());
            statement.setTimestamp(4, new Timestamp(route.getCreationDate().getTime()));
            statement.setDouble(5,  route.getFrom().getX());
            statement.setLong(6, route.getFrom().getY());
            statement.setDouble(7,  route.getFrom().getZ());
            statement.setString(8,  route.getFrom().getName());
            statement.setInt(9, route.getTo().getX());
            statement.setInt(10, route.getTo().getY());
            statement.setFloat(11, route.getTo().getZ());
            statement.setDouble(12, route.getDistance());
            statement.setString(13, user.getUser());

            statement.executeUpdate();
            return true;
        } catch (SQLException sqlException) {
            logger.warn(sqlException.getMessage());
            return false;
        }
    }

    public static boolean updateById(Route route, Integer id, User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE routes SET " +
                    "name=?, c_x=?, c_y=?, creation_date=?, from_x=?, from_y=?, from_z=?,from_name=?, to_x=?, to_y=?," +
                    "to_z=?, distance=? WHERE ? IN (SELECT postgres.public.userdata.password FROM postgres.public.userdata " +
                    "WHERE owner=?) AND id=? AND owner=?;");

            statement.setString(1, route.getName());
            statement.setDouble(2, route.getCoordinates().getX());
            statement.setInt(3, route.getCoordinates().getY());
            statement.setTimestamp(4, new Timestamp(route.getCreationDate().getTime()));
            statement.setDouble(5, route.getFrom().getX());
            statement.setLong(6, route.getFrom().getY());
            statement.setDouble(7, route.getFrom().getZ());
            statement.setString(8, route.getFrom().getName());
            statement.setInt(9, route.getTo().getX());
            statement.setInt(10, route.getTo().getY());
            statement.setFloat(11, route.getTo().getZ());
            statement.setDouble(12, route.getDistance());
            statement.setString(13, Account.hash256(user.getPassword()));
            statement.setString(14, user.getUser());
            statement.setInt(15, id);
            statement.setString(16, user.getUser());

            statement.executeUpdate();

            return statement.getUpdateCount() != 0;
        } catch (SQLException sqlException) {
            logger.warn(sqlException.getMessage());
            return false;
        }
    }

    public static boolean removeById(Integer id, User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM routes WHERE " +
                    "id=? AND ? IN (SELECT userdata.password FROM userdata WHERE owner=?) AND owner=?;");

            statement.setInt(1, id);
            statement.setString(2, Account.hash256(user.getPassword()));
            statement.setString(3, user.getUser());
            statement.setString(4, user.getUser());

            statement.executeUpdate();

            return true;
        } catch (SQLException sqlException) {
            logger.warn(sqlException.getMessage());
            return false;
        }
    }

    public static boolean clear(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM routes WHERE ? " +
                    "IN (SELECT userdata.password FROM userdata WHERE owner=?) AND owner=?;");

            statement.setString(1, Account.hash256(user.getPassword()));
            statement.setString(2, user.getUser());
            statement.setString(3, user.getUser());

            statement.executeUpdate();

            return true;
        } catch (SQLException sqlException) {
            logger.warn(sqlException.getMessage());
            return false;
        }
    }
}
