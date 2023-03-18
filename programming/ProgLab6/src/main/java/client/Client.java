package client;

import java.io.IOException;
import java.net.ConnectException;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        System.out.println("Введите порт");
        int port = new Scanner(System.in).nextInt(); //TODO: проверку на INTEGER
        System.out.println("Можете вводить команды. " +
                "Введите \"help\" для вывода списка доступных команд для обращения к серверу. " +
                "Введите \"exit\" для выхода из программы");

        while (true) {
            try {
                SocketChannel socketChannel = SocketChannel.open();
                new ConsoleInterface(socketChannel, port).startInterface(new Scanner(System.in)::nextLine);
            } catch (ConnectException conex) {
                System.out.println(conex.getMessage());
                System.out.println("Введите новый порт");
                port = new Scanner(System.in).nextInt();
            } catch (IOException ioexc) {
                System.out.println("Ошибка открытия сокета");
            }
        }
    }
}
