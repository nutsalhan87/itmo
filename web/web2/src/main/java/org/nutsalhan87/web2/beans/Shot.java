package org.nutsalhan87.web2.beans;

import java.io.Serializable;

public class Shot implements Serializable {
    String date;
    int time;
    int x;
    float y;
    float r;
    boolean result;

    public Shot() {
        this.date = "";
        this.time = 0;
        this.x = 0;
        this.y = 0;
        this.r = 0;
        this.result = false;
    }

    public Shot(String date, int time, int x, float y, float r, boolean result) {
        this.date = date;
        this.time = time;
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
