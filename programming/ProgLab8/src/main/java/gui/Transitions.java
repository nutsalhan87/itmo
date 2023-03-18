package gui;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;

public class Transitions {
    private static final Interpolator interpolator = Interpolator.SPLINE(0, 0.7, 0.7, 1);
    private static final Duration duration = Duration.millis(300);

    public static void fadeIn(Node node) {
        FadeTransition fadeTransition = new FadeTransition(duration, node);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setInterpolator(interpolator);
        fadeTransition.playFromStart();
    }

    public static void fadeOut(Node node, EventHandler<ActionEvent> onFinishedAction) {
        FadeTransition fadeTransition = new FadeTransition(duration, node);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setInterpolator(interpolator);
        fadeTransition.setOnFinished(onFinishedAction);
        fadeTransition.playFromStart();
    }

    public static void scaleIn(Node node) {
        ScaleTransition scaleTransition = new ScaleTransition(duration, node);
        scaleTransition.setFromX(0.6);
        scaleTransition.setFromY(0.6);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.setInterpolator(interpolator);
        scaleTransition.playFromStart();
    }

    public static void scaleOut(Node node, EventHandler<ActionEvent> onFinishedAction) {
        ScaleTransition scaleTransition = new ScaleTransition(duration, node);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(0);
        scaleTransition.setToY(0);
        scaleTransition.setInterpolator(interpolator);
        scaleTransition.setOnFinished(onFinishedAction);
        scaleTransition.playFromStart();
    }

    public static void translateIn(Node node, double fromX, double fromY) {
        TranslateTransition translateTransition = new TranslateTransition(duration, node);
        translateTransition.setFromX(fromX);
        translateTransition.setFromY(fromY);
        translateTransition.setToX(0);
        translateTransition.setToY(0);
        translateTransition.setInterpolator(interpolator);
        translateTransition.playFromStart();
    }

    public static void translateOut(Node node, double toX, double toY, EventHandler<ActionEvent> onFinishedAction) {
        TranslateTransition translateTransition = new TranslateTransition(duration, node);
        translateTransition.setFromX(0);
        translateTransition.setFromY(0);
        translateTransition.setToX(toX);
        translateTransition.setToY(toY);
        translateTransition.setInterpolator(interpolator);
        translateTransition.setOnFinished(onFinishedAction);
        translateTransition.playFromStart();
    }
}
