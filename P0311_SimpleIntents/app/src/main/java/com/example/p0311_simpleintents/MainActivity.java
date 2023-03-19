package com.example.p0311_simpleintents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int ID_WEB = R.id.button_web;
    private final int ID_MAP = R.id.button_map;
    private final int ID_CALL = R.id.button_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button_web = findViewById(ID_WEB);
        Button button_map = findViewById(ID_MAP);
        Button button_call = findViewById(ID_CALL);

        button_web.setOnClickListener(this);
        button_map.setOnClickListener(this);
        button_call.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch(view.getId()) {
            case ID_WEB:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://developer.android.com"));
                startActivity(intent);
                break;
            case ID_MAP:
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:-0.45609946,-90.26607513"));
                startActivity(intent);
                break;
            case ID_CALL:
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:12345"));
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}