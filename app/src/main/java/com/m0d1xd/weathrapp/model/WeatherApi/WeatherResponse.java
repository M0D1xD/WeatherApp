package com.m0d1xd.weathrapp.model.WeatherApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("count")
    private int count;

    @SerializedName("cod")
    private String code;

    @SerializedName("list")
    private List<City> list;

    public WeatherResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<City> getList() {
        return list;
    }

    public void setList(List<City> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "WeatherResponse{" +
                "message='" + message + '\'' +
                ", count=" + count +
                ", code='" + code + '\'' +
                ", list=" + list +
                '}';
    }
}
