package com.m0d1xd.weathrapp.model.WeatherApi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Main implements Parcelable {

    @SerializedName("temp")
    private Double temp;
    @SerializedName("pressure")
    private Double pressure;
    @SerializedName("humidity")
    private Double humidity;
    @SerializedName("temp_min")
    private Double tempMin;
    @SerializedName("temp_max")
    private Double tempMax;


    protected Main(Parcel in) {
        if (in.readByte() == 0) {
            temp = null;
        } else {
            temp = in.readDouble();
        }
        if (in.readByte() == 0) {
            pressure = null;
        } else {
            pressure = in.readDouble();
        }
        if (in.readByte() == 0) {
            humidity = null;
        } else {
            humidity = in.readDouble();
        }
        if (in.readByte() == 0) {
            tempMin = null;
        } else {
            tempMin = in.readDouble();
        }
        if (in.readByte() == 0) {
            tempMax = null;
        } else {
            tempMax = in.readDouble();
        }
    }

    public static final Creator<Main> CREATOR = new Creator<Main>() {
        @Override
        public Main createFromParcel(Parcel in) {
            return new Main(in);
        }

        @Override
        public Main[] newArray(int size) {
            return new Main[size];
        }
    };

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (temp == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(temp);
        }
        if (pressure == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(pressure);
        }
        if (humidity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(humidity);
        }
        if (tempMin == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(tempMin);
        }
        if (tempMax == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(tempMax);
        }
    }

    @Override
    public String toString() {
        return "Main{" +
                "temp=" + temp +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", tempMin=" + tempMin +
                ", tempMax=" + tempMax +
                '}';
    }


}
