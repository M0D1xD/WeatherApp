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

import java.util.ArrayList;
import java.util.List;

import static com.m0d1xd.weathrapp.util.Constants.CITY_LIST;
import static com.m0d1xd.weathrapp.util.Constants.LAST_COORD_LAT;
import static com.m0d1xd.weathrapp.util.Constants.LAST_COORD_LON;


public class MainActivity extends AppCompatActivity {

    public static LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;
    private static final String TAG = "MainActivity";

    public static Double latitude, longitude;

    private BottomNavigationView bottomNavigationView;
    private NavController navController;

    public static WeatherService mServices;
    public static ArrayList<City> Cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (savedInstanceState != null) {
            latitude = savedInstanceState.getDouble(LAST_COORD_LAT);
            longitude = savedInstanceState.getDouble(LAST_COORD_LON);
            Cities = savedInstanceState.getParcelableArrayList(CITY_LIST);
        } else
            initViews();

    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        navController = Navigation.findNavController(this, R.id.fragment);
        mServices = RetrofitInstance.getRetrofitInstance();
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_LOCATION);
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


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Cities = savedInstanceState.getParcelableArrayList(CITY_LIST);
        longitude = savedInstanceState.getDouble(LAST_COORD_LON);
        latitude = savedInstanceState.getDouble(LAST_COORD_LAT);

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(CITY_LIST, Cities);
        outState.putDouble(LAST_COORD_LAT, latitude);
        outState.putDouble(LAST_COORD_LON, longitude);
    }
}
