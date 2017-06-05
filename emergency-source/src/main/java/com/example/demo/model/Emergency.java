package com.example.demo.model;

import java.util.Date;

/**
 * Created by msalatino on 05/06/2017.
 */
public class Emergency {
    private Location location;
    private Date date;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Emergency{" +
                "location=" + location +
                ", date=" + date +
                '}';
    }
}
