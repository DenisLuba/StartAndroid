package com.example.p0301_activityresult;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ColorActivity extends AppCompatActivity implements View.OnClickListener {

    private final int ID_RED = R.id.btnRed;
    private final int ID_GREEN = R.id.btnGreen;
    private final int ID_BLUE = R.id.btnBlue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        Button btnRed = findViewById(ID_RED);
        Button btnGreen = findViewById(ID_GREEN);
        Button btnBlue = findViewById(ID_BLUE);

        btnRed.setOnClickListener(this);
        btnGreen.setOnClickListener(this);
        btnBlue.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.putExtra("nameActivity", 1);
        switch (view.getId()) {
            case ID_RED:
                intent.putExtra("color", Color.RED);
                break;
            case ID_GREEN:
                intent.putExtra("color", Color.GREEN);
                break;
            case ID_BLUE:
                intent.putExtra("color", Color.BLUE);
                break;
            default:
                break;
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}