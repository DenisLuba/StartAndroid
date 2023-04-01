package com.example.p0881_asynctaskresult;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "myLogs";

    private MyTask myTask;
    private TextView tvInfo;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInfo = findViewById(R.id.tvInfo);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        findViewById(R.id.btnStart).setOnClickListener(view -> {
            myTask = new MyTask();
            myTask.execute();
        });
        findViewById(R.id.btnGet).setOnClickListener(view -> showResult());
    }

    private void showResult() {
        if (myTask == null) return;

        int result = -1;

        try {
            Log.d(LOG_TAG, "Try to get result");
            result = myTask.get();
            result = myTask.get(1, TimeUnit.SECONDS);
            Log.d(LOG_TAG, "'get()' returns " + result);
            Toast.makeText(this, "'get()' returns " + result, Toast.LENGTH_LONG).show();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            Log.d(LOG_TAG, "'get()' timeout, result = " + result);
            e.printStackTrace();
        }
    }

    class MyTask extends AsyncTask<Void, Void, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            String text = "Begin";
            tvInfo.setText(text);
            Log.d(LOG_TAG, text);
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100500;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            String text = "End. Result = " + result;
            tvInfo.setText(text);
            Log.d(LOG_TAG, text);
            progressBar.setVisibility(View.GONE);
        }
    }
}