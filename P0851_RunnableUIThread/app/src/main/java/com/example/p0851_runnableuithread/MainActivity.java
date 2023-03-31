package com.example.p0851_runnableuithread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

/**
 * Если алгоритмы не особо сложны,
 * можно использовать эти методы для выполнения кода в UI-потоке:
 *
 * Activity.runOnUiThread(Runnable)
 * View.post(Runnable)
 * View.postDelayed(Runnable, long)
 *
 * Если же нужны навороты и алгоритм достаточно сложен,
 * то используем Handler.
 */

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "myLogs";

    private TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInfo = findViewById(R.id.tvInfo);

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                runOnUiThread(() -> tvInfo.setText(R.string.runnable_1));
                TimeUnit.SECONDS.sleep(2);
                tvInfo.postDelayed(() -> tvInfo.setText(R.string.runnable_2), 2000);
                tvInfo.post(() -> tvInfo.setText(R.string.runnable_3));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}