package client;

import general.Answer;
import general.CommandList;
import general.Request;
import general.User;
import general.route.Route;
import general.route.RouteProperty;
import gui.SceneControl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;

public class BackendInteractions {
    private User user;
    private final SocketChannel socketChannel;
    private final ObservableList<RouteProperty> routePropertyObservableList;
    private long lastUpdatedOnServer;

    public BackendInteractions(int port) throws ConnectException {
        socketChannel = Connector.connectedSocket(port);
        routePropertyObservableList = FXCollections.observableArrayList();
    }

    public void authorize(String login, String password) throws AuthorizationException, IOException {
        this.user = Authorizer.logIn(socketChannel, login, password);
    }

    public void register(String login, String password) throws AuthorizationException, IOException {
        this.user = Authorizer.createAccount(socketChannel, login, password);
    }

    public synchronized Answer sendRequestAndGetAnswer(Request request) throws IOException {
        SendRequest.sendRequest(request, socketChannel);
        return GetAnswer.getAnswer(socketChannel);
    }

    public User getUser() {
        return user;
    }

    public ObservableList<RouteProperty> getData() {
        return routePropertyObservableList;
    }

    public void refreshData() throws IOException {
        Request lastUpdatedRequest = new Request(CommandList.GET_LAST_UPDATE, new LinkedList<>(), getUser());
        Answer lastUpdatedAnswer = sendRequestAndGetAnswer(lastUpdatedRequest);
        if (lastUpdatedOnServer == (Long) lastUpdatedAnswer.getAnswer()) {
            return;
        }
        lastUpdatedOnServer = (Long) lastUpdatedAnswer.getAnswer();

        Request dataRequest = new Request(CommandList.GET_DATA, new LinkedList<>(), getUser());
        Answer dataAnswer = sendRequestAndGetAnswer(dataRequest);

        ObservableList<RouteProperty> newData = FXCollections.observableArrayList();
        for (Route route : (List<Route>) dataAnswer.getAnswer()) {
            newData.add(new RouteProperty(route));
        }
        routePropertyObservableList.setAll(newData);
    }

    public void executeScript(String scriptPath) throws FileNotFoundException {
        SceneControl.openMessage(CommandWorker.executeScript(scriptPath, user, socketChannel));
    }

    public void logOut() {
        try {
            socketChannel.close();
        } catch (IOException ignored) {}
    }
}
