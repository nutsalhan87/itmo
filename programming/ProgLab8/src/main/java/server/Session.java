package server;

import general.*;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.*;

public class Session implements Runnable {
    private final Socket socket;
    private final RouteCollection dataCollection;
    private final org.apache.logging.log4j.Logger logger;

    public Session(Socket socket, RouteCollection dataCollection) {
        this.dataCollection = dataCollection;
        this.socket = socket;
        logger = org.apache.logging.log4j.LogManager.getLogger();
    }

    @Override
    public void run() {
        try {
            logger.info("Клиент подключился к серверу");

            ForkJoinPool requestReader = new ForkJoinPool();
            ForkJoinTask<Request> request;
            while (true) {
                request = requestReader.submit(new GetRequest(socket));
                if (request.get().getCommand().equals(CommandList.NEW_USER)) {
                    if (Account.newUser(socket, request.get())) {
                        break;
                    }
                } else {
                    if (Account.logIn(socket, request.get())) {
                        break;
                    }
                }
            }

            ForkJoinPool requestResponser = new ForkJoinPool();
            ForkJoinTask<Answer> answer;
            ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
            while (!Thread.interrupted()) {
                request = requestReader.submit(new GetRequest(socket));
                logger.info("Запрос от клиента получен.");
                answer = requestResponser.submit(new ResponseToRequest(request.get().getCommand().getExecutableCommand(),
                        request.get().getArguments(), dataCollection, request.get().getUser()));
                fixedThreadPool.submit(new SendAnswer(answer.get(), socket));
                logger.info("Отправлен ответ клиенту.");
            }
        } catch (SocketException exs) {
            logger.warn(exs.getMessage());
            logger.warn("Соединение с клиентом потеряно.");
        } catch (ExecutionException executionException) {
            logger.error(executionException.getMessage());
        } catch (InterruptedException intExc) {
            try {
                socket.close();
            } catch (IOException ioex) {
                logger.error(ioex.getMessage());
            }
            logger.warn(intExc.getMessage());
        } catch (IOException notIgnored) {
            logger.error(notIgnored.getMessage());
            logger.warn("Мы не должны были придти сюда.");
        } finally {
            try {
                if (!socket.isClosed())
                    socket.close();
            } catch (IOException ignored) {}
        }
    }
}
