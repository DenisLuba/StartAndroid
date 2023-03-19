package com.example.p0361_db_query;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MainActivity extends Activity implements View.OnClickListener {

    private final String LOG_TAG = "myLog";

    private final String TABLE_NAME = "MY_TABLE";
    private final String COLUMN_COUNTRY = "COUNTRY";
    private final String COLUMN_POPULATION = "POPULATION";
    private final String COLUMN_REGION = "REGION";

    private final String[] country = { "Китай", "США", "Бразилия", "Россия", "Япония",
            "Германия", "Египет", "Италия", "Франция", "Канада" };
    private final int[] population = { 1400, 311, 195, 142, 128, 82, 80, 60, 66, 35 };
    private final String[] region = { "Азия", "Америка", "Америка", "Европа", "Азия",
            "Европа", "Африка", "Европа", "Европа", "Америка" };

    private EditText etFunc, etPeople, etRegionPeople;
    private RadioGroup rgSort;

    private final int BTN_ALL_ID = R.id.btnAll;
    private final int BTN_FUNC_ID = R.id.btnFunc;
    private final int BTN_PEOPLE_ID = R.id.btnPeople;
    private final int BTN_SORT_ID = R.id.btnSort;
    private final int BTN_GROUP_ID = R.id.btnGroup;
    private final int BTN_HAVING_ID = R.id.btnHaving;

    private MySQLiteOpenHelper mySQLiteOpenHelper;
    private SQLiteDatabase sqLiteDatabase;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAll = findViewById(BTN_ALL_ID);
        btnAll.setOnClickListener(this);

        Button btnFunc = findViewById(BTN_FUNC_ID);
        btnFunc.setOnClickListener(this);

        Button btnPeople = findViewById(BTN_PEOPLE_ID);
        btnPeople.setOnClickListener(this);

        Button btnSort = findViewById(BTN_SORT_ID);
        btnSort.setOnClickListener(this);

        Button btnGroup = findViewById(BTN_GROUP_ID);
        btnGroup.setOnClickListener(this);

        Button btnHaving = findViewById(BTN_HAVING_ID);
        btnHaving.setOnClickListener(this);

        etFunc = findViewById(R.id.etFunc);
        etPeople = findViewById(R.id.etPeople);
        etRegionPeople = findViewById(R.id.etRegionPeople);

        rgSort = findViewById(R.id.rgSort);

        mySQLiteOpenHelper = new MySQLiteOpenHelper(this);
        // подключаемся к базе
        sqLiteDatabase = mySQLiteOpenHelper.getWritableDatabase();

        // проверка существования записей
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor.getCount() == 0) {
            ContentValues contentValues = new ContentValues();
            // заполним таблицу
            for (int i = 0; i < country.length; i++) {
                contentValues.clear();
                contentValues.put(COLUMN_COUNTRY, country[i]);
                contentValues.put(COLUMN_POPULATION, population[i]);
                contentValues.put(COLUMN_REGION, region[i]);
                Log.d(LOG_TAG, "id = " + sqLiteDatabase.insert(TABLE_NAME, null, contentValues));
            }
        }
        cursor.close();
        mySQLiteOpenHelper.close();
        // эмулируем нажатие кнопки btnAll
        onClick(btnAll);

    }

    public void onClick(View v) {

        // подключаемся к базе
        sqLiteDatabase = mySQLiteOpenHelper.getWritableDatabase();

        // данные с экрана
        String func = etFunc.getText().toString();
        String people = etPeople.getText().toString();
        String regionPeople = etRegionPeople.getText().toString();

        // переменные для query
        String[] columns, selectionArgs;
        String selection, groupBy, having, orderBy = null;

        // курсор
        Cursor cursor = null;

        // определяем нажатую кнопку
        switch (v.getId()) {
            // Все записи
            case BTN_ALL_ID -> {
                Log.d(LOG_TAG, "--- Все записи ---");
                cursor = sqLiteDatabase.query(TABLE_NAME, null, null, null, null, null, null);
            }
            // Функция
            case BTN_FUNC_ID -> {
                Log.d(LOG_TAG, "--- Функция " + func + " ---");
                columns = new String[]{func};
                cursor = sqLiteDatabase.query(TABLE_NAME, columns, null, null, null, null, null);
            }
            // Население больше, чем
            case BTN_PEOPLE_ID -> {
                Log.d(LOG_TAG, "--- Население больше " + people + " ---");
                selection = "POPULATION > ?";
                selectionArgs = new String[]{people};
                cursor = sqLiteDatabase.query(TABLE_NAME, null, selection, selectionArgs, null, null,
                        null);
            }
            // Население по региону
            case BTN_GROUP_ID -> {
                Log.d(LOG_TAG, "--- Население по региону ---");
                columns = new String[]{COLUMN_REGION, "SUM(" + COLUMN_POPULATION + ") AS PEOPLE"};
                groupBy = COLUMN_REGION;
                cursor = sqLiteDatabase.query(TABLE_NAME, columns, null, null, groupBy, null, null);
            }
            // Население по региону больше чем
            case BTN_HAVING_ID -> {
                Log.d(LOG_TAG, "--- Регионы с населением больше " + regionPeople
                        + " ---");
                columns = new String[]{COLUMN_REGION, "SUM(" + COLUMN_POPULATION + ") AS PEOPLE"};
                groupBy = COLUMN_REGION;
                having = "SUM(" + COLUMN_POPULATION + ") > " + regionPeople;
                cursor = sqLiteDatabase.query(TABLE_NAME, columns, null, null, groupBy, having, null);
            }
            // Сортировка
            case BTN_SORT_ID -> {

                final int rbName = R.id.rbName;
                final int rbPeople = R.id.rbPeople;
                final int rbRegion = R.id.rbRegion;

                // сортировка по
                switch (rgSort.getCheckedRadioButtonId()) {
                    // наименование
                    case rbName -> {
                        Log.d(LOG_TAG, "--- Сортировка по стране ---");
                        orderBy = COLUMN_COUNTRY;
                    }
                    // население
                    case rbPeople -> {
                        Log.d(LOG_TAG, "--- Сортировка по населению ---");
                        orderBy = COLUMN_POPULATION;
                    }
                    // регион
                    case rbRegion -> {
                        Log.d(LOG_TAG, "--- Сортировка по региону ---");
                        orderBy = COLUMN_REGION;
                    }
                }
                cursor = sqLiteDatabase.query(TABLE_NAME, null, null, null, null, null, orderBy);
            }
        }

        if (cursor != null
                && cursor.moveToFirst()) {
            String logString;
            do {
                logString = "";
                for (String columnName : cursor.getColumnNames()) {
                    int columnIndex = cursor.getColumnIndex(columnName);
                    logString = logString.concat(columnName + " = "
                            + cursor.getString(columnIndex) + "; ");
                }
                Log.d(LOG_TAG, logString);
            } while (cursor.moveToNext());
            cursor.close();
        } else
            Log.d(LOG_TAG, "Cursor is null");

        mySQLiteOpenHelper.close();
    }

    class MySQLiteOpenHelper extends SQLiteOpenHelper {

        public MySQLiteOpenHelper(Context context) {
            // конструктор суперкласса
            super(context, "myDataBase", null, 1);
        }

        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            Log.d(LOG_TAG, "--- onCreate database ---");
            // создаем таблицу с полями
            sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                    + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_COUNTRY + " TEXT,"
                    + COLUMN_POPULATION + " INTEGER,"
                    + COLUMN_REGION + " TEXT);");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}