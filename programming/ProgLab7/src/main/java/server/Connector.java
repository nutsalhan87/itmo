package server;

import java.io.IOException;
import java.net.ServerSocket;

public class Connector {
    public Connector() {}

    public static ServerSocket connect(int port) throws IOException {
        return new ServerSocket(port);
    }
}
