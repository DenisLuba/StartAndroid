package com.example.p0421_simplelist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final String[] names = {"Иван", "Марья", "Петр", "Антон", "Даша", "Борис",
    "Костя", "Игорь", "Анна", "Денис", "Андрей"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        находим список
        ListView listView = findViewById(R.id.listView);

//        создаем адаптер
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.my_list_item, names);

//        присваиваем адаптер списку
        listView.setAdapter(adapter);

    }
}