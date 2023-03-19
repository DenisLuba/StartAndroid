package com.example.p0501_simpleadaptercustom2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    final String ATTRIBUTE_NAME_TEXT = "text";
    final String ATTRIBUTE_NAME_PROGRESS = "progress";
    final String ATTRIBUTE_NAME_BACKGROUND = "background";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        массив данных
        int[] load = { 41, 48, 22, 35, 30, 67, 51, 88 };

//        упаковываем данные в понятную для адаптера структуру
        ArrayList<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> map;
        for (int i = 0; i < load.length; i++) {
            map = new HashMap<>(3);
            map.put(ATTRIBUTE_NAME_TEXT, "Day " + (i + 1) + ". Load: " + load[i] + "%");
            map.put(ATTRIBUTE_NAME_PROGRESS, load[i]);
            map.put(ATTRIBUTE_NAME_BACKGROUND, load[i]);
            data.add(map);
        }

//        массив имен атрибутов, из которых будут читаться данные
        String[] from = { ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_PROGRESS, ATTRIBUTE_NAME_BACKGROUND };
//        массив ID View-компонентов, в которые будут вставлять данные
        int[] to = { R.id.textView, R.id.progressBar, R.id.background };

//        создаем адаптер
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.item, from, to);

//        Указываем адаптеру свой биндер
        adapter.setViewBinder(new MyViewBinder());

//        определяем список и присваиваем ему адаптер
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }


    class MyViewBinder implements SimpleAdapter.ViewBinder {

        private final int red = getResources().getColor(R.color.red);
        private final int orange = getResources().getColor(R.color.orange);
        private final int green = getResources().getColor(R.color.green);

        private final int backgroundID = R.id.background;
        private final int progressBarID = R.id.progressBar;

        @Override
        public boolean setViewValue(View view, Object data, String textRepresentation) {
            return switch(view.getId()) {
                case backgroundID -> {
                    int progress = (Integer) data;
                    if (progress < 40) view.setBackgroundColor(green);
                    else if (progress > 70) view.setBackgroundColor(red);
                    else view.setBackgroundColor(orange);
                    yield true;
                }
                case progressBarID -> {
                    int progress = (Integer) data;
                    ((ProgressBar) view).setProgress(progress);
                    yield true;
                }
                default -> false;
            };
        }
    }
}