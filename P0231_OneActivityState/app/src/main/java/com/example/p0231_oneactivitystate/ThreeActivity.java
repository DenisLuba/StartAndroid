package com.example.p0231_oneactivitystate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ThreeActivity extends AppCompatActivity implements View.OnClickListener {
    Button go_to_two;
    final int GO_TO_TWO = R.id.go_to_two_from_three;
    final String TAG = "lifecycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        Log.d(TAG, "ThreeActivity created");
        go_to_two = findViewById(GO_TO_TWO);
        go_to_two.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case GO_TO_TWO:
                Intent intent = new Intent(this, TwoActivity.class);
                startActivity(intent);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "ThreeActivity restarted");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "ThreeActivity started");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "ThreeActivity resumed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "ThreeActivity paused");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "ThreeActivity stopped");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "ThreeActivity destroyed");
    }
}