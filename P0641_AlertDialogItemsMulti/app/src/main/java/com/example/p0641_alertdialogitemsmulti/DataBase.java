package com.example.p0641_alertdialogitemsmulti;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBase {

    private static final int DB_VERSION = 1;

    private static final String DB_NAME = "myDataBase";
    private static final String DB_TABLE = "myTable";

    private static final String COLUMN_ID = "_id";
    public static final String COLUMN_CHECKED = "checked";
    public static final String COLUMN_TEXT = "text";

    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_CHECKED + " INTEGER, " +
            COLUMN_TEXT + " TEXT);";

    private final Context context;

    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public DataBase(Context context) {
        this.context = context;
    }

//    открыть подключение
    public void open() {
        dataBaseHelper = new DataBaseHelper(context, DB_NAME, null, DB_VERSION);
        database = dataBaseHelper.getWritableDatabase();
    }

//    закрыть подключение
    public void close() {
        if (dataBaseHelper != null) dataBaseHelper.close();
    }

//    получить cursor на все данные из таблицы DB_TABLE
    public Cursor getCursor() {
        return database.query(DB_TABLE, null, null, null, null, null, null);
    }

//    изменить запись в DB_TABLE
    public void changeRecord(int position, boolean isChecked) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CHECKED, isChecked ? 1 : 0);
        database.update(DB_TABLE, values, COLUMN_ID + " = " + (position + 1), null);
    }

//    класс по созданию и управлению БД
    private static class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(Context context, String dbName, CursorFactory factory, int version) {
            super(context, dbName, factory, version);
        }

//        создаем и заполняем БД
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_TABLE);

            ContentValues values = new ContentValues();
            for (int i = 1; i < 5; i++) {
                values.put(COLUMN_ID, i);
                values.put(COLUMN_TEXT, "some text " + i);
                values.put(COLUMN_CHECKED, 0);

                sqLiteDatabase.insert(DB_TABLE, null, values);

                Log.d("myLogs", values.toString());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
