package com.example.p0691_parcelable;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Log.d(MyObject.LOG_TAG, "getParcelableExtra");

        MyObject myObject = getIntent().getParcelableExtra("key");

        Log.d(MyObject.LOG_TAG, "myObject: " + myObject.s + ", " + myObject.i);
    }
}
