package org.nutsalhan87.web3;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Named
@SessionScoped
public class Clock implements Serializable {
    public static final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy HH:mm:ss");

    public Clock() {}

    public String getClock() {
        return formatter.format(new Date());
    }
}
