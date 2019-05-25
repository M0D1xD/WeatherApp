package com.m0d1xd.weathrapp.ui.fragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.m0d1xd.weathrapp.MainActivity;
import com.m0d1xd.weathrapp.R;
import com.m0d1xd.weathrapp.model.WeatherApi.City;
import com.m0d1xd.weathrapp.model.WeatherApi.WeatherResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MapFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener, GoogleMap.InfoWindowAdapter {

    private static final String TAG = "MapFragment";
    private MapView mMapView;
    private GoogleMap mGoogleMap;

    private Call<WeatherResponse> call;

    private static final int CITIES_COUNT = 20;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = view.findViewById(R.id.map);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
        if (mGoogleMap != null)
            setUpMarkers(mGoogleMap);

    }

    private void setUpMarkers(final GoogleMap mGoogleMap) {


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Default Value for Rostov
            MainActivity.latitude = 47.2357;
            MainActivity.longitude = 39.7015;

        } else {
            Location location = MainActivity.locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location location1 = MainActivity.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location location2 = MainActivity.locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (location != null) {
                MainActivity.latitude = location.getLatitude();
                MainActivity.longitude = location.getLongitude();

            } else if (location1 != null) {
                MainActivity.latitude = location1.getLatitude();
                MainActivity.longitude = location1.getLongitude();

            } else if (location2 != null) {
                MainActivity.latitude = location2.getLatitude();
                MainActivity.longitude = location2.getLongitude();
            }
        }
        if (MainActivity.longitude != null && MainActivity.latitude != null) {
            call = MainActivity.mServices.getWeather(getString(R.string.weather_api),
                    String.valueOf(MainActivity.latitude),
                    String.valueOf(MainActivity.longitude),
                    CITIES_COUNT);
            call.enqueue(new Callback<WeatherResponse>() {
                @Override
                public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                    if (response.isSuccessful()) {
                        MainActivity.Cities = response.body().getList();
                        mGoogleMap.clear();
                        if (MainActivity.Cities != null) {
                            for (City city : MainActivity.Cities) {
                                LatLng Coords = new LatLng(city.getCoord().getLat(), city.getCoord().getLon());
                                mGoogleMap.addMarker(new MarkerOptions().position(Coords).title(city.getName())).setTag(city);
                                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(Coords));
                                mGoogleMap.moveCamera(CameraUpdateFactory.zoomTo(10));
                            }
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

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getView().getContext());
        mGoogleMap = googleMap;
        mGoogleMap.setOnInfoWindowClickListener(this);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.setOnInfoWindowClickListener(this);
        mGoogleMap.setInfoWindowAdapter(this);
        setUpMarkers(mGoogleMap);
    }
}
