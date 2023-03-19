package com.example.p0431_simplelistchoice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "myLogs";

    private ListView listView;
    private String[] names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
//        устанавливаем режим выбора пунктов списка
//        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE); // выбрать можно только один элемент из списка
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

//        создаем адаптер, используя массив из файла ресурсов
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.names, // массив из файла ресурсов
//                android.R.layout.simple_list_item_single_choice
                android.R.layout.simple_list_item_multiple_choice
        );
//        добавляем адаптер списку
        listView.setAdapter(adapter);

//        получаем массив из файла ресурсов
        names = getResources().getStringArray(R.array.names);

        Button btnChecked = findViewById(R.id.btnChecked);
//        при нажатии на кнопку пишем в лог выделенный элемент
        btnChecked.setOnClickListener(view -> {
//            Log.d(LOG_TAG, "checked: " + names[listView.getCheckedItemPosition()])
            Log.d(LOG_TAG, "checked: ");

            SparseBooleanArray sparseBooleanArray = listView.getCheckedItemPositions();
            for(int i = 0; i < sparseBooleanArray.size(); i++) {
                int key = sparseBooleanArray.keyAt(i);

                Log.d(LOG_TAG, "key = " + key);
                if (sparseBooleanArray.get(key))
                    Log.d(LOG_TAG, names[key]);
            }
            sparseBooleanArray.clear();
        });
    }
}