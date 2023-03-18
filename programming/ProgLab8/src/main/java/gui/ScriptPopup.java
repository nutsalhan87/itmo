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

public class ScriptPopup extends PopupControl {
    Button scriptButton;

    public ScriptPopup(Button scriptButton) {
        super();
        this.scriptButton = scriptButton;
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
        if (scriptButton.getScene() != null && scriptButton.getScene().getWindow() != null) {
            if (!isShowing()) {
                Window parent = scriptButton.getScene().getWindow();
                show(parent, parent.getX() + scriptButton.localToScene(0.0D, 0.0D).getX() +
                        scriptButton.getScene().getX() + ((scriptButton.getWidth() - ((VBox)getSkin().getNode()).getWidth()) / 2),
                parent.getY() + scriptButton.localToScene(0.0D, 0.0D).getY()
                        + scriptButton.getScene().getY() + scriptButton.getLayoutBounds().getHeight());
            }
        } else {
            throw new IllegalStateException("Can not show popup. The node must be attached to a scene/window.");
        }
    }

    protected Skin<?> createDefaultSkin() {
        try {
            return new ScriptPopupSkin(this);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class ScriptPopupSkin implements Skin<ScriptPopup> {
        private final VBox scriptMenu;
        private final ScriptPopup scriptPopup;

        public ScriptPopupSkin(ScriptPopup scriptPopup) throws IOException {
            this.scriptPopup = scriptPopup;
            URL url = FilterPopup.class.getClassLoader().getResource("layouts/auxiliary/ScriptMenu.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            scriptMenu = loader.load();
        }

        @Override
        public ScriptPopup getSkinnable() {
            return scriptPopup;
        }

        @Override
        public Node getNode() {
            return scriptMenu;
        }

        @Override
        public void dispose() {}
    }
}
