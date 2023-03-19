package com.example.p0291_simpleactivityresult;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SimpleActivityResult extends AppCompatActivity implements View.OnClickListener {

    EditText et_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_result);

        et_name = findViewById(R.id.et_name);
        Button btn_ok = findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.putExtra("name", et_name.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}