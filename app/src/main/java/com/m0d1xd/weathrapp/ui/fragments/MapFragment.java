package com.m0d1xd.weathrapp.ui.fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.m0d1xd.weathrapp.MainActivity;
import com.m0d1xd.weathrapp.R;
import com.m0d1xd.weathrapp.model.WeatherApi.City;
import com.m0d1xd.weathrapp.model.WeatherApi.WeatherResponse;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.m0d1xd.weathrapp.util.Constants.ConvertKtoC;


public class MapFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener, GoogleMap.InfoWindowAdapter {

    private static final String TAG = "MapFragment";
    private MapView mMapView;
    private GoogleMap mGoogleMap;

    private Call<WeatherResponse> call;

    private static final int CITIES_COUNT = 20;
    private static Marker marker; //reference to the search marker

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
        if (MainActivity.Cities != null) {
            mGoogleMap.clear();
            for (City city : MainActivity.Cities) {
                LatLng Coords = new LatLng(city.getCoord().getLat(), city.getCoord().getLon());
                mGoogleMap.addMarker(new MarkerOptions().position(Coords).title(city.getName())).setTag(city);
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(Coords));
                mGoogleMap.moveCamera(CameraUpdateFactory.zoomTo(10));
            }
        } else {
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
                            MainActivity.Cities = (ArrayList<City>) response.body().getList();
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

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return prepareInfoView(marker);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if (marker.getTitle().equals(getString(R.string.text_search)))
            SearchArea(marker);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        MapsInitializer.initialize(getView().getContext());
        mGoogleMap = googleMap;
        mGoogleMap.setOnInfoWindowClickListener(this);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.setOnInfoWindowClickListener(this);
        mGoogleMap.setInfoWindowAdapter(this);
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (marker != null) { //if marker exists (not null or whatever)
                    marker.setPosition(latLng);
                } else {
                    marker = googleMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(getString(R.string.text_search))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_search_weather))
                            .draggable(true));
                }
            }
        });
        setUpMarkers(mGoogleMap);
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    private View prepareInfoView(Marker marker) {

        City city = (City) marker.getTag();
        if (marker.getTitle().equals(getString(R.string.text_search))) {
            return null;
        }

        LinearLayout infoView = new LinearLayout(getContext());
        LinearLayout.LayoutParams infoViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        infoView.setOrientation(LinearLayout.HORIZONTAL);
        infoView.setLayoutParams(infoViewParams);

        ImageView infoImageView = new ImageView(getContext());
        Drawable drawable = getResources().getDrawable(android.R.drawable.ic_dialog_map);
        if (city.getWeather().get(0).getIcon() != null)
            Picasso.get().load(city.getWeather().get(0).getIcon()).placeholder(drawable).into(infoImageView);

        infoView.addView(infoImageView);

        LinearLayout subInfoView = new LinearLayout(getContext());
        LinearLayout.LayoutParams subInfoViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        subInfoView.setOrientation(LinearLayout.VERTICAL);
        subInfoView.setLayoutParams(subInfoViewParams);

        TextView subInfoLat = new TextView(getContext());
        subInfoLat.setText(city.getName());
        subInfoLat.setTextColor(R.color.colorAccent);
        TextView subInfoLnt = new TextView(getContext());

        subInfoLnt.setText(ConvertKtoC(city.getMain().getTemp()) + "°C");
        subInfoLnt.setTextColor(R.color.colorAccent);

        subInfoView.addView(subInfoLat);
        subInfoView.addView(subInfoLnt);
        infoView.addView(subInfoView);

        return infoView;
    }


    void SearchArea(Marker marker) {

        MainActivity.latitude = marker.getPosition().latitude;
        MainActivity.longitude = marker.getPosition().longitude;
        MapFragment.marker = null;
        Call<WeatherResponse> call = MainActivity.mServices.getWeather(getResources()
                        .getString(R.string.weather_api),
                String.valueOf(MainActivity.latitude),
                String.valueOf(MainActivity.longitude),
                CITIES_COUNT);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    mGoogleMap.clear();
                    MainActivity.Cities = (ArrayList<City>) response.body().getList();
                    if (MainActivity.Cities != null) {
                        for (City city : MainActivity.Cities) {
                            LatLng Coords = new LatLng(city.getCoord().getLat(), city.getCoord().getLon());
                            mGoogleMap.addMarker(new MarkerOptions().position(Coords).title(city.getName())).setTag(city);
                            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(Coords));
                            mGoogleMap.moveCamera(CameraUpdateFactory.zoomTo(10));
                        }
                    }
                } else {
                    Log.d(TAG, "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
