package com.example.p0081_viewbyid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView myTextView = findViewById(R.id.myText);
        myTextView.setText("Android it's easy");

        Button myButton = findViewById(R.id.myButton);
        myButton.setText("My first Button");
        myButton.setEnabled(false);

        CheckBox myCheckBox = findViewById(R.id.myCheckBox);
        myCheckBox.setChecked(true);
    }
}