package com.example.p0631_alertdialogitemssingle;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "myDatabase";
    private static final String DB_TABLE = "myTable";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TEXT = "text";
    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY, " +
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

    private static class DataBaseHelper extends SQLiteOpenHelper {

//        класс по созданию и управлению БД
        public DataBaseHelper(Context context, String dbName, CursorFactory factory, int version) {
            super(context, dbName, factory, version);
        }

//        создаем и заполняем БД
        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL(CREATE_TABLE);

            ContentValues values = new ContentValues();
            for (int i = 1; i < 5; i++) {
                values.put(COLUMN_ID, i);
                values.put(COLUMN_TEXT, "some text " + i);
                database.insert(DB_TABLE, null, values);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {}
    }
}
