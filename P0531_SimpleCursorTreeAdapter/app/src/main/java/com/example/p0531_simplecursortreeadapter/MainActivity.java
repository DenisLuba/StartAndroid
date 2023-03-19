package com.example.p0531_simplecursortreeadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.SimpleCursorTreeAdapter;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView expListView;
    private DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        подключаемся к БД
        dataBase = new DataBase(this);
        dataBase.open();

        log(dataBase);

//        готовим данные по группам для адаптера
        Cursor cursor = dataBase.getCompanyCursor();
        startManagingCursor(cursor);
//        сопоставление данных и View для групп
        String[] groupFrom = { DataBase.COMPANY_COLUMN_NAME };
        int[] groupTo = { android.R.id.text1 };
//        сопоставление данных и View для элементов
        String[] childFrom = { DataBase.PHONE_COLUMN_NAME };
        int[] childTo = { android.R.id.text1 };

//        создаем адаптер и настраиваем список
        SimpleCursorTreeAdapter adapter = new MyAdapter(this, cursor,
                android.R.layout.simple_expandable_list_item_1, groupFrom, groupTo,
                android.R.layout.simple_list_item_1, childFrom, childTo);

        expListView = findViewById(R.id.expListView);
        expListView.setAdapter(adapter);
    }

    void log(DataBase dataBase) {
        Cursor companyCursor = dataBase.getCompanyCursor();
        while (companyCursor.moveToNext()) {
            int column = companyCursor.getColumnIndex(DataBase.COMPANY_COLUMN_NAME);
            Log.d("myLog", companyCursor.getString(column));

            int indexCompanyColumnID = companyCursor.getColumnIndex(DataBase.COMPANY_COLUMN_ID);
            int companyID = companyCursor.getInt(indexCompanyColumnID);
            Cursor phoneCursor = dataBase.getPhoneCursor(companyID);
            while (phoneCursor.moveToNext()) {
                int phone = phoneCursor.getColumnIndex(DataBase.PHONE_COLUMN_NAME);
                Log.d("myLog", "-" + phoneCursor.getString(phone));
            }
        }
    }

    private class MyAdapter extends SimpleCursorTreeAdapter {


        public MyAdapter(Context context, Cursor groupCursor,
                         int groupLayout, String[] groupFrom, int[] groupTo,
                         int childLayout, String[] childFrom, int[] childTo) {
            super(context, groupCursor, groupLayout, groupFrom, groupTo, childLayout, childFrom, childTo);
        }

        @Override
        protected Cursor getChildrenCursor(Cursor groupCursor) {
//            получаем курсор по элементам для конкретной группы
            int indexCompanyColumnID = groupCursor.getColumnIndex(DataBase.COMPANY_COLUMN_ID);
            long companyID = groupCursor.getInt(indexCompanyColumnID);
            return dataBase.getPhoneCursor(companyID);
        }
    }
}