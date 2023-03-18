package client;

import general.Request;
import general.Serializer;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class SendRequest {
    public static void sendRequest(Request request, SocketChannel socketChannel) throws IOException {
        socketChannel.write(Serializer.serialize(request));
    }
}