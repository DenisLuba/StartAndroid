package com.example.p0381_sqlitestatement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnInsert;
    TextView tvTime;

    private static final String DB_NAME = "MyDB";
    private static final String TABLE_NAME = "MyTable";
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDB();

        btnInsert = findViewById(R.id.btnInsert);
        tvTime = findViewById(R.id.tvTime);

        btnInsert.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        database.delete(TABLE_NAME, null, null);
        long startTime = System.currentTimeMillis();
//        insertRecords_1();
//        insertRecords_2();
        insertRecords_3();
        long diff = System.currentTimeMillis() - startTime;
        String text = String.format(Locale.US, "Time: %d ms", diff);
        tvTime.setText(text);
    }

    private void initDB() {
        database = this.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(FirstNumber INT, SecondNumber INT, Result INT)");
        database.delete(TABLE_NAME, null, null);
    }

    private void insertRecords_1() {
        for (int i = 0; i < 1000; i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("FirstNumber", i);
            contentValues.put("SecondNumber", i);
            contentValues.put("Result", i * i);
            database.insert(TABLE_NAME, null, contentValues);
        }
        database.setTransactionSuccessful();
    }

    private void insertRecords_2() {
        database.beginTransaction();
        try {
            for (int i = 0; i < 1000; i++) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("FirstNumber", i);
                contentValues.put("SecondNumber", i);
                contentValues.put("Result", i * i);
                database.insert(TABLE_NAME, null, contentValues);
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    private void insertRecords_3() {
        String sql = "INSERT INTO " + TABLE_NAME + " VALUES(?,?,?);";
        SQLiteStatement statement = database.compileStatement(sql);
        database.beginTransaction();
        try {
            for (int i = 0; i < 1000; i++) {
                statement.clearBindings();

                statement.bindLong(1, i);
                statement.bindLong(2, i);
                statement.bindLong(3, i * i);

                statement.execute();
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }



    @Override
    protected void onDestroy() {
        database.close();
        super.onDestroy();
    }
}