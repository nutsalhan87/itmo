package general.route;

import javafx.beans.property.*;

import java.util.Date;

// Ненастоящий Property
public class RouteProperty {
    private final IntegerProperty id;
    private final StringProperty name;
    private final ObjectProperty<Date> creationDate;
    private final DoubleProperty distance;
    private final StringProperty owner;
    // Coordinates
    private final DoubleProperty x;
    private final IntegerProperty y;
    // first.Location
    private final DoubleProperty fromX;
    private final LongProperty fromY;
    private final DoubleProperty fromZ;
    private final StringProperty fromName;
    //second.Location
    private final IntegerProperty toX;
    private final IntegerProperty toY;
    private final FloatProperty toZ;

    public RouteProperty(Route route) {
        id = new SimpleIntegerProperty(route.getId());
        name = new SimpleStringProperty(route.getName());
        creationDate = new SimpleObjectProperty<>(route.getCreationDate());
        distance = new SimpleDoubleProperty(route.getDistance());
        owner = new SimpleStringProperty(route.getOwner());
        x = new SimpleDoubleProperty(route.getCoordinates().getX());
        y = new SimpleIntegerProperty(route.getCoordinates().getY());
        fromX = new SimpleDoubleProperty(route.getFrom().getX());
        fromY = new SimpleLongProperty(route.getFrom().getY());
        fromZ = new SimpleDoubleProperty(route.getFrom().getZ());
        fromName = new SimpleStringProperty(route.getFrom().getName());
        toX = new SimpleIntegerProperty(route.getTo().getX());
        toY = new SimpleIntegerProperty(route.getTo().getY());
        toZ = new SimpleFloatProperty(route.getTo().getZ());
    }

    public IntegerProperty getId() {
        return id;
    }

    public StringProperty getName() {
        return name;
    }

    public ObjectProperty<Date> getCreationDate() {
        return creationDate;
    }

    public DoubleProperty getDistance() {
        return distance;
    }

    public StringProperty getOwner() {
        return owner;
    }

    public DoubleProperty getX() {
        return x;
    }

    public IntegerProperty getY() {
        return y;
    }

    public DoubleProperty getFromX() {
        return fromX;
    }

    public LongProperty getFromY() {
        return fromY;
    }

    public DoubleProperty getFromZ() {
        return fromZ;
    }

    public StringProperty getFromName() {
        return fromName;
    }

    public IntegerProperty getToX() {
        return toX;
    }

    public IntegerProperty getToY() {
        return toY;
    }

    public FloatProperty getToZ() {
        return toZ;
    }

    public boolean equalsByValues(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RouteProperty that = (RouteProperty) o;

        if (id != null ? id.get() != that.id.get() : that.id != null) return false;
        if (name != null ? !name.get().equals(that.name.get()) : that.name != null) return false;
        if (creationDate != null ? !creationDate.get().equals(that.creationDate.get()) : that.creationDate != null) return false;
        if (distance != null ? distance.get() != that.distance.get() : that.distance != null) return false;
        if (owner != null ? !owner.get().equals(that.owner.get()) : that.owner != null) return false;
        if (x != null ? x.get() != that.x.get() : that.x != null) return false;
        if (y != null ? y.get() != that.y.get() : that.y != null) return false;
        if (fromX != null ? fromX.get() != that.fromX.get() : that.fromX != null) return false;
        if (fromY != null ? fromY.get() != that.fromY.get() : that.fromY != null) return false;
        if (fromZ != null ? fromZ.get() != that.fromZ.get() : that.fromZ != null) return false;
        if (fromName != null ? !fromName.get().equals(that.fromName.get()) : that.fromName != null) return false;
        if (toX != null ? toX.get() != that.toX.get() : that.toX != null) return false;
        if (toY != null ? toY.get() != that.toY.get() : that.toY != null) return false;
        return toZ != null ? toZ.get() == that.toZ.get() : that.toZ == null;
    }

    @Override
    public String toString() {
        return "RouteProperty{" +
                "id=" + id +
                ", name=" + name +
                ", creationDate=" + creationDate +
                ", distance=" + distance +
                ", owner=" + owner +
                ", x=" + x +
                ", y=" + y +
                ", fromX=" + fromX +
                ", fromY=" + fromY +
                ", fromZ=" + fromZ +
                ", fromName=" + fromName +
                ", toX=" + toX +
                ", toY=" + toY +
                ", toZ=" + toZ +
                '}';
    }
}
