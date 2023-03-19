package com.example.p0231_oneactivitystate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button go_to_two_from_main;
    final int BUTTON_ID = R.id.go_to_two_from_main;
    final String TAG = "lifecycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "MainActivity created");
        go_to_two_from_main = findViewById(BUTTON_ID);
        go_to_two_from_main.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case BUTTON_ID:
                Intent intent = new Intent(this, TwoActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "MainActivity restarted");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "MainActivity started");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "MainActivity resumed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "MainActivity paused");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "MainActivity stopped");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MainActivity destroyed");
    }
}