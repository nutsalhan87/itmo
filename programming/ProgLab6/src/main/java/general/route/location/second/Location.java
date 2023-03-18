package general.route.location.second;

import java.io.Serializable;

/**
 * The class stores coordinates in three-dimensional space
 */

public class Location implements Comparable<Location>, Serializable {
    private Integer x; //Поле не может быть null
    private int y;
    private float z;

    public Location(Integer x, int y, float z) {
        if (x == null) {
            throw new IllegalArgumentException();
        }

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setX(Integer x) {
        if (x == null) {
            throw new IllegalArgumentException("Неверные данные для объекта Location");
        }

        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public Integer getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    @Override
    public int compareTo(Location anotherLoc) {
        double coordModule1 = Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));
        double coordModule2 = Math.sqrt(Math.pow(anotherLoc.x, 2) + Math.pow(anotherLoc.y, 2) + Math.pow(anotherLoc.z, 2));

        if (coordModule1 > coordModule2) return 1;
        else if (coordModule1 < coordModule2) return -1;
        else return 0;
    }

    @Override
    public String toString() {
        return "Location: " +
                "x=" + x +
                ", y=" + y +
                ", z=" + z;
    }

    public String toJSON(int deep) {
        String paragraph = " ";
        return  "\"second_location\": \n " +
                new String(new char[deep]).replace("\0", paragraph) + "{\n" +
                new String(new char[deep + 2]).replace("\0", paragraph) + "\"Location\":\n" +
                new String(new char[deep + 2]).replace("\0", paragraph) + "{\n" +
                new String(new char[deep + 4]).replace("\0", paragraph) + "\"x\": " + x + ",\n" +
                new String(new char[deep + 4]).replace("\0", paragraph) + "\"y\": " + y + ",\n" +
                new String(new char[deep + 4]).replace("\0", paragraph) + "\"z\": " + z + "\n" +
                new String(new char[deep + 2]).replace("\0", paragraph) + "}\n" +
                new String(new char[deep]).replace("\0", paragraph) + "}";
    }
}
