package com.example.p0701_saveinstancestate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "myLogs";
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(view -> {
            Toast.makeText(this, "Count = " + ++count, Toast.LENGTH_SHORT).show();

        });

        log("onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();

        log("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        log("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        log("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        log("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        log("onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        log("onRestart");
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("count", count);

        log("onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        count = savedInstanceState.getInt("count");

        log("onRestoreInstanceState");
    }

    private void log(String message) {
        Log.d(LOG_TAG, message);
    }
}