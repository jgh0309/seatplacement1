package com.iot.lee_pc.seatplacement;

import android.os.Parcel;
import android.os.Parcelable;

public class contentForParcel implements Parcelable
{
    String title;
    int season;

    public contentForParcel(String title, int season) {     //매개변수를 받아 parcel을 만든다.
        this.title = title;
        this.season = season;
    }

    public contentForParcel(Parcel parcel){
        this.title = parcel.readString();       //parcel안의 데이터를 읽는다.
        this.season = parcel.readInt();
    }

    public static final Creator CREATOR =       //이 상수를 통해 안드로이드 시스템이 읽기/쓰기를 할 수 있다.
            new Creator() {
                @Override
                public Object createFromParcel(Parcel source) {
                    return new contentForParcel(source);
                }

                @Override
                public Object[] newArray(int size) {
                    return new Object[0];
                }
            };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);        //parcel안의 데이터를 쓴다.
        dest.writeInt(season);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }
}
