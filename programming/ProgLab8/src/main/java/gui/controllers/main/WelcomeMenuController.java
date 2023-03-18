package gui.controllers.main;

import gui.LanguagePopup;
import gui.SceneControl;
import gui.TextDrawable;
import client.locales.LocaleManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeMenuController implements Initializable, TextDrawable {
    @FXML
    private Label welcomeLabel;
    @FXML
    private Button logIn;
    @FXML
    private Button register;
    @FXML
    private Button languageButton;

    private LanguagePopup languagePopup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LocaleManager.addListener(this);
        drawText();
        languagePopup = new LanguagePopup(languageButton);
    }

    @Override
    public void drawText() {
        welcomeLabel.setText(LocaleManager.getString("welcome"));
        logIn.setText(LocaleManager.getString("login"));
        register.setText(LocaleManager.getString("register"));
        languageButton.setText(LocaleManager.getString("language"));
    }

    @FXML
    private void openLanguageSelector(ActionEvent actionEvent) {
        languagePopup.showPopup();
    }

    @FXML
    private void logIn(ActionEvent actionEvent) {
        SceneControl.openAuthorizeMenu();
    }

    @FXML
    private void register(ActionEvent actionEvent) {
        SceneControl.openRegisterMenu();
    }

    @FXML
    private void shortcutAction(KeyEvent keyEvent) {
        if (keyEvent.getCharacter().equals("1")) {
            logIn(new ActionEvent());
        } else if(keyEvent.getCharacter().equals("2")) {
            register(new ActionEvent());
        }
    }
}
