package com.example.p02771_getintentaction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeActivity extends AppCompatActivity {

    private TextView date_time;
    private final int ID_DATE_TIME = R.id.date_time;

    private SimpleDateFormat format;
    private String pattern_date = "dd MMMM yyyy", pattern_time = "H:m:s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time);

        date_time = findViewById(ID_DATE_TIME);
        Intent intent = getIntent();
        String intent_action = intent.getAction();

        if (intent_action.equals("info.intent.action.date"))
            format = new SimpleDateFormat(pattern_date, Locale.US);
        if (intent_action.equals("info.intent.action.time"))
            format = new SimpleDateFormat(pattern_time, Locale.US);


        String text = format.format(new Date(System.currentTimeMillis()));
        date_time.setText(text);
    }
}