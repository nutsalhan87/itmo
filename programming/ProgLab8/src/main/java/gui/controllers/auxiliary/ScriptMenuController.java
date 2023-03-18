package gui.controllers.auxiliary;

import client.locales.LocaleManager;
import gui.SceneControl;
import gui.TextDrawable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

public class ScriptMenuController implements Initializable, TextDrawable {
    @FXML
    private TextField scriptPathField;
    @FXML
    private Button scriptExecButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LocaleManager.addListener(this);
        drawText();
    }

    @Override
    public void drawText() {
        scriptPathField.setPromptText(LocaleManager.getString("scriptPath"));
        scriptExecButton.setText(LocaleManager.getString("execute"));
    }

    @FXML
    private void executeScript(ActionEvent actionEvent) throws IOException {
        try {
            SceneControl.getBackendInteractor().executeScript(scriptPathField.getText());
            SceneControl.getBackendInteractor().refreshData();
        } catch (FileNotFoundException fnexc) {
            SceneControl.openMessage(LocaleManager.getString("noFileScript"));
        } catch (NoSuchElementException neexc) {
            SceneControl.openMessage(LocaleManager.getString("noNextLine"));
        }
    }
}
