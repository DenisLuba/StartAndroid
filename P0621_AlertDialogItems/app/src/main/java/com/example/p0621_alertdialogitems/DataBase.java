package com.example.p0621_alertdialogitems;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "myDataBase";
    private static final String TABLE_NAME = "myTab";
    private static final String COLUMN_ID = "_id";
    public static final String COLUMN_TEXT = "text";
    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase database;
    private final Context context;
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_TEXT + " TEXT);";

    public DataBase(Context context) {
        this.context = context;
    }
//    открыть подключение
    public void open() {
        dataBaseHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = dataBaseHelper.getWritableDatabase();
    }
//    закрыть подключение
    public void close() {
        if (dataBaseHelper != null) dataBaseHelper.close();
    }
//    получить курсор на все данные из таблицы TABLE_NAME
    public Cursor getCursor() {
        return database.query(TABLE_NAME, null, null, null, null, null, null);
    }
//    изменить запись в TABLE_NAME
    public void changeRecord(int id, String text) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEXT, text);
        database.update(TABLE_NAME, values, COLUMN_ID + " = " + id, null);
    }

//    класс по созданию и управлению БД
    public class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
//        создаем и заполняем БД
        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL(CREATE_TABLE);

            ContentValues values = new ContentValues();
            for (int i = 1; i < 5; i++) {
                values.put(COLUMN_ID, i);
                values.put(COLUMN_TEXT, "some text " + i);
                database.insert(TABLE_NAME, null, values);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}
    }
}
