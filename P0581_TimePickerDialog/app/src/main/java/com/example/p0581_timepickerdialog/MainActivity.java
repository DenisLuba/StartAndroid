package com.example.p0581_timepickerdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private final int DIALOG_TIME = 1;
    private int myHour;
    private int myMinute;

    private TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTime = findViewById(R.id.tvTime);
        tvTime.setOnClickListener(view -> {
            Calendar calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("Europe/Moscow"));
            calendar.setTime(new Date());

            myHour = calendar.get(Calendar.HOUR_OF_DAY);
            myMinute = calendar.get(Calendar.MINUTE);

            showDialog(DIALOG_TIME);
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_TIME) {
            OnTimeSetListener myCallBack = (timePicker, hour, minute) -> {
                String text = "Time is " + hour + " hours " + minute + " minutes";
                tvTime.setText(text);
            };
            return new TimePickerDialog(this, myCallBack, myHour, myMinute, true);
        }
        return super.onCreateDialog(id);
    }
}