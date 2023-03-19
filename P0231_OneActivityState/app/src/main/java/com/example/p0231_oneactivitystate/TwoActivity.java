package com.example.p0231_oneactivitystate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

public class TwoActivity extends AppCompatActivity implements View.OnClickListener {
    Button go_to_three, go_to_main;
    final int GO_TO_THREE = R.id.go_to_three_from_two;
    final int GO_TO_MAIN = R.id.go_to_main_from_two;
    Intent intent;
    final String TAG = "lifecycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        Log.d(TAG, "TwoActivity created");
        go_to_three = findViewById(GO_TO_THREE);
        go_to_main = findViewById(GO_TO_MAIN);
        go_to_three.setOnClickListener(this);
        go_to_main.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case GO_TO_THREE:
                intent = new Intent(this, ThreeActivity.class);
                startActivity(intent);
                break;
            case GO_TO_MAIN:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "TwoActivity restarted");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "TwoActivity started");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "TwoActivity resumed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "TwoActivity paused");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "TwoActivity stopped");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "TwoActivity destroyed");
    }
}
