package com.example.p0611_alertdialogprepare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;
import android.widget.Button;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "myLogs";
    private final int DIALOG = 1;
    private DateFormat formatter;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(this);

        formatter = new SimpleDateFormat("HH:mm:ss", Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
    }

    @Override
    public void onClick(View view) {
        showDialog(DIALOG);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Log.d(LOG_TAG, "onCreateDialog");
        if (id == DIALOG) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Текущее время");
            builder.setMessage(formatter.format(new Date(System.currentTimeMillis())));
            return builder.create();
        }
        return super.onCreateDialog(id);
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        Log.d(LOG_TAG, "onPrepareDialog");
        if (id == DIALOG) {
            ((AlertDialog) dialog).setMessage(formatter.format(new Date()));
        }
    }
}