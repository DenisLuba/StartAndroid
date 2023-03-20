package com.example.p0631_alertdialogitemssingle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "myLogs";

    private final int DIALOG_ITEMS = 1;
    private final int DIALOG_ADAPTER = 2;
    private final int DIALOG_CURSOR = 3;

    private static final int BTI_ITEMS_ID = R.id.btnItems;
    private static final int BTI_ADAPTER_ID = R.id.btnAdapter;
    private static final int BTI_CURSOR_ID = R.id.btnCursor;

    private DataBase dataBase;
    private Cursor cursor;

    private String[] data;

    private OnClickListener onClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = getResources().getStringArray(R.array.data);

        Button btnItems = findViewById(BTI_ITEMS_ID);
        Button btnAdapter = findViewById(BTI_ADAPTER_ID);
        Button btnCursor = findViewById(BTI_CURSOR_ID);

        btnItems.setOnClickListener(this);
        btnAdapter.setOnClickListener(this);
        btnCursor.setOnClickListener(this);

//        открываем подключение к БД
        dataBase = new DataBase(this);
        dataBase.open();
        cursor = dataBase.getCursor();
        startManagingCursor(cursor);

//        обработчик нажатия на пункт списка диалога или кнопку
        onClickListener = (dialog, which) -> {
            ListView listView = ((AlertDialog) dialog).getListView();
            if (which == Dialog.BUTTON_POSITIVE)
//                выводим в лог позицию выбранного элемента
                Log.d(LOG_TAG, "position " + listView.getCheckedItemPosition());
            else
//                выводим в лог позицию нажатого элемента
                Log.d(LOG_TAG, "which = " + which);
        };
    }

    @Override
    public void onClick(View view) {
        int id = switch (view.getId()) {
            case BTI_ITEMS_ID -> DIALOG_ITEMS;
            case BTI_ADAPTER_ID -> DIALOG_ADAPTER;
            case BTI_CURSOR_ID -> DIALOG_CURSOR;
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
                builder.setSingleChoiceItems(data, -1, onClickListener);
            }
//            адаптер
            case DIALOG_ADAPTER -> {
                builder.setTitle(R.string.adapter);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                        android.R.layout.select_dialog_singlechoice, data);
                builder.setSingleChoiceItems(adapter, -1, onClickListener);
            }
//            курсор
            case DIALOG_CURSOR -> {
                builder.setTitle(R.string.cursor);
                builder.setSingleChoiceItems(cursor, -1, DataBase.COLUMN_TEXT, onClickListener);
            }
        }
        builder.setPositiveButton(R.string.ok, onClickListener);
        return builder.create();
    }

//    @Override
//    protected void onPrepareDialog(int id, Dialog dialog) {
//        ((AlertDialog) dialog).getListView().setItemChecked(2, true);
//    }

    @Override
    protected void onDestroy() {
        dataBase.close();
        super.onDestroy();
    }
}