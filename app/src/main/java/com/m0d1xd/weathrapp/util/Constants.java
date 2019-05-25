package com.m0d1xd.weathrapp.util;

import java.text.DecimalFormat;

public class Constants {
    public static final String CITY_LIST = "city_list";
    public static final String LAST_COORD_LAT = "lat";
    public static final String LAST_COORD_LON = "lon";

    public static Double ConvertKtoC(double Kelvin) {
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        String formatResult = decimalFormat.format((Kelvin - 273.15));
        return Double.parseDouble(formatResult);
    }
}
