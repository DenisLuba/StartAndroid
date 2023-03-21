package com.example.p0661_alertdialogoperations;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "myLogs";
    private final int DIALOG = 1;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnDialog = findViewById(R.id.btnDialog);
        btnDialog.setOnClickListener(this::onClick);
    }

    public void onClick(View view) {
        showDialog(DIALOG);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                method_1();
            }
        }, 2000);

        handler.postDelayed(this::method_2, 4000);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG) {
            Log.d(LOG_TAG, "Create Dialog");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Title");
            builder.setMessage("Message");
            builder.setPositiveButton("Button OK", null);
            dialog = builder.create();

//            обработчик отображения
            dialog.setOnShowListener(dialogInterface ->
                    Log.d(LOG_TAG, "Show the dialog"));

//            обработчик отмены
            dialog.setOnCancelListener(dialogInterface ->
                    Log.d(LOG_TAG, "Cancel the dialog"));

//            обработчик закрытия
            dialog.setOnDismissListener(dialogInterface ->
                    Log.d(LOG_TAG, "Dismiss the dialog"));

            return dialog;
        }
        return super.onCreateDialog(id);
    }

    private void method_1() {
//        dialog.dismiss();
//        dialog.cancel();
//        dialog.hide();
//        dismissDialog(DIALOG);
        removeDialog(DIALOG);
    }

    private void method_2() {
        showDialog(DIALOG);
    }

}