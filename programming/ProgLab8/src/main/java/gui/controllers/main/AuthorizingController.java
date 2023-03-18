package gui.controllers.main;

import client.AuthorizationException;
import gui.LanguagePopup;
import gui.SceneControl;
import gui.TextDrawable;
import client.locales.LocaleManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuthorizingController implements Initializable, TextDrawable {
    @FXML
    private TextField portField;
    @FXML
    private Label authorizingLabel;
    @FXML
    private Button languageButton;
    @FXML
    private TextField nameField;
    @FXML
    private PasswordField passwordField;

    private LanguagePopup languagePopup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LocaleManager.addListener(this);
        drawText();
        portField.requestFocus();
        languagePopup = new LanguagePopup(languageButton);
    }

    @Override
    public void drawText() {
        languageButton.setText(LocaleManager.getString("language"));
        nameField.setPromptText(LocaleManager.getString("username"));
        passwordField.setPromptText(LocaleManager.getString("password"));
        authorizingLabel.setText(LocaleManager.getString("login"));
        portField.setPromptText(LocaleManager.getString("port"));
    }

    @FXML
    private void openLanguageSelector(ActionEvent actionEvent) {
        languagePopup.showPopup();
    }

    public void authorize(ActionEvent actionEvent) throws IOException {
        try {
            if (nameField.getText().isEmpty() || passwordField.getText().isEmpty())
                return;
            SceneControl.connectToPort(Integer.parseUnsignedInt(portField.getText()));
            SceneControl.getBackendInteractor().authorize(nameField.getText(), passwordField.getText());
            SceneControl.openWorkspace();
        } catch (AuthorizationException auexc) {
            SceneControl.openMessage(LocaleManager.getString(auexc.getMessage()));
        } catch (ConnectException cexc) {
            SceneControl.openMessage(LocaleManager.getString("invalidPort"));
        } catch (NumberFormatException nexc) {
            SceneControl.openMessage(LocaleManager.getString("inputPositiveIntPort"));
        }
    }

    public void goBack(ActionEvent actionEvent) {
        SceneControl.openWelcomeMenu();
    }
}
