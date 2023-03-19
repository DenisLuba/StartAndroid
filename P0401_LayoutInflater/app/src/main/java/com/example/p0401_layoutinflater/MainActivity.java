package com.example.p0401_layoutinflater;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LayoutInflater layoutInflater = getLayoutInflater();

        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        View view1 = layoutInflater.inflate(R.layout.text, linearLayout, false);
        LayoutParams layoutParams1 = view1.getLayoutParams();

        linearLayout.addView(view1);

        Log.d(LOG_TAG, "Class of view1: " + view1.getClass());
        Log.d(LOG_TAG, "Class of LayoutParams of view1: " + layoutParams1.getClass());

        RelativeLayout relativeLayout = findViewById(R.id.relativeLayout);
        View view2 = layoutInflater.inflate(R.layout.text, relativeLayout, true);
        LayoutParams layoutParams2 = view2.getLayoutParams();

        Log.d(LOG_TAG, "Class of view2: " + view2.getClass());
        Log.d(LOG_TAG, "Class of LayoutParams of view2: " + layoutParams2.getClass());
    }
}