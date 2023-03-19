package com.example.p0521_simplecursoradapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBase {

    private static final String DATABASE_NAME = "myDatabase";
    private static final String DATABASE_TABLE = "myTable";
    private static final int DATABASE_VERSION = 1;

    static final String COLUMN_ID = "_id";
    static final String COLUMN_TEXT = "text";
    static final String COLUMN_IMAGE = "image";

    private static final String DATABASE_CREATE =
            "CREATE TABLE " + DATABASE_TABLE + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_IMAGE + " INTEGER, " +
                    COLUMN_TEXT + " TEXT);";

    private final Context context;

    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public DataBase(Context context) {
        this.context = context;
    }

//    открыть подключение, создать базу данных
    public void open() {
        dataBaseHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = dataBaseHelper.getWritableDatabase();
    }

//    закрыть подключение
    public void close() {
        if (dataBaseHelper != null) dataBaseHelper.close();
    }

//    получить курсор со всеми данными из таблицы DATABASE_TABLE
    public Cursor getCursor() {
        return database.query(DATABASE_TABLE, null, null, null, null, null, null);
    }

//    добавить запись в DATABASE_TABLE
    public void addRecord(String text, int image) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TEXT, text);
        contentValues.put(COLUMN_IMAGE, image);
        database.insert(DATABASE_TABLE, null, contentValues);
    }

//    удалить запись из DATABASE_TABLE
    public void deleteRecord(long id) {
        database.delete(DATABASE_TABLE, COLUMN_ID + " = " +id, null);
    }

//    класс по созданию и управлению БД
    private class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

//        создаем и заполняем БД
        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL(DATABASE_CREATE);

            ContentValues contentValues = new ContentValues();
            for (int i = 0; i < 5; i++) {
                contentValues.put(COLUMN_TEXT, "sometext " + i);
                contentValues.put(COLUMN_IMAGE, R.mipmap.ic_launcher);
                database.insert(DATABASE_TABLE, null, contentValues);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        }
    }
}


