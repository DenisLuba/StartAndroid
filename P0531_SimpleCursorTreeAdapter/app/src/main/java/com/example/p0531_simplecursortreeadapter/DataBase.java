package com.example.p0531_simplecursortreeadapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBase {
    private static final String DATABASE_NAME = "database";
    private static final int DATABASE_VERSION = 1;

//    имя таблицы компаний, поля и запрос создания
    private static final String COMPANY_TABLE = "companies";
    public static final String COMPANY_COLUMN_ID = "_id";
    public static final String COMPANY_COLUMN_NAME = "name";
    private static final String COMPANY_TABLE_CREATE =
            "CREATE TABLE " + COMPANY_TABLE + "(" +
                    COMPANY_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    COMPANY_COLUMN_NAME + " TEXT);";

//    имя таблицы телефонов, поля и запрос создания
    private static final String PHONE_TABLE = "phones";
    public static final String PHONE_COLUMN_ID = "_id";
    public static final String PHONE_COLUMN_NAME = "name";
    public static final String PHONE_COLUMN_COMPANY = "company";
    private static final String PHONE_TABLE_CREATE =
            "CREATE TABLE " + PHONE_TABLE + "(" +
                    PHONE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PHONE_COLUMN_NAME + " TEXT, " +
                    PHONE_COLUMN_COMPANY + " INTEGER);";

    private final Context context;
    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public DataBase(Context context) {
        this.context = context;
    }

//    открываем подключение
    public void open() {
        dataBaseHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = dataBaseHelper.getWritableDatabase();
    }

//    закрываем подключение
    public void close() {
        if (dataBaseHelper != null) dataBaseHelper.close();
    }

//    данные по компаниям
    public Cursor getCompanyCursor() {
        return database.query(COMPANY_TABLE, null, null, null, null, null, null);
    }

//    данные по телефонам конкретной группы
    public Cursor getPhoneCursor(long companyID) {
        return database.query(PHONE_TABLE, null, PHONE_COLUMN_COMPANY + " = " + companyID, null, null, null, null);
    }


    private class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            ContentValues contentValues = new ContentValues();

//            названия компаний (групп)
            String[] companies = context.getResources().getStringArray(R.array.companies);

//            создаем и заполняем таблицу компаний
            sqLiteDatabase.execSQL(COMPANY_TABLE_CREATE);

            for (int i = 0; i < companies.length; i++) {
                contentValues.put(COMPANY_COLUMN_ID, i + 1);
                contentValues.put(COMPANY_COLUMN_NAME, companies[i]);
                sqLiteDatabase.insert(COMPANY_TABLE, null, contentValues);
                contentValues.clear();
            }

//            названия телефонов (элементов
            String[] phonesHTC = context.getResources().getStringArray(R.array.phonesHTC);
            String[] phonesSamsung = context.getResources().getStringArray(R.array.phonesSamsung);
            String[] phonesLG = context.getResources().getStringArray(R.array.phonesLG);

//            создаем и заполняем таблицу телефонов
            sqLiteDatabase.execSQL(PHONE_TABLE_CREATE);

            for (String item : phonesHTC) {
                contentValues.put(PHONE_COLUMN_COMPANY, 1);
                contentValues.put(PHONE_COLUMN_NAME, item);
                sqLiteDatabase.insert(PHONE_TABLE, null, contentValues);
                contentValues.clear();
            }
            for (String value : phonesSamsung) {
                contentValues.put(PHONE_COLUMN_COMPANY, 2);
                contentValues.put(PHONE_COLUMN_NAME, value);
                sqLiteDatabase.insert(PHONE_TABLE, null, contentValues);
                contentValues.clear();
            }
            for (String s : phonesLG) {
                contentValues.put(PHONE_COLUMN_COMPANY, 3);
                contentValues.put(PHONE_COLUMN_NAME, s);
                sqLiteDatabase.insert(PHONE_TABLE, null, contentValues);
                contentValues.clear();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        }
    }
}
