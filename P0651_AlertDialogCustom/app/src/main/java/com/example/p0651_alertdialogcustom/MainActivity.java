package com.example.p0651_alertdialogcustom;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private final int DIALOG = 1;

    private final int BTN_ADD_ID = R.id.btnAdd;
    private final int BTN_REMOVE_ID = R.id.btnRemove;

    private int selectedButton;

    private DateFormat formatter;
    private final String TIME_ZONE = "Europe/Moscow";

    private LinearLayout view;
    private TextView tvTime;
    private TextView tvCount;

    private List<TextView> textViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnRemove = findViewById(R.id.btnRemove);

        btnAdd.setOnClickListener(this::onClick);
        btnRemove.setOnClickListener(this::onClick);

        textViews = new ArrayList<>(10);
        formatter = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        formatter.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
    }

    public void onClick(View view) {
        selectedButton = view.getId();
        showDialog(DIALOG);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Custom dialog");
//        создаем view из dialog.xml
        view = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog, null);
//        устанавливаем ее, как содержимое тела диалога
        builder.setView(view);
//        находим TexView для отображения кол-ва
        tvCount = view.findViewById(R.id.tvCount);
        return builder.create();
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        if (id != DIALOG) return;

//        Находим TextView для отображения времени и показываем текущее время
        tvTime = dialog.getWindow().findViewById(R.id.tvTime);
        String time = formatter.format(new Date());
        tvTime.setText(time);

        switch(selectedButton) {
//        если была нажата кнопка "Добавить"
            case BTN_ADD_ID -> {
//                создаем новое TextView, добавляем в диалог, указываем текст
                TextView textView = new TextView(this);
                view.addView(textView,
                        new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                textView.setText(String.format(Locale.getDefault(),
                        "TextView %d", (textViews.size() + 1)));
//                добавляем новое TextView в коллекцию
                textViews.add(textView);
            }
//            если была нажата кнопка "Удалить"
            case BTN_REMOVE_ID -> {
//                если коллекция созданных TextView непуста
                if (!textViews.isEmpty()) {
//                    находим в коллекции последний TextView
                    TextView textView = textViews.get(textViews.size() - 1);
//                    удаляем из диалога
                    view.removeView(textView);
//                    удаляем из коллекции
                    textViews.remove(textView);
                }
            }
        }

//        обновляем счетчик
        tvCount.setText(String.format(Locale.getDefault(),
                "Количество TextView = %d", textViews.size()));
    }
}