package gui.controllers.main;

import general.Answer;
import general.CommandList;
import general.Request;
import general.route.RouteProperty;
import gui.*;
import gui.controllers.auxiliary.side.AbstractSideMenu;
import gui.controllers.auxiliary.side.EditMenuController;
import client.locales.LocaleManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class WorkspaceController implements Initializable, TextDrawable {
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    public TableView<RouteProperty> table;
    @FXML
    private FilteredTableColumn<RouteProperty, Integer> idColumn;
    @FXML
    private FilteredTableColumn<RouteProperty, String> nameColumn;
    @FXML
    private FilteredTableColumn<RouteProperty, Double> xColumn;
    @FXML
    private FilteredTableColumn<RouteProperty, Integer> yColumn;
    @FXML
    private FilteredTableColumn<RouteProperty, String> dateColumn;
    @FXML
    private FilteredTableColumn<RouteProperty, Double> fromXColumn;
    @FXML
    private FilteredTableColumn<RouteProperty, Long> fromYColumn;
    @FXML
    private FilteredTableColumn<RouteProperty, Double> fromZColumn;
    @FXML
    private FilteredTableColumn<RouteProperty, String> fromNameColumn;
    @FXML
    private FilteredTableColumn<RouteProperty, Integer> toXColumn;
    @FXML
    private FilteredTableColumn<RouteProperty, Integer> toYColumn;
    @FXML
    private FilteredTableColumn<RouteProperty, Float> toZColumn;
    @FXML
    private FilteredTableColumn<RouteProperty, Double> distanceColumn;
    @FXML
    private FilteredTableColumn<RouteProperty, String> ownerColumn;
    @FXML
    private Button userButton;
    @FXML
    private Button scriptButton;
    @FXML
    private Button visualizationButton;
    @FXML
    private Button languageButton;
    @FXML
    private Label tableHeading;

    private LanguagePopup languagePopup;
    private ScriptPopup scriptPopup;
    private UserPopup userPopup;

    private FilterPopup<RouteProperty, Integer> idFilter;
    private FilterPopup<RouteProperty, String> nameFilter;
    private FilterPopup<RouteProperty, Double> xFilter;
    private FilterPopup<RouteProperty, Integer> yFilter;
    private FilterPopup<RouteProperty, String> dateFilter;
    private FilterPopup<RouteProperty, Double> fromXFilter;
    private FilterPopup<RouteProperty, Long> fromYFilter;
    private FilterPopup<RouteProperty, Double> fromZFilter;
    private FilterPopup<RouteProperty, String> fromNameFilter;
    private FilterPopup<RouteProperty, Integer> toXFilter;
    private FilterPopup<RouteProperty, Integer> toYFilter;
    private FilterPopup<RouteProperty, Float> toZFilter;
    private FilterPopup<RouteProperty, Double> distanceFilter;
    private FilterPopup<RouteProperty, String> ownerFilter;
    private List<FilterPopup<RouteProperty, ?>> filters;

    private StackPane translucentBackground;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LocaleManager.addListener(this);
        initializeTable();
        drawText();

        languagePopup = new LanguagePopup(languageButton);
        scriptPopup = new ScriptPopup(scriptButton);
        userPopup = new UserPopup(userButton);

        translucentBackground = new StackPane();
        AnchorPane.setLeftAnchor(translucentBackground, 0D);
        AnchorPane.setRightAnchor(translucentBackground, 0D);
        AnchorPane.setTopAnchor(translucentBackground, 0D);
        AnchorPane.setBottomAnchor(translucentBackground, 0D);
        translucentBackground.setStyle("-fx-background-color: rgba(0, 0, 0, 0.25);");
        translucentBackground.setDisable(true);
        translucentBackground.setOpacity(0);
        mainAnchor.getChildren().add(translucentBackground);
    }

    @Override
    public void drawText() {
        userButton.setText(SceneControl.getBackendInteractor().getUser().getUser());
        scriptButton.setText(LocaleManager.getString("script"));
        visualizationButton.setText(LocaleManager.getString("map"));
        languageButton.setText(LocaleManager.getString("language"));
        tableHeading.setText(LocaleManager.getString("dagestanBase"));
        dateColumn.setText(LocaleManager.getString("creationDate"));
        distanceColumn.setText(LocaleManager.getString("distance"));
        fromNameColumn.setText(LocaleManager.getString("fromName"));
        nameColumn.setText(LocaleManager.getString("name"));
        ownerColumn.setText(LocaleManager.getString("owner"));
        fromXColumn.setText("'" + LocaleManager.getString("from") + "' X");
        fromYColumn.setText("'" + LocaleManager.getString("from") + "' Y");
        fromZColumn.setText("'" + LocaleManager.getString("from") + "' Z");
        toXColumn.setText("'" + LocaleManager.getString("to") + "' X");
        toYColumn.setText("'" + LocaleManager.getString("to") + "' Y");
        toZColumn.setText("'" + LocaleManager.getString("to") + "' Z");

        table.refresh();
    }

    private void initializeTable() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getId().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
        xColumn.setCellValueFactory(cellData -> cellData.getValue().getX().asObject());
        yColumn.setCellValueFactory(cellData -> cellData.getValue().getY().asObject());
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                LocaleManager.getDate(cellData.getValue().getCreationDate().get())));
        fromXColumn.setCellValueFactory(cellData -> cellData.getValue().getFromX().asObject());
        fromYColumn.setCellValueFactory(cellData -> cellData.getValue().getFromY().asObject());
        fromZColumn.setCellValueFactory(cellData -> cellData.getValue().getFromZ().asObject());
        fromNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFromName());
        toXColumn.setCellValueFactory(cellData -> cellData.getValue().getToX().asObject());
        toYColumn.setCellValueFactory(cellData -> cellData.getValue().getToY().asObject());
        toZColumn.setCellValueFactory(cellData -> cellData.getValue().getToZ().asObject());
        distanceColumn.setCellValueFactory(cellData -> cellData.getValue().getDistance().asObject());
        ownerColumn.setCellValueFactory(cellData -> cellData.getValue().getOwner());

        idFilter = new FilterPopup<>(idColumn, S -> S.getId().get());
        nameFilter = new FilterPopup<>(nameColumn, S -> S.getName().get());
        xFilter = new FilterPopup<>(xColumn, S -> S.getX().get());
        yFilter = new FilterPopup<>(yColumn, S -> S.getY().get());
        dateFilter = new FilterPopup<>(dateColumn, S -> LocaleManager.getDate(S.getCreationDate().get()));
        fromXFilter = new FilterPopup<>(fromXColumn, S -> S.getFromX().get());
        fromYFilter = new FilterPopup<>(fromYColumn, S -> S.getFromY().get());
        fromZFilter = new FilterPopup<>(fromZColumn, S -> S.getFromZ().get());
        fromNameFilter = new FilterPopup<>(fromNameColumn, S -> S.getFromName().get());
        toXFilter = new FilterPopup<>(toXColumn, S -> S.getToX().get());
        toYFilter = new FilterPopup<>(toYColumn, S -> S.getToY().get());
        toZFilter = new FilterPopup<>(toZColumn, S -> S.getToZ().get());
        distanceFilter = new FilterPopup<>(distanceColumn, S -> S.getDistance().get());
        ownerFilter = new FilterPopup<>(ownerColumn, S -> S.getOwner().get());
        filters = new LinkedList<>(Arrays.asList(idFilter, nameFilter, xFilter, yFilter, dateFilter, fromXFilter,
                fromYFilter, fromZFilter, fromNameFilter, toXFilter, toYFilter, toZFilter, distanceFilter, ownerFilter));

        idColumn.setOnFilterAction(event -> idFilter.showPopup());
        nameColumn.setOnFilterAction(event -> nameFilter.showPopup());
        xColumn.setOnFilterAction(event -> xFilter.showPopup());
        yColumn.setOnFilterAction(event -> yFilter.showPopup());
        dateColumn.setOnFilterAction(event -> dateFilter.showPopup());
        fromXColumn.setOnFilterAction(event -> fromXFilter.showPopup());
        fromYColumn.setOnFilterAction(event -> fromYFilter.showPopup());
        fromZColumn.setOnFilterAction(event -> fromZFilter.showPopup());
        fromNameColumn.setOnFilterAction(event -> fromNameFilter.showPopup());
        toXColumn.setOnFilterAction(event -> toXFilter.showPopup());
        toYColumn.setOnFilterAction(event -> toYFilter.showPopup());
        toZColumn.setOnFilterAction(event -> toZFilter.showPopup());
        distanceColumn.setOnFilterAction(event -> distanceFilter.showPopup());
        ownerColumn.setOnFilterAction(event -> ownerFilter.showPopup());

        table.widthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.doubleValue() / oldValue.doubleValue() > 10D) {
                return;
            }
            table.resizeColumn(idColumn, (newValue.doubleValue() / oldValue.doubleValue() - 1) * idColumn.getWidth());
            table.resizeColumn(nameColumn, (newValue.doubleValue() / oldValue.doubleValue() - 1) * nameColumn.getWidth());
            table.resizeColumn(xColumn, (newValue.doubleValue() / oldValue.doubleValue() - 1) * xColumn.getWidth());
            table.resizeColumn(yColumn, (newValue.doubleValue() / oldValue.doubleValue() - 1) * yColumn.getWidth());
            table.resizeColumn(dateColumn, (newValue.doubleValue() / oldValue.doubleValue() - 1) * dateColumn.getWidth());
            table.resizeColumn(fromXColumn, (newValue.doubleValue() / oldValue.doubleValue() - 1) * fromXColumn.getWidth());
            table.resizeColumn(fromYColumn, (newValue.doubleValue() / oldValue.doubleValue() - 1) * fromYColumn.getWidth());
            table.resizeColumn(fromZColumn, (newValue.doubleValue() / oldValue.doubleValue() - 1) * fromZColumn.getWidth());
            table.resizeColumn(fromNameColumn, (newValue.doubleValue() / oldValue.doubleValue() - 1) * fromNameColumn.getWidth());
            table.resizeColumn(toXColumn, (newValue.doubleValue() / oldValue.doubleValue() - 1) * toXColumn.getWidth());
            table.resizeColumn(toYColumn, (newValue.doubleValue() / oldValue.doubleValue() - 1) * toYColumn.getWidth());
            table.resizeColumn(toZColumn, (newValue.doubleValue() / oldValue.doubleValue() - 1) * toZColumn.getWidth());
            table.resizeColumn(distanceColumn, (newValue.doubleValue() / oldValue.doubleValue() - 1) * distanceColumn.getWidth());
            table.resizeColumn(ownerColumn, (newValue.doubleValue() / oldValue.doubleValue() - 1) * ownerColumn.getWidth());
        });

        // Открытие EditMenu по дабл-клику
        table.setRowFactory(tableView -> {
            TableRow<RouteProperty> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
                    RouteProperty rowData = row.getItem();
                    URL url = SceneControl.class.getClassLoader().getResource("layouts/auxiliary/side/EditMenu.fxml");
                    FXMLLoader loader = new FXMLLoader(url);
                    try {
                        Answer answer = SceneControl.getBackendInteractor().sendRequestAndGetAnswer(
                                new Request(CommandList.CHECK_ID, Collections.singletonList(rowData.getId().getValue()),
                                        SceneControl.getBackendInteractor().getUser()));
                        if (answer.getAnswer().equals("TRUE")) {
                            openSideMenu(loader);
                            loader.<EditMenuController>getController().setFields(rowData);
                            loader.<EditMenuController>getController().setIdRoute(rowData.getId().getValue());
                        }
                    } catch (IOException ioexc) {
                        SceneControl.openMessage(ioexc.getMessage());
                    }
                }
            });
            return row;
        });

        SceneControl.getBackendInteractor().getData().addListener((ListChangeListener<? super RouteProperty>) observable ->
                table.setItems(checkFilter(observable.getList())));
    }

    public ObservableList<RouteProperty> checkFilter(ObservableList<? extends RouteProperty> data) {
        ObservableList<RouteProperty> filteredData = FXCollections.observableArrayList(data);
        for (FilterPopup<?, ?> filter : filters) {
            filteredData = filter.checkFilter(filteredData);
        }
        return filteredData;
    }

    private void openSideMenu(FXMLLoader loader) throws IOException {
        AnchorPane sideMenu = loader.load();
        AnchorPane.setTopAnchor(sideMenu, 0D);
        AnchorPane.setBottomAnchor(sideMenu, 0D);
        AnchorPane.setLeftAnchor(sideMenu, 0D);

        mainAnchor.getChildren().add(sideMenu);

        translucentBackground.setDisable(false);
        Transitions.translateIn(sideMenu, -600, 0);
        Transitions.fadeIn(translucentBackground);

        Runnable closeSideMenu = () -> {
            Transitions.translateOut(sideMenu, -600, 0, event -> mainAnchor.getChildren().remove(sideMenu));
            Transitions.fadeOut(translucentBackground, event -> translucentBackground.setDisable(true));
        };

        loader.<AbstractSideMenu>getController().closeMenuButton.setOnAction(a -> closeSideMenu.run());
        translucentBackground.setOnMouseClicked(value -> closeSideMenu.run());
        sideMenu.requestFocus();
    }

    @FXML
    private void openLanguageSelector(ActionEvent actionEvent) {
        languagePopup.showPopup();
    }

    @FXML
    private void openScriptExecutor(ActionEvent actionEvent) {
        scriptPopup.showPopup();
    }

    @FXML
    private void openUserMenu(ActionEvent actionEvent) {
        userPopup.showPopup();
    }

    @FXML
    public void refreshData(ActionEvent actionEvent) throws IOException {
        SceneControl.getBackendInteractor().refreshData();
    }

    @FXML
    private void getInfo(ActionEvent actionEvent) throws IOException {
        Request request = new Request(CommandList.INFO, new LinkedList<>(),
                SceneControl.getBackendInteractor().getUser());
        Answer answer = SceneControl.getBackendInteractor().sendRequestAndGetAnswer(request);
        SceneControl.openMessage((String) answer.getAnswer());
    }

    @FXML
    private void openAddMenu(ActionEvent actionEvent) throws IOException {
        URL url = SceneControl.class.getClassLoader().getResource("layouts/auxiliary/side/AddMenu.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        openSideMenu(loader);
    }

    @FXML
    private void openRemoveMenu(ActionEvent actionEvent) throws IOException {
        URL url = SceneControl.class.getClassLoader().getResource("layouts/auxiliary/side/RemoveMenu.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        openSideMenu(loader);
    }

    @FXML
    private void resetFilters(ActionEvent actionEvent) {
        for (FilterPopup<RouteProperty, ?> filterPopup : filters) {
            filterPopup.resetFilter();
        }
    }

    @FXML
    private void openVisualization(ActionEvent actionEvent) {
        SceneControl.openVisualization();
    }
}
