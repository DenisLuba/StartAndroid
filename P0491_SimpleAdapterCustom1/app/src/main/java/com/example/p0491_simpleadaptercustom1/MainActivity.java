package com.example.p0491_simpleadaptercustom1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

//    имена атрибутов для Map
    private final String ATTRIBUTE_NAME_TEXT = "text";
    private final String ATTRIBUTE_NAME_VALUE = "value";
    private final String ATTRIBUTE_NAME_IMAGE = "image";

//    картинки для отображения динамики
    private final int positive = android.R.drawable.arrow_up_float;
    private final int negative = android.R.drawable.arrow_down_float;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        массив данных
        int[] values = { 8, 4, -3, 2, -5, 0, 3, -6, 1, -1 };

//        упаковываем данные в понятную для адаптера структуру
        ArrayList<Map<String, Object>> data = new ArrayList<>(values.length);
        Map<String, Object> map;
        int image;
        for (int i = 0; i < values.length; i++) {
            if (values[i] == 0) image = 0;
            else image = values[i] > 0 ? positive : negative;
            map = new HashMap<>(3);
            map.put(ATTRIBUTE_NAME_TEXT, "Day " + (i + 1));
            map.put(ATTRIBUTE_NAME_VALUE, values[i]);
            map.put(ATTRIBUTE_NAME_IMAGE, image);
            data.add(map);
        }

//        массив имен атрибутов, из которых будут читаться данные
        String[] from ={ ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_VALUE, ATTRIBUTE_NAME_IMAGE };
//        массив ID View-компонентов, в которые будут вставлятся данные
        int[] to = { R.id.textView, R.id.textViewValue, R.id.imageView };

//        сoздаем адаптер
        MySimpleAdapter adapter = new MySimpleAdapter(this, data, R.layout.item, from, to);

//        определяем список и присваиваем ему адаптер
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    class MySimpleAdapter extends SimpleAdapter {

        public MySimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
        }

        @Override
        public void setViewText(TextView textView, String text) {
//            метод суперкласса, который вставляет текст
            super.setViewText(textView, text);
//            если нужный нам TextView, то разрисовываем
            if (textView.getId() == R.id.textViewValue) {
                int value = Integer.parseInt(text);
                if (value > 0) textView.setTextColor(Color.RED);
                if (value < 0) textView.setTextColor(Color.GREEN);
            }
        }

        @Override
        public void setViewImage(ImageView imageView, int value) {
//            метод суперкласса
            super.setViewImage(imageView, value);
//            разрисовываем ImageView
            if (value == negative) imageView.setBackgroundColor(Color.RED);
            if (value == positive) imageView.setBackgroundColor(Color.GREEN);
        }
    }
}