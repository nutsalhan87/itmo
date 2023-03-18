package client;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class Connector {
    private Connector() {}

    public static SocketChannel connectedSocket(int port) throws ConnectException {
        for (int i = 1; i <= 5; ++i) {
            try {
                SocketChannel socketChannel = SocketChannel.open();
                socketChannel.connect(new InetSocketAddress(InetAddress.getLocalHost(), port));
                socketChannel.configureBlocking(false);
                return socketChannel;
            } catch (IOException exc) {
                System.out.println("Ошибка подключения к серверу. Попытка номер " + i);
            }
        }
        throw new ConnectException("Подключиться к серверу не вышло");
    }

    public static void closeConnection(SocketChannel socketChannel) {
        try {
            socketChannel.close();
        } catch (IOException ignored) {}
    }
}
