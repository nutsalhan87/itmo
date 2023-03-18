package server;


import general.Request;
import general.route.Route;
import server.workwithexternaldata.JSONToParsedObject;
import server.workwithexternaldata.ParsedObjectToListRoute;
import server.workwithexternaldata.parsedobjects.ParsedObject;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Server {
    private static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger();

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        List<Route> data = new LinkedList<>();
        try {
            if (!(new File("Data.json").exists())) {
                throw new RuntimeException("Файла данных не существует");
            }
            if (!(new File("Data.json").canWrite() && new File("Data.json").canRead())) {
                throw new RuntimeException("Ввод или вывод в данный файл не доступен");
            }
            ParsedObject parsedObject = new JSONToParsedObject().parseFile("Data.json");
            data = ParsedObjectToListRoute.convertToListRoute(parsedObject);
            logger.info("Данные коллекции загружены из файла");
        } catch (RuntimeException exc) {
            logger.warn(exc.getMessage());
        } catch (IOException excio) {
            logger.warn("Файл данных с коллекией не удалось прочитать");
        }

        /*setupSignalHandler(data);
        setupShutDownWork(data);*/

        logger.info("Введите порт");
        int port = new Scanner(System.in).nextInt();

        ServerSocket serverSocket = Connector.connect(port);
        Socket socket = serverSocket.accept();
        while (true) {
            try {
                if(serverSocket.isClosed()) {
                    serverSocket = Connector.connect(port);
                    socket = serverSocket.accept();
                }
                logger.info("Клиент подключился к серверу");
                Request request = GetRequest.getRequest(socket);
                logger.info("Запрос от клиента получен");
                SendAnswer.sendAnswer(request.getCommand().getExecutableCommand().execute(request.getArguments(), data), socket);
                logger.info("Отправлен ответ клиенту");
                logger.info("Клиент отключился от сервера");
            } catch (SocketException exs) {
                logger.warn("Соединение с клиентом потеряно");
                serverSocket.close();
            }
        }
    }

    /*private static void setupSignalHandler(List<Route> data) { //CTRL + Z
        Signal.handle(new Signal("TSTP"), signal -> {
            try {
                Commands.save(new LinkedList<>(), data);
            } catch (IOException excio) {
                logger.warn("Сохранение недоступно. Проблемы с доступом к файлу");
            }
        });
    }

    private static void setupShutDownWork(List<Route> data) { //CTRL + C
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Произошел выход из программы");
            System.exit(0);
        }));
    }*/
}
