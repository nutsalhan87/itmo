package gui.controllers.main;

import client.locales.LocaleManager;
import general.Answer;
import general.CommandList;
import general.Request;
import general.route.RouteProperty;
import gui.POI;
import gui.SceneControl;
import gui.TextDrawable;
import gui.Transitions;
import gui.controllers.auxiliary.side.AbstractSideMenu;
import gui.controllers.auxiliary.side.EditMenuController;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class VisualizationController implements Initializable, TextDrawable {
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private AnchorPane poiMap;
    @FXML
    private ImageView dagestanBackground;
    @FXML
    private Label fromNameLabelVisualization;
    @FXML
    private Label ownerAndCreationDateLabel;
    @FXML
    private Label nameLabelVisualization;
    @FXML
    private Label fromLabelVisualization;
    @FXML
    private Label toLabelVisualization;
    @FXML
    private Label distanceLabelVisualization;
    @FXML
    private BorderPane rightSide;

    private Image dagestanMap;
    private ObservableList<POI> poi;
    private ObjectProperty<RouteProperty> selectedRoute;
    private UserColorManager userColorManager;
    private StackPane translucentBackground;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LocaleManager.addListener(this);
        drawText();
        dagestanMap = new Image("images/dagestanMap.png");
        poi = FXCollections.observableArrayList();
        userColorManager = new UserColorManager();

        rightSide.heightProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(this::redrawVisual));
        rightSide.widthProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(this::redrawVisual));

        selectedRoute = new SimpleObjectProperty<>();
        selectedRoute.addListener((observable, oldValue, newValue) -> drawText());

        poi.addListener((ListChangeListener<? super POI>) observable -> redrawPOI());

        SceneControl.getBackendInteractor().getData().
                addListener((ListChangeListener<? super RouteProperty>) observable -> refreshData(new ActionEvent()));
        refreshData(new ActionEvent());

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
        if (selectedRoute == null || selectedRoute.get() == null) {
            ownerAndCreationDateLabel.setText("");
            nameLabelVisualization.setText(LocaleManager.getString("selectPoint"));
            fromLabelVisualization.setText("");
            toLabelVisualization.setText("");
            distanceLabelVisualization.setText("");
            fromNameLabelVisualization.setText("");
        } else {
            ownerAndCreationDateLabel.setText(LocaleManager.getString("pointCreator") + ": " +
                    selectedRoute.get().getOwner().get() + "\n " + LocaleManager.getString("creationDate") + ": " +
                    LocaleManager.getDate(selectedRoute.get().getCreationDate().get()));
            nameLabelVisualization.setText(selectedRoute.get().getName().get());
            fromLabelVisualization.setText(LocaleManager.getString("from") + ": XYZ - (" + selectedRoute.get().getFromX().get() +
                    "; " + selectedRoute.get().getFromY().get() + "; " + selectedRoute.get().getFromZ().get() + ")");
            fromNameLabelVisualization.setText("    " + selectedRoute.get().getFromName().get());
            toLabelVisualization.setText(LocaleManager.getString("to") + ": XYZ - (" + selectedRoute.get().getToX().get() +
                    "; " + selectedRoute.get().getToY().get() + "; " + selectedRoute.get().getToZ().get() + ")");
            distanceLabelVisualization.setText(LocaleManager.getString("distance") + ": " + selectedRoute.get().getDistance().get());
        }
    }

    private void redrawVisual() {
        if (dagestanBackground.getImage() == null) {
            dagestanBackground.setImage(dagestanMap);
        }
        dagestanBackground.setFitHeight(rightSide.getHeight());
        dagestanBackground.setFitWidth(rightSide.getHeight() * (dagestanMap.getWidth()/dagestanMap.getHeight()));
        redrawPOI();
    }

    private void redrawPOI() {
        poiMap.getChildren().clear();
        for (POI point : poi) {
            Canvas canvas = point.getCanvas();

            poiMap.getChildren().add(canvas);
            AnchorPane.setLeftAnchor(canvas,point.getX() * dagestanBackground.getFitWidth() / dagestanMap.getWidth());
            AnchorPane.setTopAnchor(canvas, point.getY() * dagestanBackground.getFitHeight() / dagestanMap.getHeight());
        }
    }

    public void refreshData(ActionEvent actionEvent) {
        ObservableList<POI> newPOI = FXCollections.observableArrayList();
        ObservableList<RouteProperty> data = SceneControl.getBackendInteractor().getData();
        if (data.stream().noneMatch(r -> r.equalsByValues(selectedRoute.get()))) {
            selectedRoute.setValue(null);
        }
        for (RouteProperty route : data) {
            if (route.getX().get() < POI.MIN_X || route.getX().get() > POI.MAX_X ||
                    route.getY().get() < POI.MIN_Y || route.getY().get() > POI.MAX_Y) {
                continue;
            }
            POI point = new POI(route.getX().get(), route.getY().get(), userColorManager.getColor(route.getOwner().get()));
            List<POI> singlePOI = poi.stream().filter(p -> p.equalsWithoutCanvas(point)).collect(Collectors.toList());
            if (singlePOI.size() == 0) {
                point.getCanvas().setOnMouseClicked(mouseEvent -> {
                    selectedRoute.setValue(route);
                    drawText();
                    point.makeActive();
                    for (POI anotherPoint : poi) {
                        if (!anotherPoint.equals(point)) {
                            anotherPoint.makeInactive();
                        }
                    }
                });
                newPOI.add(point);
            } else {
                newPOI.add(singlePOI.get(0));
            }
        }
        poi.setAll(newPOI);
    }

    @FXML
    private void goBack(ActionEvent actionEvent) {
        SceneControl.openWorkspace();
    }

    @FXML
    private void editOrRemove(ActionEvent actionEvent) {
        if (selectedRoute == null || selectedRoute.get() == null) {
            return;
        }
        URL url = SceneControl.class.getClassLoader().getResource("layouts/auxiliary/side/EditMenu.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        try {
            Answer answer = SceneControl.getBackendInteractor().sendRequestAndGetAnswer(
                    new Request(CommandList.CHECK_ID, Collections.singletonList(selectedRoute.get().getId().getValue()),
                            SceneControl.getBackendInteractor().getUser()));
            if (answer.getAnswer().equals("TRUE")) {
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

                loader.<AbstractSideMenu>getController().closeMenuButton.setOnAction(event -> closeSideMenu.run());
                translucentBackground.setOnMouseClicked(event -> closeSideMenu.run());

                sideMenu.requestFocus();
                loader.<EditMenuController>getController().setFields(selectedRoute.get());
                loader.<EditMenuController>getController().setIdRoute(selectedRoute.get().getId().getValue());
            }
        } catch (IOException ioexc) {
            SceneControl.openMessage(ioexc.getMessage());
        }
    }

    private static class UserColorManager {
        Map<String, Color> userColors;

        public UserColorManager() {
            userColors = new HashMap<>();
        }

        public Color getColor(String owner) {
            if (!userColors.containsKey(owner)) {
                Random random = new Random();
                double red = random.nextDouble();
                double green = random.nextDouble();
                double blue = random.nextDouble();
                Color color = new Color(red, green, blue, 1);
                userColors.put(owner, color);
            }

            return userColors.get(owner);
        }
    }
}
