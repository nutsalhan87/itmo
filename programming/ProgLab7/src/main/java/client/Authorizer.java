package client;

import general.CommandList;
import general.Request;
import general.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class Authorizer {
    private Authorizer() {}

    public static User authorize(SocketChannel socketChannel) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Для входа в существующий аккаунт введите команду 'log_in'.\n" +
                    "Для регистрации введите команду 'create_account'.\n" +
                    "Для выхода введите 'exit'.");
            String inputLine = null;
            try {
                inputLine = reader.readLine();
                if (inputLine.equals("exit")) {
                    System.out.println("Пока.");
                    System.exit(0);
                }
                if (inputLine.equals("log_in")) {
                    return logIn(socketChannel);
                } else if(inputLine.equals("create_account")) {
                    return createAccount(socketChannel);
                } else {
                    System.out.println("Такой команды нет.");
                }
            } catch (IOException e) {
                System.out.println("Недоступен ввод с консоли. Тут F. Пока.");
                System.exit(0);
            } catch (AuthorizationException exc) {
                System.out.println(exc.getMessage());
            } catch (ClassNotFoundException ignored) {}
        }
    }

    private static User logIn(SocketChannel socketChannel) throws IOException, ClassNotFoundException, AuthorizationException {
        System.out.println("Введите логин, а после пароль:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String user = reader.readLine();
        String password = reader.readLine();

        SendRequest.sendRequest(new Request(CommandList.LOG_IN, Arrays.asList(user, password), null), socketChannel);
        Boolean isUser = (Boolean) GetAnswer.getAnswer(socketChannel).getAnswer();
        if (isUser) {
            return (User)GetAnswer.getAnswer(socketChannel).getAnswer();
        } else {
            throw new AuthorizationException("Не удалось авторизоваться.");
        }
    }

    private static User createAccount(SocketChannel socketChannel) throws IOException, ClassNotFoundException, AuthorizationException {
        System.out.println("Введите желаемые логин и пароль:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String user = reader.readLine();
        String password = reader.readLine();

        SendRequest.sendRequest(new Request(CommandList.NEW_USER, Arrays.asList(user, password), null), socketChannel);
        Boolean isUser = (Boolean)GetAnswer.getAnswer(socketChannel).getAnswer();
        if (isUser) {
            return (User)GetAnswer.getAnswer(socketChannel).getAnswer();
        } else {
            throw new AuthorizationException("Не удалось создать аккаунт.");
        }
    }
}
