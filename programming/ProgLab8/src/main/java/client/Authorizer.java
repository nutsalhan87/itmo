package client;

import general.CommandList;
import general.Request;
import general.User;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class Authorizer {
    private Authorizer() {}

    public static User logIn(SocketChannel socketChannel, String user, String password) throws IOException, AuthorizationException {
        SendRequest.sendRequest(new Request(CommandList.LOG_IN, Arrays.asList(user, password), null), socketChannel);
        Boolean isUser = (Boolean) GetAnswer.getAnswer(socketChannel).getAnswer();
        if (isUser) {
            return (User)GetAnswer.getAnswer(socketChannel).getAnswer();
        } else {
            throw new AuthorizationException("authorizationUnsuccessful");
        }
    }

    public static User createAccount(SocketChannel socketChannel, String user, String password) throws IOException, AuthorizationException {
        SendRequest.sendRequest(new Request(CommandList.NEW_USER, Arrays.asList(user, password), null), socketChannel);
        Boolean isUser = (Boolean)GetAnswer.getAnswer(socketChannel).getAnswer();
        if (isUser) {
            return (User)GetAnswer.getAnswer(socketChannel).getAnswer();
        } else {
            throw new AuthorizationException("registerUnsuccessful");
        }
    }
}
