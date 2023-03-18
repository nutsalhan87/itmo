package gui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PopupControl;
import javafx.scene.control.Skin;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;

public class LanguagePopup extends PopupControl {
    Button languageButton;

    public LanguagePopup(Button languageButton) {
        super();
        this.languageButton = languageButton;
        setAutoHide(true);
        setAutoFix(true);
        setHideOnEscape(true);
        setSkin(createDefaultSkin());
        Platform.runLater(() -> {
            showPopup();
            hide();
        });
    }

    public void showPopup() {
        if (languageButton.getScene() != null && languageButton.getScene().getWindow() != null) {
            if (!isShowing()) {
                Window parent = languageButton.getScene().getWindow();
                show(parent, parent.getX() + languageButton.localToScene(0.0D, 0.0D).getX() +
                        languageButton.getScene().getX() + ((languageButton.getWidth() - ((VBox)getSkin().getNode()).getWidth()) / 2),
                        parent.getY() + languageButton.localToScene(0.0D, 0.0D).getY()
                        + languageButton.getScene().getY() + languageButton.getLayoutBounds().getHeight());
            }
        } else {
            throw new IllegalStateException("Can not show popup. The node must be attached to a scene/window.");
        }
    }

    protected Skin<?> createDefaultSkin() {
        try {
            return new LanguagePopup.LanguagePopupSkin(this);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class LanguagePopupSkin implements Skin<LanguagePopup> {
        private final VBox languageSelector;
        private final LanguagePopup languagePopup;

        public LanguagePopupSkin(LanguagePopup languagePopup) throws IOException {
            this.languagePopup = languagePopup;
            URL url = FilterPopup.class.getClassLoader().getResource("layouts/auxiliary/Language.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            languageSelector = loader.load();
        }

        @Override
        public LanguagePopup getSkinnable() {
            return languagePopup;
        }

        @Override
        public Node getNode() {
            return languageSelector;
        }

        @Override
        public void dispose() {}
    }
}
