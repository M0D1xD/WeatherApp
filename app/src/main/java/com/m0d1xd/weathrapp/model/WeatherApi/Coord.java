package com.m0d1xd.weathrapp.model.WeatherApi;

import com.google.gson.annotations.SerializedName;

public class Coord {
    @SerializedName("lat")
    private Double lat;
    @SerializedName("lon")
    private Double lon;

    public Coord() {
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "Coord{" +
                "lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
