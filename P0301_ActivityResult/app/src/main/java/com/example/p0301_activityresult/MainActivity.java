package com.example.p0301_activityresult;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvText;

    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            this::onActivityResult);
    private final int REQUEST_CODE_COLOR = 1;
    private final int REQUEST_CODE_ALIGN = 2;

    private final int ID_COLOR = R.id.btnColor;
    private final int ID_ALIGNMENT = R.id.btnAlignment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnColor = findViewById(ID_COLOR);
        Button btnAlignment = findViewById(ID_ALIGNMENT);
        tvText = findViewById(R.id.tvText);

        btnColor.setOnClickListener(this);
        btnAlignment.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch(view.getId()) {
            case ID_COLOR:
                intent = new Intent(this, ColorActivity.class);
//                startActivityForResult(intent, REQUEST_CODE_COLOR);
                break;
            case ID_ALIGNMENT:
                intent = new Intent(this, AlignmentActivity.class);
//                startActivityForResult(intent, REQUEST_CODE_ALIGN);
                break;
            default:
                break;
        }
        launcher.launch(intent);
    }

    private void onActivityResult(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            assert data != null;
            switch (data.getIntExtra("nameActivity", 0)) {
                case REQUEST_CODE_COLOR:
                    int color = data.getIntExtra("color", Color.WHITE);
                    tvText.setTextColor(color);
                    break;
                case REQUEST_CODE_ALIGN:
                    int gravity = data.getIntExtra("alignment", Gravity.LEFT);
                    tvText.setGravity(gravity);
                    break;
                default:
                    break;
            }
        } else Toast.makeText(this, "Wrong result", Toast.LENGTH_SHORT).show();
    }

//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.d("myLogs", "requestCode = " + requestCode + ", resultCode = " + resultCode);
//        if (resultCode != RESULT_OK) {
//            Toast.makeText(this, "Wrong result", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        switch(requestCode) {
//            case REQUEST_CODE_COLOR:
//                int color = data.getIntExtra("color", Color.WHITE);
//                tvText.setTextColor(color);
//                break;
//            case REQUEST_CODE_ALIGN:
//                int align = data.getIntExtra("alignment", Gravity.CENTER_HORIZONTAL);
//                tvText.setGravity(align);
//                break;
//            default:
//                break;
//        }
//    }
}