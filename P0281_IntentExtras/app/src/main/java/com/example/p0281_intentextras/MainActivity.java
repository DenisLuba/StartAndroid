package com.example.p0281_intentextras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button submit = findViewById(R.id.button);
        text = findViewById(R.id.editText);

        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button) {
            Intent intent = new Intent(this, ViewActivity.class);
            intent.putExtra("name", text.getText().toString());
            startActivity(intent);
        }
    }
}