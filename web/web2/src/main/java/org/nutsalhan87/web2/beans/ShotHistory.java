package org.nutsalhan87.web2.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShotHistory implements Serializable {
    List<Shot> history;

    public ShotHistory() {
        history = new ArrayList<>();
    }

    public void addShot(Shot shot) {
        history.add(shot);
    }

    public List<Shot> getHistory() {
        return history;
    }

    public void setHistory(List<Shot> history) {
        this.history = history;
    }
}
