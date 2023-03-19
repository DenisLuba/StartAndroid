package com.example.p0341_sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

//    SQLiteOpenHelper.
//    Methods:
//    onCreate() - called when creating the database;
//    onUpgrade() - to upgrade a version;
//    onDowngrade() - to downgrade a version;
//    onOpen() - called when the database is opened;
//    getReadableDatabase() - returns the database for reading;
//    getWritableDatabase() - return the database for writing and reading.

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "contactDB";
    public static final String TABLE_CONTACTS = "contacts";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_MAIL = "mail";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION); // SQLiteDatabase.CursorFactory = null
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String command = String.format("create table %s(%s integer primary key,%s text,%s text)",
                TABLE_CONTACTS, KEY_ID, KEY_NAME, KEY_MAIL);
        database.execSQL(command);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("drop table if exists " + TABLE_CONTACTS);
        onCreate(database);
    }
}
