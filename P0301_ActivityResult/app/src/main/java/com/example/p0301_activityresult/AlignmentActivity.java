package com.example.p0301_activityresult;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

public class AlignmentActivity extends AppCompatActivity implements View.OnClickListener {

    private final int ID_LEFT = R.id.btnLeft;
    private final int ID_CENTER = R.id.btnCenter;
    private final int ID_RIGHT = R.id.btnRight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alignment);

        Button btnLeft = findViewById(ID_LEFT);
        Button btnCenter = findViewById(ID_CENTER);
        Button btnRight = findViewById(ID_RIGHT);

        btnLeft.setOnClickListener(this);
        btnCenter.setOnClickListener(this);
        btnRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.putExtra("nameActivity", 2);
        switch(view.getId()) {
            case ID_LEFT:
                intent.putExtra("alignment", Gravity.START);
                break;
            case ID_CENTER:
                intent.putExtra("alignment", Gravity.CENTER);
                break;
            case ID_RIGHT:
                intent.putExtra("alignment", Gravity.END);
                break;
            default:
                break;
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}