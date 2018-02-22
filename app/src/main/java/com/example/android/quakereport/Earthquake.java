package com.example.android.quakereport;

/**
 * Created by junedahmed on 2/18/18.
 */

public class Earthquake {

    private double magnitude;
    private String location;
    private long time;

    public Earthquake(double magnitude, String location, long time) {
        this.magnitude = magnitude;
        this.location = location;
        this.time = time;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
