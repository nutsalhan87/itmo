package gui;

import general.route.RouteProperty;
import gui.controllers.auxiliary.FilterMenuController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.PopupControl;
import javafx.scene.control.Skin;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FilterPopup<S, T> extends PopupControl {
    FilteredTableColumn<S, T> filteredTableColumn;
    Function<RouteProperty, T> getter;

    public FilterPopup(FilteredTableColumn<S, T> filteredTableColumn, Function<RouteProperty, T> getter) {
        super();
        this.filteredTableColumn = filteredTableColumn;
        this.getter = getter;
        setAutoHide(true);
        setAutoFix(true);
        setHideOnEscape(true);
        setSkin(createDefaultSkin());
    }

    public ObservableList<RouteProperty> checkFilter(ObservableList<RouteProperty> data) {
        if (((FilterPopupSkin<S, T>)getSkin()).isToggleStartWithActive()) {
            return data.stream().filter(n -> getter.apply(n).toString().startsWith(((FilterPopupSkin<S, T>)getSkin()).getText())).
                    collect(Collectors.toCollection(FXCollections::observableArrayList));
        } else if(((FilterPopupSkin<S, T>)getSkin()).isToggleContainsActive()) {
            return data.stream().filter(n -> getter.apply(n).toString().contains(((FilterPopupSkin<S, T>)getSkin()).getText())).
                    collect(Collectors.toCollection(FXCollections::observableArrayList));
        } else {
            return data;
        }
    }

    public void resetFilter() {
        ((FilterPopupSkin<S, T>)getSkin()).resetFilter();
    }

    public void showPopup() {
        Node node = filteredTableColumn.getGraphic().getParent().getParent();
        if (node.getScene() != null && node.getScene().getWindow() != null) {
            if (!isShowing()) {
                Window parent = node.getScene().getWindow();
                show(parent, parent.getX() + node.localToScene(0.0D, 0.0D).getX() +
                        node.getScene().getX(), parent.getY() + node.localToScene(0.0D, 0.0D).getY()
                        + node.getScene().getY() + node.getLayoutBounds().getHeight());
            }
        } else {
            throw new IllegalStateException("Can not show popup. The node must be attached to a scene/window.");
        }
    }

    protected Skin<?> createDefaultSkin() {
        try {
            return new FilterPopupSkin<>(this);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class FilterPopupSkin<S, T> implements Skin<FilterPopup<S, T>> {
        private final FilterPopup<S, T> filterPopup;
        private final VBox filterMenu;
        private final FilterMenuController filterMenuController;

        public FilterPopupSkin(FilterPopup<S, T> filterPopup) throws IOException {
            this.filterPopup = filterPopup;
            URL url = FilterPopup.class.getClassLoader().getResource("layouts/auxiliary/FilterMenu.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            filterMenu = loader.load();
            filterMenuController = loader.getController();
        }

        @Override
        public FilterPopup<S, T> getSkinnable() {
            return filterPopup;
        }

        @Override
        public Node getNode() {
            return filterMenu;
        }

        @Override
        public void dispose() {}

        public boolean isToggleStartWithActive() {
            return filterMenuController.isToggleStartsWithActive();
        }

        public boolean isToggleContainsActive() {
            return filterMenuController.isToggleContainsActive();
        }

        public String getText() {
            return filterMenuController.getText();
        }

        public void resetFilter() {
            filterMenuController.clearText();
        }
    }
}
