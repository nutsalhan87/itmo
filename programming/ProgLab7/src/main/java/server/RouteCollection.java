package server;

import general.route.Route;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class RouteCollection {
    private List<Route> data;
    private final ReentrantLock lock;
    private org.apache.logging.log4j.Logger logger;

    public RouteCollection() {
        data = new LinkedList<>();
        lock = new ReentrantLock();
        logger = org.apache.logging.log4j.LogManager.getLogger();
        new Thread(() -> {
            while(!Thread.interrupted()) {
                try {
                    Thread.sleep(5000);
                    updateData(DatabaseWorker.getAllRoutes());
                } catch (InterruptedException | SQLException e) {
                    logger.warn(e.getMessage());
                }
            }
        }).start();
    }

    public void updateData(List<Route> newData) {
        lock.lock();
        data.clear();
        data.addAll(newData);
        lock.unlock();
    }

    public List<Route> getData() {
        return new LinkedList<>(data);
    }
}
