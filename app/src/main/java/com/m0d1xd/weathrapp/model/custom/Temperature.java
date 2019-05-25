package com.m0d1xd.weathrapp.model.custom;

import android.os.Parcel;
import android.os.Parcelable;

public class Temperature implements Parcelable {
    public final String name;

    public Temperature(String name) {
        this.name = name;
    }

    protected Temperature(Parcel in) {
        name = in.readString();
    }

    public static final Creator<Temperature> CREATOR = new Creator<Temperature>() {
        @Override
        public Temperature createFromParcel(Parcel in) {
            return new Temperature(in);
        }

        @Override
        public Temperature[] newArray(int size) {
            return new Temperature[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
