package server;

import general.route.Route;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class RouteCollection {
    private final List<Route> data;
    private final ReentrantLock lock;
    private final org.apache.logging.log4j.Logger logger;
    private long lastUpdate;

    public RouteCollection() {
        data = new LinkedList<>();
        lock = new ReentrantLock();
        logger = org.apache.logging.log4j.LogManager.getLogger();
        lastUpdate = System.currentTimeMillis();
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
        Iterator<Route> iterator = newData.listIterator();
        if (data.size() != newData.size() || data.stream().anyMatch(r -> r.compareTo(iterator.next()) != 0)) {
            data.clear();
            data.addAll(newData);
            lastUpdate = System.currentTimeMillis();
        }
        lock.unlock();
    }

    public List<Route> getData() {
        return new LinkedList<>(data);
    }

    public long getLastUpdate() {
        return lastUpdate;
    }
}
