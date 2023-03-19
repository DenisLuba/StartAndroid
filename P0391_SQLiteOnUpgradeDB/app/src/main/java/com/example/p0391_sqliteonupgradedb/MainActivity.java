package com.example.p0391_sqliteonupgradedb;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";
    final String DB_NAME = "staff"; // the database's name
    final int DB_VERSION = 2; // version of the database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "--- Staff database version: " + database.getVersion() + " ---");
        writeStaff(database);
        dbHelper.close();
    }

    private void writeStaff(SQLiteDatabase database) {
        Cursor cursor = database.rawQuery("SELECT * FROM people", null);
        logCursor(cursor, "Table people");
        cursor.close();

        cursor = database.rawQuery("SELECT * FROM position", null);
        logCursor(cursor, "Table position");
        cursor.close();

        String sqlQuery = "SELECT PL.name AS Name, PS.name as Position, salary as Salary " +
                "FROM people as PL " +
                "INNER JOIN position as PS " +
                "ON PL.posid = PS.id ";
        cursor = database.rawQuery(sqlQuery, null);
        logCursor(cursor, "Inner join");
        cursor.close();
    }

    void logCursor(Cursor cursor, String title) {
        if (cursor != null &&
                cursor.moveToFirst()) {
            Log.d(LOG_TAG, title + ". " + cursor.getCount() + " rows");
            StringBuilder builder = new StringBuilder();
            do {
                builder.setLength(0);
                for(String columnName : cursor.getColumnNames()) {
                    int columnIndex = cursor.getColumnIndex(columnName);
                    builder.append(columnName)
                            .append(" = ")
                            .append(cursor.getString(columnIndex))
                            .append("; ");
                }
                Log.d(LOG_TAG, builder.toString());
            } while (cursor.moveToNext());
        }
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(@Nullable Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            Log.d(LOG_TAG, "--- onCreate database ---");

//            данные для таблицы людей
            String[] people_name = {"Ivan", "Maria", "Petr", "Anton", "Dasha",
                    "Boris", "Kostya", "Igor"};
            int[] people_posid = {2, 3, 2, 2, 3, 1, 2, 4};

//            данные для таблицы должностей
            int[] position_id = {1, 2, 3, 4};
            String[] position_name = {"Director", "Programmer", "Accountant", "Security"};
            int[] position_salary = {150000, 130000, 100000, 20000};

            ContentValues contentValues = new ContentValues();

//            создаем таблицу должностей
            database.execSQL("CREATE TABLE position (" +
                    "id INTEGER PRIMARY KEY," +
                    "name TEXT," +
                    "salary INTEGER);");

//            заполняем ее
            for (int i = 0; i < position_id.length; i++) {
                contentValues.clear();
                contentValues.put("id", position_id[i]);
                contentValues.put("name", position_name[i]);
                contentValues.put("salary", position_salary[i]);
                database.insert("position", null, contentValues);
            }

            // создаем таблицу людей
            database.execSQL("CREATE TABLE people (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT, " +
                    "posid INTEGER);");

            // заполняем ее
            for (int i = 0; i < people_name.length; i++) {
                contentValues.clear();
                contentValues.put("name", people_name[i]);
                contentValues.put("posid", people_posid[i]);
                database.insert("people", null, contentValues);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
            Log.d(LOG_TAG, "--- onUpgrade database from " + oldVersion +
                    " to " + newVersion + " version ---");

            if (oldVersion == 1 && newVersion == 2) {
                ContentValues contentValues = new ContentValues();

                // данные для таблиц должностей
                int[] position_id = {1, 2, 3, 4};
                String[] position_name = {"Director", "Programmer", "Accountant", "Security"};
                int[] position_salary = {150000, 130000, 100000, 20000};

                database.beginTransaction();
                try {
                    // создаем таблицу должностей
                    database.execSQL("CREATE TABLE position (" +
                            "id INTEGER PRIMARY KEY," +
                            "name TEXT, salary INTEGER);");

                    // заполняем ее
                    for (int i = 0; i < position_id.length; i++) {
                        contentValues.clear();
                        contentValues.put("id", position_id[i]);
                        contentValues.put("name", position_name[i]);
                        contentValues.put("salary", position_salary[i]);
                        database.insert("position", null, contentValues);
                    }

                    database.execSQL("ALTER TABLE people ADD COLUMN posid INTEGER;");

                    for (int i = 0; i < position_id.length; i++) {
                        contentValues.clear();
                        contentValues.put("posid", position_id[i]);
                        database.update("people", contentValues, "position = ?",
                                new String[] {position_name[i]});
                    }

                    database.execSQL("CREATE TEMPORARY TABLE people_tmp (" +
                            "id INTEGER, " +
                            "name TEXT, " +
                            "position TEXT, " +
                            "posid INTEGER);");

                    database.execSQL("INSERT INTO people_tmp SELECT id, name, position, posid FROM people;");

                    database.execSQL("DROP TABLE people;");

                    database.execSQL("CREATE TABLE people (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "name TEXT," +
                            "posid INTEGER);");

                    database.execSQL("INSERT INTO people SELECT id, name, posid FROM people_tmp;");

                    database.execSQL("DROP TABLE people_tmp;");

                    database.setTransactionSuccessful();
                } finally {
                    database.endTransaction();
                }
            }
        }
    }
}