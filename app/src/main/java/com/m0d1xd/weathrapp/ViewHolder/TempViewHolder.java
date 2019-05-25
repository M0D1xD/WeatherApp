package com.m0d1xd.weathrapp.ViewHolder;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.m0d1xd.weathrapp.R;
import com.m0d1xd.weathrapp.model.custom.Temperature;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.text.DecimalFormat;

public class TempViewHolder extends ChildViewHolder {

    private TextView tv_current;

    public TempViewHolder(View itemView) {
        super(itemView);
        tv_current = itemView.findViewById(R.id.tv_current_temp);
    }

    @SuppressLint("SetTextI18n")
    public void Bind(Temperature temperature) {
        tv_current.setText(temperature.toString());
    }

    private Double ConvertKtoC(double Kelvin) {
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        String formatResult = decimalFormat.format((Kelvin - 273.15));
        return Double.parseDouble(formatResult);
    }
}