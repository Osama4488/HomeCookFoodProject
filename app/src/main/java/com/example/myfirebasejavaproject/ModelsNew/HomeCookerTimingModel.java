package com.example.myfirebasejavaproject.ModelsNew;

import android.os.Parcel;
import android.os.Parcelable;

public class HomeCookerTimingModel implements Parcelable {
    private String  HomeCookerTimingDay, HomeCookerOpenTime, HomeCookerCloseTime;

    public HomeCookerTimingModel(String homeCookerTimingDay, String homeCookerOpenTime, String homeCookerCloseTime) {
        HomeCookerTimingDay = homeCookerTimingDay;
        HomeCookerOpenTime = homeCookerOpenTime;
        HomeCookerCloseTime = homeCookerCloseTime;
    }

    public HomeCookerTimingModel() {
    }

    protected HomeCookerTimingModel(Parcel in) {
        HomeCookerTimingDay = in.readString();
        HomeCookerOpenTime = in.readString();
        HomeCookerCloseTime = in.readString();
    }

    public static final Creator<HomeCookerTimingModel> CREATOR = new Creator<HomeCookerTimingModel>() {
        @Override
        public HomeCookerTimingModel createFromParcel(Parcel in) {
            return new HomeCookerTimingModel(in);
        }

        @Override
        public HomeCookerTimingModel[] newArray(int size) {
            return new HomeCookerTimingModel[size];
        }
    };

    public String getHomeCookerTimingDay() {
        return HomeCookerTimingDay;
    }

    public void setHomeCookerTimingDay(String homeCookerTimingDay) {
        HomeCookerTimingDay = homeCookerTimingDay;
    }

    public String getHomeCookerOpenTime() {
        return HomeCookerOpenTime;
    }

    public void setHomeCookerOpenTime(String homeCookerOpenTime) {
        HomeCookerOpenTime = homeCookerOpenTime;
    }

    public String getHomeCookerCloseTime() {
        return HomeCookerCloseTime;
    }

    public void setHomeCookerCloseTime(String homeCookerCloseTime) {
        HomeCookerCloseTime = homeCookerCloseTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(HomeCookerTimingDay);
        dest.writeString(HomeCookerOpenTime);
        dest.writeString(HomeCookerCloseTime);
    }
}
