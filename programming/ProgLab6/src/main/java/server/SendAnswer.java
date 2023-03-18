package server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SendAnswer {
    private SendAnswer() {}

    public static void sendAnswer(String answer, Socket socket) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
        outputStreamWriter.write(answer.toCharArray());
        outputStreamWriter.flush();
        socket.close();
    }
}
