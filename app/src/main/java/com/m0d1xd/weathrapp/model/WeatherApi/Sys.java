package com.m0d1xd.weathrapp.model.WeatherApi;

import com.google.gson.annotations.SerializedName;

class Sys {


    @SerializedName("country")
    private String country;

    public Sys() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Sys{" +
                "country='" + country + '\'' +
                '}';
    }
}
