package gui.controllers.auxiliary.side;

import general.CommandList;
import general.Request;
import general.route.RouteProperty;
import gui.SceneControl;
import gui.TextDrawable;
import client.locales.LocaleManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class EditMenuController extends AbstractSideMenu implements Initializable, TextDrawable {
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
    private Button removeButton;
    @FXML
    private Button sendButton;
    @FXML
    private Label nameLabel;
    @FXML
    private Label distanceLabel;
    @FXML
    private Label fromNameLabel;

    private int idRoute;

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
        removeButton.setText(LocaleManager.getString("remove"));
        sendButton.setText(LocaleManager.getString("send"));
    }

    public void setFields(RouteProperty rowData) {
        nameField.setText(rowData.getName().getValue());
        xField.setText(rowData.getX().getValue().toString());
        yField.setText(rowData.getY().getValue().toString());
        fromXField.setText(rowData.getFromX().getValue().toString());
        fromYField.setText(rowData.getFromY().getValue().toString());
        fromZField.setText(rowData.getFromZ().getValue().toString());
        fromNameField.setText(rowData.getFromName().getValue());
        toXField.setText(rowData.getToX().getValue().toString());
        toYField.setText(rowData.getToY().getValue().toString());
        toZField.setText(rowData.getToZ().getValue().toString());
        distanceField.setText(rowData.getDistance().getValue().toString());
        fromXLabel.setText("'" + LocaleManager.getString("from") + "' X");
        fromYLabel.setText("'" + LocaleManager.getString("from") + "' Y");
        fromZLabel.setText("'" + LocaleManager.getString("from") + "' Z");
        toXLabel.setText("'" + LocaleManager.getString("to") + "' X");
        toYLabel.setText("'" + LocaleManager.getString("to") + "' Y");
        toZLabel.setText("'" + LocaleManager.getString("to") + "' Z");
    }

    public void setIdRoute(int id) {
        idRoute = id;
    }

    @FXML
    private void removeRoute(ActionEvent actionEvent) throws IOException {
        String id = Integer.toString(idRoute);
        SceneControl.getBackendInteractor().sendRequestAndGetAnswer(new Request(CommandList.REMOVE_BY_ID,
                Collections.singletonList(id), SceneControl.getBackendInteractor().getUser()));
        closeMenuButton.fire();
        SceneControl.getBackendInteractor().refreshData();
    }

    @FXML
    private void updateRoute(ActionEvent actionEvent) throws IOException {
        try {
            List<Object> arguments = new LinkedList<>();
            arguments.add(getRouteFromFields());
            arguments.add(idRoute);
            SceneControl.getBackendInteractor().sendRequestAndGetAnswer(new Request(CommandList.UPDATE,
                    arguments, SceneControl.getBackendInteractor().getUser()));
            closeMenuButton.fire();
            SceneControl.getBackendInteractor().refreshData();
        } catch (IllegalArgumentException iaexc) {
            SceneControl.openMessage(LocaleManager.getString(iaexc.getMessage()));
        }
    }
}
