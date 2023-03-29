package com.example.p0811_handlersimplemessage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "myLogs";

    private static final int STATUS_NONE = 0;          // нет подключения
    private static final String NONE = "Not connected";
    private static final int STATUS_CONNECTING = 1;    // подключаемся
    private static final String CONNECTING = "Connecting";
    private static final int STATUS_CONNECTED = 2;     // подключено
    private static final String CONNECTED = "Connected";

    private Handler handler;

    private TextView tvStatus;
    private ProgressBar pbConnect;
    private Button btnConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tvStatus);
        pbConnect = findViewById(R.id.pbConnect);
        btnConnect = findViewById(R.id.btnConnect);

        btnConnect.setOnClickListener(this::onClick);

        handler = getHandler(Looper.getMainLooper());

        handler.sendEmptyMessage(STATUS_NONE);
    }

    private void onClick(View view) {
        Thread thread = new Thread(() -> {
            try {
//            устанавливаем подключение
                handler.sendEmptyMessage(STATUS_CONNECTING);
                TimeUnit.SECONDS.sleep(2);
//            установлено
                handler.sendEmptyMessage(STATUS_CONNECTED);
//            выполняется какая-то работа
                TimeUnit.SECONDS.sleep(3);
//            разрываем подключение
                handler.sendEmptyMessage(STATUS_NONE);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private Handler getHandler(Looper myLooper) {

        return new Handler(myLooper) {
            @Override
            public void handleMessage(android.os.Message message) {

                switch (message.what) {
                    case STATUS_NONE -> {
                        btnConnect.setEnabled(true);
                        tvStatus.setText(NONE);
                    }
                    case STATUS_CONNECTING -> {
                        btnConnect.setEnabled(false);
                        pbConnect.setVisibility(View.VISIBLE);
                        tvStatus.setText(CONNECTING);
                    }
                    case STATUS_CONNECTED -> {
                        pbConnect.setVisibility(View.GONE);
                        tvStatus.setText(CONNECTED);
                    }
                }
            }
        };
    }
}