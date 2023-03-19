package com.example.p0321_simplebrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String address_1 = "https://www.google.ru/";
        String address_2 = "http://fandroid.info";

        findViewById(R.id.buttonWebHttps)
                .setOnClickListener(view ->
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse(address_1))));

        findViewById(R.id.buttonWebHttp)
                .setOnClickListener(view ->
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse(address_2))));
    }
}