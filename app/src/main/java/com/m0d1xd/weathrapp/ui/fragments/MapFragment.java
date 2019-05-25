package com.m0d1xd.weathrapp.ui.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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


public class MapFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener, GoogleMap.InfoWindowAdapter {

    private static final String TAG = "MapFragment";
    private MapView mMapView;
    private GoogleMap mGoogleMap;

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

    private void setUpMarkers(GoogleMap mGoogleMap) {
        mGoogleMap.clear();
        LatLng point = new LatLng(MainActivity.latitude, MainActivity.longitude);
        mGoogleMap.addMarker(new MarkerOptions().position(point).title("Rostov On Don"));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(point));
        mGoogleMap.moveCamera(CameraUpdateFactory.zoomTo(10));
        if (MainActivity.Cities != null) {
            for (City city : MainActivity.Cities) {
                LatLng Coords = new LatLng(city.getCoord().getLat(), city.getCoord().getLon());
                mGoogleMap.addMarker(new MarkerOptions().position(Coords).title(city.getName())).setTag(city);
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(Coords));
                mGoogleMap.moveCamera(CameraUpdateFactory.zoomTo(10));
            }
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
