package com.m0d1xd.weathrapp.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m0d1xd.weathrapp.MainActivity;
import com.m0d1xd.weathrapp.R;
import com.m0d1xd.weathrapp.adapter.ExpandableRecyclerAdapter;
import com.m0d1xd.weathrapp.model.WeatherApi.City;
import com.m0d1xd.weathrapp.model.custom.Temperature;

import java.util.ArrayList;
import java.util.List;

import static com.m0d1xd.weathrapp.util.Constants.ConvertKtoC;


public class CitiesFragment extends Fragment {
    private ExpandableRecyclerAdapter adapter;

    public CitiesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cities, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<City> companies = new ArrayList<>();
        if (MainActivity.Cities != null) {
            for (City c : MainActivity.Cities) {
                List<Temperature> list = new ArrayList<>();
                list.add(new Temperature("Current Temperature : " + ConvertKtoC(c.getMain().getTemp()) + " °C"));
                companies.add(new City(c.getName(), list));
            }

            adapter = new ExpandableRecyclerAdapter(companies);
            RecyclerView rv_cities = view.findViewById(R.id.rv_cities);
            rv_cities.setLayoutManager(new LinearLayoutManager(getContext()));
            rv_cities.setAdapter(adapter);

        }
    }
}