package gui;

import client.BackendInteractions;
import client.DataReloader;
import gui.controllers.auxiliary.MessageController;
import gui.controllers.main.WorkspaceController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;

public class SceneControl extends Application {
    private static Stage stage;
    private static BackendInteractions backendInteractor;
    private static FXMLLoader workspaceLoader;
    private static Scene workspace;
    private static Scene visualization;
    private static DataReloader dataReloader;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        openWelcomeMenu();
        stage.setMinHeight(768);
        stage.setMinWidth(1024);
        stage.setTitle("Dagestan");
        stage.getIcons().add(new Image("icons/globeIconBlack.png"));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static BackendInteractions getBackendInteractor() {
        return backendInteractor;
    }

    public static void connectToPort(int port) throws ConnectException {
        backendInteractor = new BackendInteractions(port);
    }

    public static void openWelcomeMenu() {
        try {
            URL url = SceneControl.class.getClassLoader().getResource("layouts/main/WelcomeMenu.fxml");
            stage.setScene(FXMLLoader.load(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openAuthorizeMenu() {
        try {
            URL url = SceneControl.class.getClassLoader().getResource("layouts/main/Authorizing.fxml");
            stage.setScene(FXMLLoader.load(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openRegisterMenu() {
        try {
            URL url = SceneControl.class.getClassLoader().getResource("layouts/main/Registering.fxml");
            stage.setScene(FXMLLoader.load(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openWorkspace() {
        try {
            if (workspace == null) {
                URL url = SceneControl.class.getClassLoader().getResource("layouts/main/Workspace.fxml");
                workspaceLoader = new FXMLLoader(url);
                workspace = workspaceLoader.load();
                stage.setScene(workspace);
                dataReloader = new DataReloader();
                dataReloader.runBackgroundReloading();
            } else {
                stage.setScene(workspace);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openVisualization() {
        try {
            if (visualization == null) {
                URL url = SceneControl.class.getClassLoader().getResource("layouts/main/Visualization.fxml");
                visualization = new FXMLLoader(url).load();
            }
            stage.setScene(visualization);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openMessage(String message) {
        try {
            URL url = SceneControl.class.getClassLoader().getResource("layouts/auxiliary/Message.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);

            Stage messageStage = new Stage();
            Scene scene = fxmlLoader.load();
            scene.setFill(Color.TRANSPARENT);
            messageStage.initOwner(stage);
            messageStage.setScene(scene);
            messageStage.initStyle(StageStyle.TRANSPARENT);
            messageStage.setResizable(false);
            messageStage.setWidth(600);
            messageStage.setHeight(400);

            fxmlLoader.<MessageController>getController().setMessage(message);
            fxmlLoader.<MessageController>getController().closeWindowButton.
                    setOnAction(actionEvent -> Transitions.scaleOut(scene.getRoot(), event -> messageStage.close()));
            Transitions.scaleIn(scene.getRoot());
            messageStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static WorkspaceController getWorkspaceController() {
        return workspaceLoader.getController();
    }

    public static void logOut() {
        backendInteractor.logOut();
        workspace = null;
        workspaceLoader = null;
        visualization = null;
        dataReloader.stopReloading();
        dataReloader = null;
        openWelcomeMenu();
    }
}
