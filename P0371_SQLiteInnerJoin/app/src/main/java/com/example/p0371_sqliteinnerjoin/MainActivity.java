package com.example.p0371_sqliteinnerjoin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private final String LOG_TAG = "myLog";

    private final String TABLE_POSITIONS = "POSITIONS";
    private final String TABLE_PEOPLE = "PEOPLE";

    // данные для таблицы должностей
    private final int[] POSITION_ID = { 1, 2, 3, 4 };
    private final String[] POSITION = { "Директор", "Программист", "Бухгалтер", "Охранник" };
    private final int[] SALARY = { 150_000, 130_000, 100_000, 28_000 };

    // данные для таблицы людей
    private final String[] NAME = { "Иван", "Марья", "Петр", "Антон", "Даша", "Борис", "Костя", "Игорь" };
    private final int[] PEOPLE_POSITION_ID = { 2, 3, 2, 2, 3, 1, 2, 4 };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Подключаемся к БД
        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(getApplicationContext());
        SQLiteDatabase sqLiteDatabase = mySQLiteOpenHelper.getWritableDatabase();

        // Описание курсора
        Cursor cursor;

        // выводим в лог данные по должностям
        Log.d(LOG_TAG, "--- TABLE POSITIONS ---");
        cursor = sqLiteDatabase.query(TABLE_POSITIONS, null, null, null, null, null, null);
        logCursor(cursor);
        cursor.close();
        Log.d(LOG_TAG, "--- ---");

        // выводим в лог данные по людям
        Log.d(LOG_TAG, "--- TABLE PEOPLE ---");
        cursor = sqLiteDatabase.query(TABLE_PEOPLE, null, null, null, null, null, null);
        logCursor(cursor);
        cursor.close();
        Log.d(LOG_TAG, "--- ---");

        // выводим результат объединения
        // используем rawQuery
        Log.d(LOG_TAG, "--- INNER JOIN with rawQuery---");

        String sqlQuery = "SELECT Л.NAME AS Name, Д.POSITION AS Position, SALARY AS Salary "
                + "FROM PEOPLE AS Л "
                + "INNER JOIN POSITIONS AS Д "
                + "ON Л.POSITION_ID = Д.ID "
                + "where SALARY > ?";
        cursor = sqLiteDatabase.rawQuery(sqlQuery, new String[] {"120000"});
        logCursor(cursor);
        cursor.close();
        Log.d(LOG_TAG, "--- ---");

        // выводим результат объединения
        // используем query
        Log.d(LOG_TAG, "--- INNER JOIN with query---");
        String tableQuery = "PEOPLE as Л INNER JOIN POSITIONS AS Д ON Л.position_id = Д.id";
        String[] columnsQuery = {"Л.name AS Name", "Д.position AS Position", "salary AS Salary"};
        String selection = "salary < ?";
        String[] selectionArgs = {"120000"};
        cursor = sqLiteDatabase.query(tableQuery, columnsQuery, selection, selectionArgs, null, null, null);
        logCursor(cursor);
        cursor.close();
        Log.d(LOG_TAG, "--- ---");

        // закрываем БД
        mySQLiteOpenHelper.close();
    }

    // вывод в лог данных из курсора

    void logCursor(Cursor cursor) {
        if (cursor != null
                && cursor.moveToFirst()) {
            String logString;
            do {
                logString = "";
                for (String columnName : cursor.getColumnNames()) {
                    int columnIndex = cursor.getColumnIndex(columnName);
                    logString = logString.concat(columnName + " = " + cursor.getString(columnIndex) + "; ");
                }
                Log.d(LOG_TAG, logString);
            } while (cursor.moveToNext());
        } else
            Log.d(LOG_TAG, "Cursor is null");
    }

    // класс для работы с БД
    class MySQLiteOpenHelper extends SQLiteOpenHelper {

        public MySQLiteOpenHelper(Context context) {
            super(context, "mySQLiteOpenHelper", null, 1);
        }

        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            Log.d(LOG_TAG, "--- onCreate database ---");

            ContentValues contentValues = new ContentValues();

            // создаем таблицу должностей
            sqLiteDatabase.execSQL("CREATE TABLE "+ TABLE_POSITIONS + " ("
                    + "ID INTEGER PRIMARY KEY,"
                    + "POSITION TEXT,"
                    + "SALARY INTEGER);");

            // заполняем ее
            for (int i = 0; i < POSITION_ID.length; i++) {
                contentValues.clear();
                contentValues.put("ID", POSITION_ID[i]);
                contentValues.put("POSITION", POSITION[i]);
                contentValues.put("SALARY", SALARY[i]);
                sqLiteDatabase.insert(TABLE_POSITIONS, null, contentValues);
            }

            // создаем таблицу людей
            sqLiteDatabase.execSQL("CREATE TABLE "+ TABLE_PEOPLE + " ("
                    + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "NAME TEXT,"
                    + "POSITION_ID INTEGER);");

            // заполняем ее
            for (int i = 0; i < NAME.length; i++) {
                contentValues.clear();
                contentValues.put("NAME", NAME[i]);
                contentValues.put("POSITION_ID", PEOPLE_POSITION_ID[i]);
                sqLiteDatabase.insert(TABLE_PEOPLE, null, contentValues);
            }
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}