package com.example.p0831_handlermessagemanage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "myLogs";

    private Handler handler;
    private Handler.Callback callback = (message) -> {

        Log.d(LOG_TAG, "what = " + message.what);
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler(Looper.myLooper(), callback);
        sendMessages();
    }

    private void sendMessages() {

        Log.d(LOG_TAG, "Send messages");

        handler.sendEmptyMessageDelayed(1, 1000);
        handler.sendEmptyMessageDelayed(2, 2000);
        handler.sendEmptyMessageDelayed(3, 3000);
        handler.sendEmptyMessageDelayed(2, 4000);
        handler.sendEmptyMessageDelayed(5, 5000);
        handler.sendEmptyMessageDelayed(2, 6000);
        handler.sendEmptyMessageDelayed(7, 7000);

        handler.removeMessages(2);
    }
}