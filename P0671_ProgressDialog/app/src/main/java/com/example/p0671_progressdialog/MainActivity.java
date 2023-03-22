/**
 * P0671_ProgressDialog
 */

package com.example.p0671_progressdialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "myLogs";

    private Button btnOrdinary;
    private Button btnHorizontal;

    private static final int BTN_ORDINARY_ID = R.id.btnOrdinary;
    private static final int BTN_HORIZONTAL_ID = R.id.btnHorizontal;

    private static ProgressDialog progressDialog;
    private static Handler handler;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOrdinary = findViewById(BTN_ORDINARY_ID);
        btnHorizontal = findViewById(BTN_HORIZONTAL_ID);

        btnOrdinary.setOnClickListener(this::onClick);
        btnHorizontal.setOnClickListener(this::onClick);
    }

    public void onClick(View view) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Title");
        progressDialog.setMessage("Message");

        switch (view.getId()) {
            case BTN_ORDINARY_ID -> {
//                добавляем кнопку
                progressDialog.setButton(Dialog.BUTTON_POSITIVE, "YES",
                        ((dialogInterface, which) ->
                                Log.d(LOG_TAG, dialogInterface.toString() + " = " + which)));
                progressDialog.show();
            }
            case BTN_HORIZONTAL_ID -> {
//                меняем стиль на индикатор
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//                устанавливаем максимум
                progressDialog.setMax(2148);
//                включаем анимацию ожидания
                progressDialog.setIndeterminate(true);
                progressDialog.show();

                handler = new MyHandler();

                handler.sendEmptyMessageDelayed(0, 2000);
                System.out.println("Hello");
            }
        }
    }

    private static class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
//            выключаем анимацию ожидания
            progressDialog.setIndeterminate(false);
            if (progressDialog.getProgress() < progressDialog.getMax()) {
//                увеличиваем значения индикаторов
                progressDialog.incrementProgressBy(50);
                progressDialog.incrementSecondaryProgressBy(75);
                handler.sendEmptyMessageDelayed(0, 100);
            } else progressDialog.dismiss();
        }
    }
}