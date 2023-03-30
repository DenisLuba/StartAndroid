package com.example.p0841_handlerrunnable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

/**
 * P0841_HandlerRunnable
 */

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "myLogs";

    private static final int MAX = 100;
    private int count;

    private ProgressBar pbCount;
    private TextView tvInfo;
    private CheckBox cbInfo;

    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler(Looper.myLooper());

        pbCount = findViewById(R.id.pbCount);
        pbCount.setMax(MAX);
        pbCount.setProgress(0);

        tvInfo = findViewById(R.id.tvInfo);

        cbInfo = findViewById(R.id.cbInfo);
        cbInfo.setOnCheckedChangeListener(this::onCheckedChanged);

        new Thread(this::run).start(); // new Thread(Runnable)
    }

    private void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            tvInfo.setVisibility(View.VISIBLE);
//                показываем информацию
            handler.post(showInfo); // Handler.post(Runnable)
        } else {
            tvInfo.setVisibility(View.GONE);
//                отменяем показ информации
            handler.removeCallbacks(showInfo); // Handler.removeCallbacks(Runnable)
        }
    }

    //        как будто реализация лямбды для Runnable.run()
    private void run() {
        try {
            for (count = 1; count < MAX; count++) {
                TimeUnit.MILLISECONDS.sleep(100);
//            обновляем ProgressBar
                handler.post(this::updateProgress); // Handler.post(Runnable)
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//        обновление ProgressBar
//        как будто реализация лямбды для Runnable.run()
    private void updateProgress() {
        pbCount.setProgress(count);
    }

//        показ информации
//        Через лямбду не получится: надо создать объект
    public Runnable showInfo = new Runnable() {
        @Override
        public void run() {
            Log.d(LOG_TAG, "showInfo");
            String text = "Count = " + count;
            tvInfo.setText(text);
//        планирует сам себя через 1000 мсек
//        public final boolean postDelayed(Runnable r,long delayMillis)
            handler.postDelayed(showInfo, 1000);
        }
    };
}