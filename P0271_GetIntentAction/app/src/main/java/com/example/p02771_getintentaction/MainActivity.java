package com.example.p02771_getintentaction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int ID_TIME = R.id.time;
    private final int ID_DATE = R.id.date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button time = findViewById(ID_TIME);
        Button date = findViewById(ID_DATE);

        time.setOnClickListener(this);
        date.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case ID_DATE:
                Intent intent = new Intent("info.intent.action.date");
                startActivity(intent);
                break;
            case ID_TIME:
                intent = new Intent("info.intent.action.time");
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}