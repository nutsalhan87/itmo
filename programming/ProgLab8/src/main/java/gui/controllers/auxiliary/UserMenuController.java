package gui.controllers.auxiliary;

import client.locales.LocaleManager;
import general.CommandList;
import general.Request;
import gui.SceneControl;
import gui.TextDrawable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

public class UserMenuController implements Initializable, TextDrawable {
    @FXML
    private Button removeElementsButton;
    @FXML
    private Button exitButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LocaleManager.addListener(this);
        drawText();
    }

    @Override
    public void drawText() {
        removeElementsButton.setText(LocaleManager.getString("removeElements"));
        exitButton.setText(LocaleManager.getString("exit"));
    }

    public void setOnAction(EventHandler<ActionEvent> eventHandler) {
        exitButton.setOnAction(eventHandler);
    }

    @FXML
    private void removeAllUsersElements(ActionEvent actionEvent) throws IOException {
        SceneControl.getBackendInteractor().sendRequestAndGetAnswer(new Request(CommandList.CLEAR,
                Collections.emptyList(), SceneControl.getBackendInteractor().getUser()));
        SceneControl.getBackendInteractor().refreshData();
    }
}
