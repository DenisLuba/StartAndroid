package com.example.p0481_simpleadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

//    имена атрибутов для Map
    private final String ATTRIBUTE_NAME_TEXT = "text";
    private final String ATTRIBUTE_NAME_CHECKED = "checked";
    private final String ATTRIBUTE_NAME_IMAGE = "image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        массивы данных
        String[] texts = getResources().getStringArray(R.array.texts);

        String[] checkedString = getResources().getStringArray(R.array.checked);
        boolean[] checked = new boolean[checkedString.length];
        for (int i = 0; i < checkedString.length; i++)
            checked[i] = checkedString[i].equals("true");

        int image = R.drawable.baseline_child_friendly_24;

//        упаковываем данные в понятную для адаптера структуру
        ArrayList<Map<String, Object>> data = new ArrayList<>(texts.length);
        Map<String, Object> map;
        for (int i = 0; i < texts.length; i++) {
            map = new HashMap<>();
            map.put(ATTRIBUTE_NAME_TEXT, texts[i]);
            map.put(ATTRIBUTE_NAME_CHECKED, checked[i]);
            map.put(ATTRIBUTE_NAME_IMAGE, image);
            data.add(map);
        }

//        массив имен атрибутов, из которых будут читаться данные
        String[] from = { ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_CHECKED, ATTRIBUTE_NAME_IMAGE, ATTRIBUTE_NAME_TEXT };
//        массив ID View-компонентов, в которые будут вставляться данные из from
        int[] to = { R.id.textView, R.id.checkBox, R.id.imageView, R.id.checkBox };

//        создаем адаптер
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.item, from, to);

//        определяем список и присваиваем ему адаптер
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}