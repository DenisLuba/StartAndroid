package com.example.p0091_onclickbuttons;

import static android.view.Gravity.CENTER;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button1, button2, button3;
    TextView textView;

    private static final String TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "Найдем View-элементы");

        textView = findViewById(R.id.textView);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("Нажата кнопка 1");

                Toast toast = Toast.makeText(MainActivity.this, "Нажата кнопка 1", Toast.LENGTH_LONG);
//                toast.setGravity(CENTER, 0, 0); // в новых версиях этот метод игнорируется
//                LinearLayout toastImage = (LinearLayout) toast.getView();
//                ImageView imageView = new ImageView(MainActivity.this);
//                imageView.setImageResource(R.drawable.ic_baseline_blur_on_24);
//                toastImage.addView(imageView, 0);
                toast.show();
            }
        });

        button2.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Log.d(TAG, "Обработаем нажатие кнопки 2");
        textView.setText("Нажата кнопка 2");
    }

    public void clickButton3(View view) {
        Log.d(TAG, "Обработаем нажатие кнопки 3");
        textView.setText("Нажата кнопка 3");
    }
}