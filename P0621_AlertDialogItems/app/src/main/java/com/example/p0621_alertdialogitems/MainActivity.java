package com.example.p0621_alertdialogitems;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String LOG_TAG = "myLogs";

    private final int DIALOG_ITEMS = 1;
    private final int DIALOG_ADAPTER = 2;
    private final int DIALOG_CURSOR = 3;

    private final int ITEMS_ID = R.id.btnItems;
    private final int ADAPTER_ID = R.id.btnAdapter;
    private final int CURSOR_ID = R.id.btnCursor;

    private int count;
    private DataBase dataBase;
    private Cursor cursor;
    private DialogInterface.OnClickListener myClickListener;

    private final String[] data = { "one", "two", "three", "four" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnItems = findViewById(R.id.btnItems);
        Button btnAdapter = findViewById(R.id.btnAdapter);
        Button btnCursor = findViewById(R.id.btnCursor);

        btnItems.setOnClickListener(this);
        btnAdapter.setOnClickListener(this);
        btnCursor.setOnClickListener(this);

//        открываем подключение к БД
        dataBase = new DataBase(this);
        dataBase.open();
        cursor = dataBase.getCursor();
//        обработчик нажатия на пункт списка диалога
//        выводим в лог позицию нажатого элемента
        myClickListener = (dialogInterface, which) -> Log.d(LOG_TAG, "which " + which);
    }

    @Override
    public void onClick(View view) {
        changeCount();

        int id = switch (view.getId()) {
            case ITEMS_ID -> DIALOG_ITEMS;
            case ADAPTER_ID -> DIALOG_ADAPTER;
            case CURSOR_ID -> DIALOG_CURSOR;
            default -> 0;
        };
        showDialog(id);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        switch (id) {
//            массив
            case DIALOG_ITEMS -> {
                builder.setTitle(R.string.items);
                builder.setItems(data, myClickListener);
            }
//            адаптер
            case DIALOG_ADAPTER -> {
                builder.setTitle(R.string.adapter);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, data);
                builder.setAdapter(adapter, myClickListener);
            }
//            курсор
            case DIALOG_CURSOR -> {
                builder.setTitle(R.string.cursor);
                builder.setCursor(cursor, myClickListener, DataBase.COLUMN_TEXT);
            }
        }

        return builder.create();
    }

//    @Override
//    protected void onPrepareDialog(int id, Dialog dialog) {
////        получаем доступ к адаптеру списка диалога
//        ListAdapter adapter = ((AlertDialog) dialog).getListView().getAdapter();
//
//        switch (id) {
//            case DIALOG_ITEMS, DIALOG_ADAPTER -> {
////                проверка возможности преобразования
//                if (adapter instanceof BaseAdapter) {
////                    преобразование и вызов метода-уведомления о новых данных
//                    ((BaseAdapter) adapter).notifyDataSetChanged();
//                }
//            }
//        }
//    }
//          меняем значение счетчика
    private void changeCount() {
        count++;
//        обновляем массив
        data[3] = String.valueOf(count);
//        обновляем БД
        dataBase.changeRecord(4, String.valueOf(count));
        cursor.requery();
    }

    @Override
    protected void onDestroy() {
        dataBase.close();
        super.onDestroy();
    }
}