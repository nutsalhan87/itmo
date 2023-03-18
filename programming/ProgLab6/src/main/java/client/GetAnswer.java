package client;

import general.BufferSize;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class GetAnswer {
    public static String getAnswer(SocketChannel socketChannel) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(BufferSize.BUFFER_SIZE);
        do {
            byteBuffer.clear();
            socketChannel.read(byteBuffer);
            byteBuffer.flip();
        } while(!byteBuffer.hasRemaining());

        byte[] message = new byte[byteBuffer.limit()];
        byteBuffer.get(message, 0, byteBuffer.limit());

        return new String(message, StandardCharsets.UTF_8);
    }
}
