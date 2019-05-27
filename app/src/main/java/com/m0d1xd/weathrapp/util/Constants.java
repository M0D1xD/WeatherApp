package com.m0d1xd.weathrapp.util;


public class Constants {
    public static final String CITY_LIST = "city_list";
    public static final String LAST_COORD_LAT = "lat";
    public static final String LAST_COORD_LON = "lon";

    public static Double ConvertKtoC(double Kelvin) {
        long p = pow(10, 2);
        long l = (long) ((Kelvin - 273.15) * p);
        return (double) l / (double) p;
    }


    private static long pow(long a, int b) {
        long result = 1;
        for (int i = 1; i <= b; i++) {
            result *= a;
        }
        return result;
    }
}
