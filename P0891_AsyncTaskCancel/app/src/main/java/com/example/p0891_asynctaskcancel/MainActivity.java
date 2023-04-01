package com.example.p0891_asynctaskcancel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

/**
 *  P0891_AsyncTaskCancel
 */

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "myLogs";

    private MyTask myTask;
    private TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInfo = findViewById(R.id.tvInfo);

        findViewById(R.id.btnStart).setOnClickListener(view -> {
            myTask = new MyTask();
            myTask.execute();
        });
        findViewById(R.id.btnCancel).setOnClickListener(view -> cancelTask());
    }

    private void cancelTask() {
        if (myTask != null)
//            если вызываем метод AsincTask.cancel(true),
//            в методе doInBackground() будет InterruptedException для TimeUnit.sleep()
//            если вызываем метод AsincTask.cancel(false),
//            то doInBackground() отработает корректно
            Log.d(LOG_TAG, "cancel result: " + myTask.cancel(true));
//            Log.d(LOG_TAG, "cancel result: " + myTask.cancel(false));
    }

    class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String text = "Begin";
            tvInfo.setText(text);
            Log.d(LOG_TAG, text);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                for (int i = 0; i < 5; i++) {
                    TimeUnit.SECONDS.sleep(1);
//                    if (isCancelled()) return null;
                    Log.d(LOG_TAG, "isCancelled = " + isCancelled());
                }
            } catch (InterruptedException e) {
                Log.d(LOG_TAG, "Interrupted");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            String text = "End";
            tvInfo.setText(text);
            Log.d(LOG_TAG, text);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            String text = "Cancel";
            tvInfo.setText(text);
            Log.d(LOG_TAG, text);
        }
    }
}