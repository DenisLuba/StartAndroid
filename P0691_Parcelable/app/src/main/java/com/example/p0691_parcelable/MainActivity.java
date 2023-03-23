package com.example.p0691_parcelable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(view -> {

            MyObject myObject = new MyObject("text", 1);

            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("key", myObject);

            Log.d(MyObject.LOG_TAG, "startActivity");

            startActivity(intent);
        });
    }
}