package com.rpm.caash.model;

import java.io.Serializable;

/**
 * Class for storing latitude and longitude coordinates
 *
 * @author maallen (Mark Allen)
 */
public class Coordinates implements Serializable{

    private double latitude;
    private double longitude;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


}
