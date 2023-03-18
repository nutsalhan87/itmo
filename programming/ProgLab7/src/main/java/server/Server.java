package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.sql.SQLException;

public class Server {
    public static void main(String[] args) throws IOException {
        org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger();

        RouteCollection routeCollection = new RouteCollection();
        try {
            routeCollection.updateData(DatabaseWorker.getAllRoutes());
        } catch (SQLException exc) {
            logger.error(exc.getMessage());
            logger.error("Невозможно получить коллекцию из БД. Без нее всей программе капут. Пока.");
            System.exit(-1);
        }

        System.out.println("Введите порт:");
        int port;
        while (true) {
            try {
                port = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
                break;
            } catch (NumberFormatException ignored) {}
        }

        ServerSocket serverSocket = Connector.connect(port);
        while (true) {
            new Thread(new Session(serverSocket.accept(), routeCollection)).start();
        }
    }
}
