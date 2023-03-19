package com.example.p0261_intentfilter2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeActivity extends AppCompatActivity {
    private TextView timeText;
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
    private final int ID_timeText = R.id.timeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        timeText = findViewById(ID_timeText);
        String time = timeFormat.format(new Date(System.currentTimeMillis()));
        timeText.setText(time);
    }
}