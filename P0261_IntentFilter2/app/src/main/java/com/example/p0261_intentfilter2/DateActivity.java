package com.example.p0261_intentfilter2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateActivity extends AppCompatActivity {
    private TextView dateText;
    private final int ID_dateText = R.id.dateText;
    SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        dateText = findViewById(ID_dateText);
        String date = dateFormat.format(new Date(System.currentTimeMillis()));
        dateText.setText(date);
    }
}