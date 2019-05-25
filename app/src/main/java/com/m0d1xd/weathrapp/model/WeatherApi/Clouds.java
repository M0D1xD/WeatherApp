package com.m0d1xd.weathrapp.model.WeatherApi;

import com.google.gson.annotations.SerializedName;

class Clouds {


    @SerializedName("all")
    private Integer all;

    public Clouds() {
    }

    public Integer getAll() {
        return all;
    }

    public void setAll(Integer all) {
        this.all = all;
    }

    @Override
    public String toString() {
        return "Clouds{" +
                "all=" + all +
                '}';
    }
}
