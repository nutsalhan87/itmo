package gui;

import javafx.animation.ScaleTransition;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class POI {
    public final static double MIN_X = 10;
    public final static double MAX_X = 1422;
    public final static int MIN_Y = 10;
    public final static int MAX_Y = 2038;

    private final Canvas canvas;
    private final double x;
    private final int y;

    public POI(double x, int y, Color color) {
        this.x = x;
        this.y = y;

        canvas = new Canvas(17, 17);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(color);
        graphicsContext.fillOval(0, 0, 15, 15);
        canvas.getStyleClass().add("poi");
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public double getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void makeActive() {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(150), canvas);
        scaleTransition.setFromX(canvas.getScaleX());
        scaleTransition.setFromY(canvas.getScaleY());
        scaleTransition.setToX(1.4);
        scaleTransition.setToY(1.4);
        scaleTransition.playFromStart();
        canvas.setScaleX(1.4);
        canvas.setScaleY(1.4);
    }

    public void makeInactive() {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(150), canvas);
        scaleTransition.setFromX(canvas.getScaleX());
        scaleTransition.setFromY(canvas.getScaleY());
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.playFromStart();
        canvas.setScaleX(1);
        canvas.setScaleY(1);
    }

    public boolean equalsWithoutCanvas(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        POI poi = (POI) o;

        if (Double.compare(poi.x, x) != 0) return false;
        return y == poi.y;
    }
}
