package gui;

import gui.controllers.auxiliary.UserMenuController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PopupControl;
import javafx.scene.control.Skin;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;

public class UserPopup extends PopupControl {
    Button userButton;

    public UserPopup(Button userButton) {
        super();
        this.userButton = userButton;
        setAutoHide(true);
        setAutoFix(true);
        setHideOnEscape(true);
        setSkin(createDefaultSkin());
        ((UserPopupSkin)getSkin()).setOnAction(event -> {
            hide();
            SceneControl.logOut();
        });
        Platform.runLater(() -> {
            showPopup();
            hide();
        });
    }

    public void showPopup() {
        if (userButton.getScene() != null && userButton.getScene().getWindow() != null) {
            if (!isShowing()) {
                Window parent = userButton.getScene().getWindow();
                show(parent, parent.getX() + userButton.localToScene(0.0D, 0.0D).getX() +
                        userButton.getScene().getX() + ((userButton.getWidth() - ((VBox)getSkin().getNode()).getWidth()) / 2),
                parent.getY() + userButton.localToScene(0.0D, 0.0D).getY()
                        + userButton.getScene().getY() + userButton.getLayoutBounds().getHeight());
            }
        } else {
            throw new IllegalStateException("Can not show popup. The node must be attached to a scene/window.");
        }
    }

    protected Skin<?> createDefaultSkin() {
        try {
            return new UserPopupSkin(this);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class UserPopupSkin implements Skin<UserPopup> {
        private final VBox userMenu;
        private final UserPopup userPopup;
        private final UserMenuController userMenuController;

        public UserPopupSkin(UserPopup userPopup) throws IOException {
            this.userPopup = userPopup;
            URL url = FilterPopup.class.getClassLoader().getResource("layouts/auxiliary/UserMenu.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            userMenu = loader.load();
            userMenuController = loader.getController();
        }

        public void setOnAction(EventHandler<ActionEvent> eventHandler) {
            userMenuController.setOnAction(eventHandler);
        }

        @Override
        public UserPopup getSkinnable() {
            return userPopup;
        }

        @Override
        public Node getNode() {
            return userMenu;
        }

        @Override
        public void dispose() {}
    }
}
