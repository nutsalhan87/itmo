package org.nutsalhan87.web4backend.model;

import jakarta.persistence.*;
import org.nutsalhan87.web4backend.util.Clock;

import java.util.Date;

@Entity
@Table(name = "SHOT_HISTORY")
public class Shot {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(name = "event_date")
    private String date;
    @Column(name = "event_time")
    private int time;
    private float x;
    private float r;
    private float y;
    private boolean result;
    private String username;

    public Shot() {
        this.date = Clock.formatter.format(new Date());
        this.time = 0;
        this.x = 0;
        this.y = 0;
        this.r = 2;
        this.result = false;
        this.username = "";
    }

    public Shot(String date, int time, float x, float y, float r, boolean result, String username) {
        this.date = date;
        this.time = time;
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
