package com.m0d1xd.weathrapp.model.WeatherApi;

import com.google.gson.annotations.SerializedName;

class Wind {


    @SerializedName("speed")
    private Double speed;
    @SerializedName("deg")
    private Double deg;
    @SerializedName("gust")
    private Double gust;

    public Wind() {
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getDeg() {
        return deg;
    }

    public void setDeg(Double deg) {
        this.deg = deg;
    }

    public Double getGust() {
        return gust;
    }

    public void setGust(Double gust) {
        this.gust = gust;
    }

    @Override
    public String toString() {
        return "Wind{" +
                "speed=" + speed +
                ", deg=" + deg +
                ", gust=" + gust +
                '}';
    }
}
