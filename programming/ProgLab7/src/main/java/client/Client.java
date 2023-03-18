package client;

import general.CommandList;
import general.Request;
import general.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        System.out.println("Введите порт");
        int port;
        while (true) {
            try {
                port = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
                break;
            } catch (NumberFormatException ignored) {
            } catch (IOException ioex) {
                System.out.println("Невозможно читать с консоли. Тут F. Пока.");
                System.exit(0);
            }
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String inputLine = "";
        Request request;
        while (true) {
            try {
                SocketChannel socketChannel = Connector.connectedSocket(port);
                User user = Authorizer.authorize(socketChannel);
                System.out.println("Можете вводить команды. " +
                        "Введите \"help\" для вывода списка доступных команд для обращения к серверу. " +
                        "Введите \"exit\" для выхода из программы");

                while(true) {
                    try {
                        inputLine = bufferedReader.readLine();
                    } catch (IOException ioexc) {
                        System.out.println("Невозможно читать с консоли. Ну тут F, так что пока.");
                        System.exit(-1);
                    }

                    try {
                        request = CommandWorker.createRequest(inputLine, bufferedReader::readLine, user, socketChannel);
                        if (CommandList.NO_COMMAND.equals(request.getCommand())) {
                            continue;
                        }
                        SendRequest.sendRequest(request, socketChannel);
                        System.out.println((String)GetAnswer.getAnswer(socketChannel).getAnswer());
                    } catch (WrongCommandException exc) {
                        System.out.println(exc.getMessage());
                    } catch (IOException ioexc) {
                        throw new ConnectException("Соединение было разорвано.");
                    } catch (ClassNotFoundException ignored) {
                        System.out.println("Такое не должно произойти.");
                    }
                }
            } catch (ConnectException conex) {
                System.out.println(conex.getMessage());
                System.out.println("Введите новый порт");
                port = new Scanner(System.in).nextInt(); //TODO: проверка на int
            }
        }
    }
}
