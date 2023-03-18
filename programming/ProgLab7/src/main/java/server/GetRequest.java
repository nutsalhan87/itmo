package server;

import general.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

public class GetRequest implements Callable<Request> {
    private Socket socket;
    private ReentrantLock lock;
    private org.apache.logging.log4j.Logger logger;
    public GetRequest(Socket socket) {
        this.socket = socket;
        lock = new ReentrantLock();
        logger = org.apache.logging.log4j.LogManager.getLogger();
    }

    @Override
    public Request call() throws IOException, ClassNotFoundException {
        lock.lock();
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        Request request = (Request) objectInputStream.readObject();
        if (request.getUser() != null)
            logger.info("Получен запрос от " + request.getUser().getUser() + ".");
        else
            logger.info("Получен запрос на авторизацию или создание аккаунта.");
        lock.unlock();
        return request;
    }
}
