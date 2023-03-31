package com.example.p0861_asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

/**
 *  P0861_AsyncTask
 *
 * class AsyncTask is Deprecated
 *
 *     doInBackground – будет выполнен в новом потоке, здесь решаем все свои тяжелые задачи.
 *  Т.к. поток не основной - не имеет доступа к UI.
 *
 *     onPreExecute – выполняется перед doInBackground, имеет доступ к UI
 *
 *     onPostExecute – выполняется после doInBackground
 *  (не срабатывает в случае, если AsyncTask был отменен), имеет доступ к UI
 *
 *
 *  4 правила:
 *  - объект AsyncTask должен быть создан в UI-потоке
 * - метод execute должен быть вызван в UI-потоке
 * - не вызывайте напрямую методы onPreExecute, doInBackground, onPostExecute и onProgressUpdate
 * - AsyncTask может быть запущен (execute) только один раз, иначе будет exception
 */

public class MainActivity extends AppCompatActivity {

    private MyTask myTask;
    private TextView tvInfo;
    private ProgressBar pbWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInfo = findViewById(R.id.tvInfo);
        pbWork = findViewById(R.id.pbWork);

        findViewById(R.id.btnStart).setOnClickListener(view -> {
            myTask = new MyTask();
            myTask.execute();
        });
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvInfo.setText(R.string.begin);
            pbWork.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override

        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            tvInfo.setText(R.string.end);
            pbWork.setVisibility(View.GONE);
        }
    }
}