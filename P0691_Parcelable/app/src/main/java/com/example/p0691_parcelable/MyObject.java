package com.example.p0691_parcelable;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class MyObject implements Parcelable {

    final static String LOG_TAG = "myLogs";

    String s;
    int i;

//    обычный конструктор
    public MyObject(String s, int i) {
        Log.d(LOG_TAG, "MyObject(String s, int i)");
        this.s = s;
        this.i = i;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        Log.d(LOG_TAG, "writeToParcel");
        parcel.writeString(s);
        parcel.writeInt(i);
    }

    public static final Parcelable.Creator<MyObject> CREATOR = new Parcelable.Creator<MyObject>() {
//        распаковываем объект из Parcel
        @Override
        public MyObject createFromParcel(Parcel in) {
            Log.d(LOG_TAG, "createFromParcel");
            return new MyObject(in);
        }

        @Override
        public MyObject[] newArray(int size) {
            return new MyObject[size];
        }
    };

//    конструктор, считывающий данные из Parcel
    private MyObject(Parcel parcel) {
        Log.d(LOG_TAG, "MyObject(Parcel parcel)");
//        считываем данные в том же порядке, в каком записали в Parcel
        s = parcel.readString();
        i = parcel.readInt();
    }
}
