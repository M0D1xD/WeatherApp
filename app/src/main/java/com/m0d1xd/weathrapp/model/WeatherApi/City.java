package com.m0d1xd.weathrapp.model.WeatherApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class City {
    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("coord")
    private Coord coord;
    @SerializedName("main")
    private Main main;
    @SerializedName("dt")
    private Integer dt;
    @SerializedName("wind")
    private Wind wind;
    @SerializedName("sys")
    private Sys sys;
    @SerializedName("rain")
    private Object rain;
    @SerializedName("snow")
    private Object snow;
    @SerializedName("clouds")
    private Clouds clouds;
    @SerializedName("weather")
    private List<Weather> weather = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public Object getRain() {
        return rain;
    }

    public void setRain(Object rain) {
        this.rain = rain;
    }

    public Object getSnow() {
        return snow;
    }

    public void setSnow(Object snow) {
        this.snow = snow;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coord=" + coord +
                ", main=" + main +
                ", dt=" + dt +
                ", wind=" + wind +
                ", sys=" + sys +
                ", rain=" + rain +
                ", snow=" + snow +
                ", clouds=" + clouds +
                ", weather=" + weather +
                '}';
    }
}
