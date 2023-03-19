package com.example.p0591_datepickerdialog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private final int DIALOG_DATE = 1;
    private TextView tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDate = findViewById(R.id.tvDate);
        tvDate.setOnClickListener(view -> showDialog(DIALOG_DATE));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_DATE) {
            Calendar calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("Europe/Moscow"));
            calendar.setTime(new Date());

            OnDateSetListener on_OK_Dialog_Listener = (datePicker, year, monthOfYear, dayOfMonth) -> {
                DateFormat formatter = new SimpleDateFormat("dd/MM/y", Locale.getDefault());
                tvDate.setText(formatter.format(calendar.getTime()));
            };

            int myYear = calendar.get(Calendar.YEAR);
            int myMonth = calendar.get(Calendar.MONTH);
            int myDay = calendar.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(this, on_OK_Dialog_Listener, myYear, myMonth, myDay);
        }
        return super.onCreateDialog(id);
    }
}