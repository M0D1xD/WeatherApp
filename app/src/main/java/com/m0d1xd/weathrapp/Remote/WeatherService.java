package com.m0d1xd.weathrapp.Remote;

import com.m0d1xd.weathrapp.model.WeatherApi.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("find")
    Call<WeatherResponse> getWeather(@Query("appid") String key,
                                     @Query("lat") String lat,
                                     @Query("lon") String lon,
                                     @Query("cnt") int count);

}
