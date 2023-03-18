package gui.controllers.auxiliary.side;

import general.CommandList;
import general.Request;
import general.route.Route;
import gui.SceneControl;
import gui.TextDrawable;
import client.locales.LocaleManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

public class AddMenuController extends AbstractSideMenu implements Initializable, TextDrawable {
    @FXML
    private Label fromXLabel;
    @FXML
    private Label fromYLabel;
    @FXML
    private Label fromZLabel;
    @FXML
    private Label toXLabel;
    @FXML
    private Label toYLabel;
    @FXML
    private Label toZLabel;
    @FXML
    private Button sendButton;
    @FXML
    private RadioButton ifMaxButton;
    @FXML
    private Label distanceLabel;
    @FXML
    private Label fromNameLabel;
    @FXML
    private Label nameLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LocaleManager.addListener(this);
        drawText();
    }

    @Override
    public void drawText() {
        nameLabel.setText(LocaleManager.getString("name"));
        fromNameLabel.setText(LocaleManager.getString("fromName"));
        distanceLabel.setText(LocaleManager.getString("distance"));
        sendButton.setText(LocaleManager.getString("send"));
        ifMaxButton.setText(LocaleManager.getString("ifMax"));
        fromXLabel.setText("'" + LocaleManager.getString("from") + "' X");
        fromYLabel.setText("'" + LocaleManager.getString("from") + "' Y");
        fromZLabel.setText("'" + LocaleManager.getString("from") + "' Z");
        toXLabel.setText("'" + LocaleManager.getString("to") + "' X");
        toYLabel.setText("'" + LocaleManager.getString("to") + "' Y");
        toZLabel.setText("'" + LocaleManager.getString("to") + "' Z");
    }

    @FXML
    private void sendRoute(ActionEvent actionEvent) throws IOException {
        try {
            Route route = getRouteFromFields();
            if (ifMaxButton.isSelected()) {
                SceneControl.getBackendInteractor().sendRequestAndGetAnswer(new Request(CommandList.ADD_IF_MAX,
                        Collections.singletonList(route), SceneControl.getBackendInteractor().getUser()));
            } else {
                SceneControl.getBackendInteractor().sendRequestAndGetAnswer(new Request(CommandList.ADD,
                        Collections.singletonList(route), SceneControl.getBackendInteractor().getUser()));
            }
            closeMenuButton.fire();
            SceneControl.getBackendInteractor().refreshData();
        } catch (IllegalArgumentException iaexc) {
            SceneControl.openMessage(LocaleManager.getString(iaexc.getMessage()));
        }
    }
}
