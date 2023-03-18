package gui.controllers.auxiliary;

import client.locales.LocaleManager;
import gui.SceneControl;
import gui.TextDrawable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class FilterMenuController implements Initializable, TextDrawable {
    @FXML
    private TextField filterField;
    @FXML
    private RadioButton startsFilter;
    @FXML
    private ToggleGroup filterToggleGroup;
    @FXML
    private RadioButton containsFilter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LocaleManager.addListener(this);
        drawText();

        filterToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) ->
                SceneControl.getWorkspaceController().table.
                        setItems(SceneControl.getWorkspaceController().
                                checkFilter(SceneControl.getBackendInteractor().getData())));
        filterField.textProperty().addListener((observable, oldValue, newValue) ->
                SceneControl.getWorkspaceController().table.
                        setItems(SceneControl.getWorkspaceController().
                                checkFilter(SceneControl.getBackendInteractor().getData())));
    }

    @Override
    public void drawText() {
        startsFilter.setText(LocaleManager.getString("startsWith"));
        containsFilter.setText(LocaleManager.getString("contains"));
    }

    public boolean isToggleStartsWithActive() {
        return filterToggleGroup.getSelectedToggle() != null && filterToggleGroup.getSelectedToggle().equals(startsFilter);
    }

    public boolean isToggleContainsActive() {
        return filterToggleGroup.getSelectedToggle() != null && filterToggleGroup.getSelectedToggle().equals(containsFilter);
    }

    public String getText() {
        return filterField.getText();
    }

    public void clearText() {
        filterField.clear();
    }
}
