package com.example.p0261_intentfilter2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button time, date;
    private final int DATE = R.id.date;
    private final int TIME = R.id.time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        time = findViewById(TIME);
        date = findViewById(DATE);
        time.setOnClickListener(this);
        date.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()) {
            case TIME:
                intent = new Intent("info.intent.action.time");
                startActivity(intent);
                break;
            case DATE:
                intent = new Intent("info.intent.action.date");
                startActivity(intent);
                break;
        }
    }
}