package gui.controllers.auxiliary;

import gui.TextDrawable;
import client.locales.LocaleManager;
import client.locales.Locales;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;

import java.net.URL;
import java.util.ResourceBundle;

public class LanguageController implements Initializable, TextDrawable {
    @FXML
    private RadioButton russianButton;
    @FXML
    private RadioButton norwegianButton;
    @FXML
    private RadioButton italianButton;
    @FXML
    private RadioButton spanishButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LocaleManager.addListener(this);
        drawText();
    }

    public void drawText() {
        russianButton.setText(LocaleManager.getString("russian"));
        norwegianButton.setText(LocaleManager.getString("norwegian"));
        italianButton.setText(LocaleManager.getString("italian"));
        spanishButton.setText(LocaleManager.getString("spanish"));
    }

    public void selectLanguage(ActionEvent actionEvent) {
        if (russianButton.isArmed()) {
            LocaleManager.changeLocale(Locales.RUSSIAN.getLocale());
        } else if (norwegianButton.isArmed()) {
            LocaleManager.changeLocale(Locales.NORWEGIAN.getLocale());
        } else if (italianButton.isArmed()) {
            LocaleManager.changeLocale(Locales.ITALIAN.getLocale());
        } else if (spanishButton.isArmed()) {
            LocaleManager.changeLocale(Locales.SPANISH.getLocale());
        }
    }
}
