package com.example.p0521_simplecursoradapter;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    private static final int CONTEXT_MENU_DELETE_ID = 1;
    private ListView listView;
    private DataBase dataBase;
    private SimpleCursorAdapter adapter;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        открываем подключение к БД
        dataBase = new DataBase(this);
        dataBase.open();

//        получаем курсор
        cursor = dataBase.getCursor();
//        управление жизненным циклом курсора вместе с жизненным циклом активити
        startManagingCursor(cursor);

//        формируем столбцы сопоставления
        String[] from = { DataBase.COLUMN_TEXT, DataBase.COLUMN_IMAGE };
        int[] to = { R.id.textView, R.id.imageView };

//        создааем адаптер и настраиваем список
        adapter = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to);
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

//        добавляем контекстное меню к списку
        registerForContextMenu(listView);
    }

//    обработка нажатия кнопки
    public void onButtonClick(View view) {
//        добавляем запись
        dataBase.addRecord("sometext " + (cursor.getCount() + 1), R.mipmap.ic_launcher);
//        обновляем курсор
        cursor.requery();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo info) {
        super.onCreateContextMenu(menu, view, info);
        menu.add(0, CONTEXT_MENU_DELETE_ID, 0, R.string.delete_record);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CONTEXT_MENU_DELETE_ID) {
//            получаем из пункта контекстного меню данные по пункту списка
            AdapterContextMenuInfo adapter = (AdapterContextMenuInfo) item.getMenuInfo();
//            извлекаем id записи и удаляем соответствующую запись в БД
            dataBase.deleteRecord(adapter.id);
//            обновляем курсор
            cursor.requery();
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
//        закрываем подключение при выходе
        dataBase.close();
        super.onDestroy();
    }
}