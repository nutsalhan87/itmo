package server;

import general.Answer;
import general.Request;
import general.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private static final ReentrantLock lock = new ReentrantLock();
    private static Statement statement;
    private static final org.apache.logging.log4j.Logger logger;
    static {
        logger = org.apache.logging.log4j.LogManager.getLogger();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("database.txt"))) {
            String url = bufferedReader.readLine();
            String user = bufferedReader.readLine();
            String password = bufferedReader.readLine();
            Connection connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
        } catch (IOException | SQLException exc) {
            logger.error("Невозможно подключиться к базе данных пользователей. Без нее серверу капут.");
            System.exit(-1);
        }
    }

    public static boolean logIn(Socket socket, Request request) throws IOException {
        String user = (String)(request.getArguments().get(0));
        String password = (String)(request.getArguments().get(1));
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
        try {
            ResultSet resultSet = statement.executeQuery("SELECT password FROM userdata WHERE owner='" + user + "';");
            resultSet.next();
            if (resultSet.getString("password").equals(hash256(password))) {
                fixedThreadPool.submit(new SendAnswer(new Answer(Boolean.TRUE), socket));
                fixedThreadPool.submit(new SendAnswer(new Answer(new User(user, password)), socket));
                logger.info("Пользователь " + user + " успешно авторизовался.");
                return true;
            } else {
                fixedThreadPool.submit(new SendAnswer(new Answer(Boolean.FALSE), socket));
                logger.info("У пользователя " + user + " не совпал пароль.");
                return false;
            }
        } catch (SQLException sqex) {
            fixedThreadPool.submit(new SendAnswer(new Answer(Boolean.FALSE), socket));
            logger.info(sqex.getMessage());
            logger.info("Пользователь " + user + " пытался авторизоваться, но его нет в системе.");
            return false;
        }
    }

    public static boolean newUser(Socket socket, Request request) throws IOException {
        String user = (String)(request.getArguments().get(0));
        String password = (String)(request.getArguments().get(1));
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
        try {
            ResultSet resultSet = statement.executeQuery("SELECT owner FROM userdata WHERE owner='" + user + "';");
            if (resultSet.next()) {
                fixedThreadPool.submit(new SendAnswer(new Answer(Boolean.FALSE), socket));
                logger.info("Пользователь " + user + " пытался зарегистрироваться, но такой идентефикатор уже есть в системе.");
                return false;
            } else {
                addUser(user, password);
                fixedThreadPool.submit(new SendAnswer(new Answer(Boolean.TRUE), socket));
                fixedThreadPool.submit(new SendAnswer(new Answer(new User(user, password)), socket));
                logger.info("Пользователь " + user + " успешно зарегистрировался в системе.");
                return true;
            }
        } catch (SQLException sqex) {
            fixedThreadPool.submit(new SendAnswer(new Answer(Boolean.FALSE), socket));
            logger.warn(sqex.getMessage());
            logger.warn("Произошла ошибка при попытке регистрации пользователя " + user + ".");
            return false;
        }
    }

    public static void addUser(String user, String password) throws SQLException {
        lock.lock();
        statement.executeUpdate("INSERT INTO userdata VALUES('" + user + "', '" + hash256(password) + "');");
        lock.unlock();
    }

    public static String hash256(String word) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashed = digest.digest(
                    word.getBytes(StandardCharsets.UTF_8));
            return encodeHexString(hashed);
        } catch (NoSuchAlgorithmException ignored) {return "Коля лох.";} //Такой алгоритм есть
    }

    private static String encodeHexString(byte[] byteArray) {
        StringBuffer hexStringBuffer = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            hexStringBuffer.append(byteToHex(byteArray[i]));
        }
        return hexStringBuffer.toString();
    }

    private static String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }
}
