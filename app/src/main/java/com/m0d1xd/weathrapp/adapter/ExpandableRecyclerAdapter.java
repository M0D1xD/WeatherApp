package com.m0d1xd.weathrapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m0d1xd.weathrapp.R;
import com.m0d1xd.weathrapp.ViewHolder.CityViewHolder;
import com.m0d1xd.weathrapp.ViewHolder.TempViewHolder;
import com.m0d1xd.weathrapp.model.WeatherApi.City;
import com.m0d1xd.weathrapp.model.custom.Temperature;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class ExpandableRecyclerAdapter extends ExpandableRecyclerViewAdapter<CityViewHolder, TempViewHolder> {

    public ExpandableRecyclerAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public CityViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_city_list_item, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public TempViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_city_temp_details, parent, false);
        return new TempViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(TempViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        Temperature temp = (Temperature) group.getItems().get(childIndex);
        holder.Bind(temp);
    }

    @Override
    public void onBindGroupViewHolder(CityViewHolder holder, int flatPosition, ExpandableGroup group) {
        City city = (City) group;
        holder.Bind(city);
    }
}
