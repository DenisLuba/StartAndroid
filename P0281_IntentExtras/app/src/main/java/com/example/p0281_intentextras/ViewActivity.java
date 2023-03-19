package com.example.p0281_intentextras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        TextView textView = findViewById(R.id.textView);
        Intent intent = getIntent();
        String text = intent.getStringExtra("name");
        textView.setText(text);
    }
}