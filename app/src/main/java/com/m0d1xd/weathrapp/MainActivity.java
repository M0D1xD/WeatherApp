package com.m0d1xd.weathrapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.m0d1xd.weathrapp.Remote.RetrofitInstance;
import com.m0d1xd.weathrapp.Remote.WeatherService;
import com.m0d1xd.weathrapp.model.WeatherApi.City;
import com.m0d1xd.weathrapp.model.WeatherApi.WeatherResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION = 1;
    private static final int CITIES_COUNT = 20;
    private LocationManager locationManager;
    private static final String TAG = "MainActivity";

    public static Double latitude, longitude;

    private BottomNavigationView bottomNavigationView;
    private NavController navController;

    private Call<WeatherResponse> call;
    public static WeatherService mServices;
    public static List<City> Cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            initViews();
        }
    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        navController = Navigation.findNavController(this, R.id.fragment);
        mServices = RetrofitInstance.getRetrofitInstance();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_map:
                        navController.navigate(R.id.action_map);
                        break;
                    case R.id.nav_cities:
                        navController.navigate(R.id.action_cities);
                        break;
                }
                return true;
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_LOCATION);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Default Value for Rostov
            latitude = 47.2357;
            longitude = 39.7015;

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();

            } else if (location1 != null) {
                latitude = location1.getLatitude();
                longitude = location1.getLongitude();

            } else if (location2 != null) {
                latitude = location2.getLatitude();
                longitude = location2.getLongitude();
            }
        }
        if (longitude != null && latitude != null) {
            call = mServices.getWeather(getString(R.string.weather_api),
                    String.valueOf(latitude),
                    String.valueOf(longitude),
                    CITIES_COUNT);
            call.enqueue(new Callback<WeatherResponse>() {
                @Override
                public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {

                    if (response.isSuccessful()) {
                        if (response.body().getCode().equals("200")) {
                            Cities = response.body().getList();
                        } else {
                            Log.d(TAG, "onResponse: " + response.body().getCode());
                        }
                    } else {
                        Log.d(TAG, "onResponse: " + response.body().getCode());
                        Log.d(TAG, "onResponse: " + response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<WeatherResponse> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                }
            });
        }
    }


}
