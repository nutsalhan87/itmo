package org.nutsalhan87.web3;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "SHOT_HISTORY")
public class Shot implements Serializable {
    private long id;
    private String date;
    private int time;
    private float x;
    private float y;
    private float r;
    private boolean result;

    protected Shot() {
        this.date = Clock.formatter.format(new Date());
        this.time = 0;
        this.x = 0;
        this.y = 0;
        this.r = 2;
        this.result = false;
    }

    public Shot(String date, int time, float x, float y, float r, boolean result) {
        this.date = date;
        this.time = time;
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public long getId() {
        return id;
    }

    protected void setId(long id) {
        this.id = id;
    }

    @Column(name = "EVENT_DATE")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Column(name = "EVENT_TIME")
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
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
