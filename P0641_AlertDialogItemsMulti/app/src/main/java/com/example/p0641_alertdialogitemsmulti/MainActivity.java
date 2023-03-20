package com.example.p0641_alertdialogitemsmulti;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String LOG_TAG = "myLogs";

    private final int DIALOG_ITEMS = 1;
    private final int DIALOG_CURSOR = 2;

    private final int BTN_ITEMS_ID = R.id.btnItems;
    private final int BTN_CURSOR_ID = R.id.btnCursor;

    private DataBase dataBase;
    private Cursor cursor;

    private DialogInterface.OnClickListener onOKClickListener;
    private DialogInterface.OnMultiChoiceClickListener onCursorMultiChoiceClickListener;
    private DialogInterface.OnMultiChoiceClickListener onItemsMultiChoiceClickListener;

    private String[] data;
    private boolean[] checked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        boolean[] массив из ресурсов
        data = getResources().getStringArray(R.array.data);
        Boolean[] checkedArray = Arrays.stream(getResources().getStringArray(R.array.checked))
                .map(Boolean::valueOf)
                .toArray(Boolean[]::new);
        checked = new boolean[checkedArray.length];
        for (int i = 0; i < checkedArray.length; i++)
            checked[i] = checkedArray[i];

        Button btnItems = findViewById(R.id.btnItems);
        Button btnCursor = findViewById(R.id.btnCursor);

        btnItems.setOnClickListener(this);
        btnCursor.setOnClickListener(this);

//        открываем подключение к БД
        dataBase = new DataBase(this);
        dataBase.open();
        cursor = dataBase.getCursor();
        startManagingCursor(cursor);

//        обработчик для списка массива
        onItemsMultiChoiceClickListener = (dialog, which, isChecked) -> {
            Log.d(LOG_TAG, "ITEMS: which = " + which + ", isChecked = " + isChecked);
        };

//        обработчик для списка курсора
        onCursorMultiChoiceClickListener = (dialog, which, isChecked) -> {
            ListView listView = ((AlertDialog) dialog).getListView();
            Log.d(LOG_TAG, "DB: which = " + which + ", isChecked = " + isChecked);
            dataBase.changeRecord(which, isChecked);
            cursor.requery();
        };

//        обработчик нажатия на кнопку OK
        onOKClickListener = (dialog, which) -> {
            SparseBooleanArray sparseBooleanArray = ((AlertDialog) dialog)
                    .getListView()
                    .getCheckedItemPositions();
            for (int i = 0; i < sparseBooleanArray.size(); i++) {
                int key = sparseBooleanArray.keyAt(i);
                if (sparseBooleanArray.get(key))
                    Log.d(LOG_TAG, "checked: " + key);
            }
        };
    }

    @Override
    public void onClick(View view) {
        int id = switch (view.getId()) {
            case BTN_ITEMS_ID -> DIALOG_ITEMS;
            case BTN_CURSOR_ID -> DIALOG_CURSOR;
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
                builder.setMultiChoiceItems(
                        data,                            // массив строк
                        checked,                         // boolean-массив, определяющий выделенные элементы
                        onItemsMultiChoiceClickListener  // обработчик нажатия
                );
            }
            case DIALOG_CURSOR -> {
                builder.setTitle(R.string.cursor);
                builder.setMultiChoiceItems(
                        cursor,                          // курсор
                        DataBase.COLUMN_CHECKED,         // имя поля выделения (данные о выделении элементов списка)
                        DataBase.COLUMN_TEXT,            // имя поля с текстом (текст, который будет отображен в списке)
                        onCursorMultiChoiceClickListener // обработчик нажатия
                );
            }
        }

        builder.setPositiveButton(R.string.ok, onOKClickListener);
        return builder.create();
    }

    @Override
    protected void onDestroy() {
        dataBase.close();
        super.onDestroy();
    }
}