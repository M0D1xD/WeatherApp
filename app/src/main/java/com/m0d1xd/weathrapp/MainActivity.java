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

import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;
    private static final String TAG = "MainActivity";

    public static Double latitude, longitude;

    private BottomNavigationView bottomNavigationView;
    private NavController navController;

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


}
