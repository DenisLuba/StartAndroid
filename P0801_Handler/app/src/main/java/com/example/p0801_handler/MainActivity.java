package com.example.p0801_handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "myLogs";

    private static final int ID_BTN_START = R.id.btnStart;
    private static final int ID_BTN_TEST = R.id.btnTest;

    private Handler handler;
    private TextView tvInfo;
    private Button btnStart;
    private Button btnTest;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInfo = findViewById(R.id.tvInfo);
        progress = findViewById(R.id.progress);

        btnStart = findViewById(ID_BTN_START);
        btnTest = findViewById(ID_BTN_TEST);

        btnStart.setOnClickListener(this::onClick);
        btnTest.setOnClickListener(this::onClick);

        handler = new Handler(Looper.getMainLooper()) {

            @Override
            public void handleMessage(android.os.Message msg) {
//            обновляем TextView
                String text = "Закачано файлов: " + msg.what;
                tvInfo.setText(text);
                if (msg.what == 10) {
                    btnStart.setEnabled(true);
                    progress.setVisibility(View.GONE);
                }
            }
        };
    }

    private void onClick(View view) {
        switch (view.getId()) {
            case ID_BTN_START -> {
                btnStart.setEnabled(false);
                progress.setVisibility(View.VISIBLE);
                Thread thread = new Thread(() -> {
                    for (int i = 1; i < 11; i++) {
//                    долгий процесс
                        downloadFile();
                        handler.sendEmptyMessage(i);
//                    пишем лог
                        Log.d(LOG_TAG, "Закачано файлов: " + i);
                    }
                });
                thread.start();
            }
            case ID_BTN_TEST -> Log.d(LOG_TAG, "Test");
        }
    }

    private void downloadFile() {
//        пауза - 2 секунды
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}