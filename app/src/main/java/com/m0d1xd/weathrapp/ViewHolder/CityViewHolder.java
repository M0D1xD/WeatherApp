package com.m0d1xd.weathrapp.ViewHolder;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.m0d1xd.weathrapp.R;
import com.m0d1xd.weathrapp.model.WeatherApi.City;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class CityViewHolder extends GroupViewHolder {
    private static final String TAG = "CitiesViewHolder";
    private TextView tv_city_name;
    private ImageView arrow;

    public CityViewHolder(View itemView) {
        super(itemView);
        tv_city_name = itemView.findViewById(R.id.tv_city_name);
        arrow = itemView.findViewById(R.id.arrow);
    }

    public void Bind(City city) {
        tv_city_name.setText(city.getName());
    }

    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }


    private void animateExpand() {
        RotateAnimation rotate = new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate = new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }
}
