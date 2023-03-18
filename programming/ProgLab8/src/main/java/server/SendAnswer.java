package server;

import general.Answer;
import general.Serializer;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.concurrent.locks.ReentrantLock;

public class SendAnswer implements Runnable {
    private final Answer answer;
    private final Socket socket;
    private final ReentrantLock lock;
    private final org.apache.logging.log4j.Logger logger;
    SendAnswer(Answer answer, Socket socket) {
        this.answer = answer;
        this.socket = socket;
        lock = new ReentrantLock();
        logger = org.apache.logging.log4j.LogManager.getLogger();
    }

    @Override
    public void run() {
        try {
            lock.lock();
            OutputStream outputStream = socket.getOutputStream();
            ByteBuffer byteBuffer = Serializer.serialize(answer);
            outputStream.write(byteBuffer.array(), 0, byteBuffer.limit());
            outputStream.flush();
            logger.info("Ответ успешно передан клиенту.");
            lock.unlock();
        } catch (IOException ioException) {
            logger.error(ioException.getMessage());
            logger.error("Это произошло из-за разрыва соединения.");
            lock.unlock();
        }
    }
}
