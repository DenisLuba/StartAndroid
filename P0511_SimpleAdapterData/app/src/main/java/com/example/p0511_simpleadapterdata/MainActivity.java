package com.example.p0511_simpleadapterdata;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final int CONTEXT_MENU_DELETE_ID = 1;

    private final String ATTRIBUTE_NAME_TEXT = "text";
    private final String ATTRIBUTE_NAME_IMAGE = "image";

    private ListView listView;
    private SimpleAdapter adapter;
    private List<Map<String, Object>> data;
    private Map<String, Object> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        упаковываем данные в понятную для адаптера структуру
        data = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            map = new HashMap<>(2);
            map.put(ATTRIBUTE_NAME_TEXT, "sometext " + i);
            map.put(ATTRIBUTE_NAME_IMAGE, R.mipmap.ic_launcher);
            data.add(map);
        }

//        массив имен атрибутов, из которых будут читаться данные
        String[] from = { ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_IMAGE };
//        массив ID View-компонентов, в которые будут вставлять данные
        int[] to = { R.id.textView, R.id.imageView };

//        создаем адаптер
        adapter = new SimpleAdapter(this, data, R.layout.item, from, to);

//        определяем список и присваиваем ему адаптер
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
    }

    public void onButtonClick(View view) {
        String text = (String) data.get(data.size() - 1).get(ATTRIBUTE_NAME_TEXT);
        String number = text.replaceFirst(".* (\\d+)", "$1");
        int i = Integer.parseInt(number);
//        создаем новый Map
        map = new HashMap<>(2);
        map.put(ATTRIBUTE_NAME_TEXT, "sometext " + ++i);
        map.put(ATTRIBUTE_NAME_IMAGE, R.mipmap.ic_launcher);
//        добавляем его в коллекцию
        data.add(map);
//        уведомляем, что данные изменились
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CONTEXT_MENU_DELETE_ID, 0, "Удалить запись");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getItemId() == CONTEXT_MENU_DELETE_ID) {
//            получаем инфу о пункте списка
                AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
//            удаляем Map из коллекции, используя позицию пункта в списке
            data.remove(menuInfo.position);
//            уведомляем, что данные изменились
            adapter.notifyDataSetChanged();
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
