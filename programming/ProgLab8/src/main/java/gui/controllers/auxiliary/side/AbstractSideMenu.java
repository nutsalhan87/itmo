package gui.controllers.auxiliary.side;

import client.workwithroute.RouteFields;
import client.workwithroute.ValuesChecking;
import general.route.Coordinates;
import general.route.Route;
import general.route.location.first.Location;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

abstract public class AbstractSideMenu {
    @FXML
    public Button closeMenuButton;
    @FXML
    protected TextField distanceField;
    @FXML
    protected TextField toZField;
    @FXML
    protected TextField toYField;
    @FXML
    protected TextField toXField;
    @FXML
    protected TextField fromNameField;
    @FXML
    protected TextField fromZField;
    @FXML
    protected TextField fromYField;
    @FXML
    protected TextField fromXField;
    @FXML
    protected TextField yField;
    @FXML
    protected TextField xField;
    @FXML
    protected TextField nameField;

    protected Route getRouteFromFields() {
        if (nameField.getText().isEmpty()) {
            throw new IllegalArgumentException("valuesRestrictions");
        } else if (fromNameField.getText().isEmpty()) {
            throw new IllegalArgumentException("valuesRestrictions");
        }
        return new Route(nameField.getText(),
                new Coordinates(
                        Double.parseDouble(ValuesChecking.checkCondition(xField.getText(), RouteFields.C_X)),
                        Integer.parseInt(ValuesChecking.checkCondition(yField.getText(), RouteFields.C_Y))
                ),
                new Location(
                        Double.parseDouble(ValuesChecking.checkCondition(fromXField.getText(), RouteFields.FL_X)),
                        Long.parseLong(ValuesChecking.checkCondition(fromYField.getText(), RouteFields.FL_Y)),
                        Double.parseDouble(ValuesChecking.checkCondition(fromZField.getText(), RouteFields.FL_Z)),
                        fromNameField.getText()
                ),
                new general.route.location.second.Location(
                        Integer.parseInt(ValuesChecking.checkCondition(toXField.getText(), RouteFields.SL_X)),
                        Integer.parseInt(ValuesChecking.checkCondition(toYField.getText(), RouteFields.SL_Y)),
                        Float.parseFloat(ValuesChecking.checkCondition(toZField.getText(), RouteFields.SL_Z))
                ),
                Double.parseDouble(ValuesChecking.checkCondition(distanceField.getText(), RouteFields.R_DISTANCE)));
    }
}
