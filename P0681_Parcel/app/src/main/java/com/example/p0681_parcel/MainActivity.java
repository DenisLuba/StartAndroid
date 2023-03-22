package com.example.p0681_parcel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "myLogs";
    private Parcel parcel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        writeParcel();
        readParcel();
    }

    private void writeParcel() {
        parcel = Parcel.obtain();

        String s = "12345";
        double d = 5;
        float f = 4;
        long l = 3;
        int i = 2;
        byte b = 1;

        logWriteInfo("before writing");

        parcel.writeString(s);
        logWriteInfo("String");

        parcel.writeDouble(d);
        logWriteInfo("double");

        parcel.writeFloat(f);
        logWriteInfo("float");

        parcel.writeLong(l);
        logWriteInfo("long");

        parcel.writeInt(i);
        logWriteInfo("int");

        parcel.writeByte(b);
        logWriteInfo("byte");
    }

    private void logWriteInfo(String text) {
        Log.d(LOG_TAG, text + ": dataSize = " + parcel.dataSize());
    }

    private void readParcel() {
        logReadInfo("before reading:");
        parcel.setDataPosition(0);

        logReadInfo("String = " + parcel.readString());
        logReadInfo("double = " + parcel.readDouble());
        logReadInfo("float = " + parcel.readFloat());
        logReadInfo("long = " + parcel.readLong());
        logReadInfo("int = " + parcel.readInt());
        logReadInfo("byte = " + parcel.readByte());
    }

    private void logReadInfo(String text) {
        Log.d(LOG_TAG, text + ": dataPosition = " + parcel.dataPosition());
    }
}