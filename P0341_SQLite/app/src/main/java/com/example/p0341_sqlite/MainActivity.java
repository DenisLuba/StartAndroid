package com.example.p0341_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etId, etName, etEmail;
    private Button btnAdd, btnRead, btnClear, btnUpdate, btnDelete;
    private TableLayout table;


    private final int ID_ET_ID = R.id.etId;
    private final int ID_ET_NAME = R.id.etName;
    private final int ID_ET_EMAIL = R.id.etEmail;
    private final int ID_BTN_ADD = R.id.btnAdd;
    private final int ID_BTN_READ = R.id.btnRead;
    private final int ID_BTN_CLEAR = R.id.btnClear;
    private final int ID_BTN_UPDATE = R.id.btnUpdate;
    private final int ID_BTN_DELETE = R.id.btnDelete;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etId = findViewById(ID_ET_ID);
        etName = findViewById(ID_ET_NAME);
        etEmail = findViewById(ID_ET_EMAIL);
        btnAdd = findViewById(ID_BTN_ADD);
        btnRead = findViewById(ID_BTN_READ);
        btnClear = findViewById(ID_BTN_CLEAR);
        btnUpdate = findViewById(ID_BTN_UPDATE);
        btnDelete = findViewById(ID_BTN_DELETE);

        btnAdd.setOnClickListener(this);
        btnRead.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        table = findViewById(R.id.table);
        table.setVisibility(View.GONE);

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View view) {

//        SQLiteDatabase.
//        Methods:
//        query() - to read data from the database;
//        insert() - to add data to the database;
//        delete() - to delete data from the database;
//        update() - to change data in the database;
//        execSQL() - executes code in SQL language.

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        switch(view.getId()) {
            case ID_BTN_ADD -> add(database, contentValues);
            case ID_BTN_READ -> read(database);
            case ID_BTN_CLEAR -> clear(database);
            case ID_BTN_UPDATE -> update(contentValues, database);
            case ID_BTN_DELETE -> delete(database);
        }

        dbHelper.close();
    }

    private void add(SQLiteDatabase database, ContentValues contentValues) {
        table.setVisibility(View.GONE);

        String name = etName.getText().toString();
        String email = etEmail.getText().toString();

        if (!name.equals("")) {
            contentValues.put(DBHelper.KEY_NAME, name);
            contentValues.put(DBHelper.KEY_MAIL, email);

            etName.setText("");
            etEmail.setText("");

            database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
            Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);
            cursor.moveToLast();
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int id = cursor.getInt(idIndex);
            cursor.close();
            newRow(id, name, email);
        }

    }

    private void read(SQLiteDatabase database) {

        int childCount = table.getChildCount();
        if (childCount > 1)
            table.removeViews(1, childCount - 1);

//        Cursor.
//        Methods:
//        moveToFirst() - перемещает курсор на первую строку в результате запроса;
//        moveToNext() - перемещает курсор на следующую строку;
//        moveToLast() - перемещает курсор на последнюю строку;
//        moveToPrevious() - перемещает курсор на предыдущую строку;
//        getCount() - возвращает количество строк в наборе данных;
//        getColumnIndexOrThow() - возвращает индекс для столбца с указанным именем;
//        getColumnName() - возвращает имя столбца с указанным индексом;
//        getColumnNames() - возвращает массив имен всех столбцов в объекте Cursor;
//        moveToPosition() - перемещает курсор на указанную строку;
//        getPosition() - возвращает текущую позицию курсора;
//        isBeforeFirst() - возвращает, указывает ли курсор на позицию перед первой строкой;
//        isAfterLast() - полезный метод, сигнализирующий о достижении конца запроса;
//        isClosed() - возвращает значение true, если курсор закрыт.


//        Parameters of the "query" method
//        table - имя таблицы
//        columns - список полей, которые мы хотим получить
//        selection - строка условия WHERE
//        selectionArgs - массив аргументов для selection
//        goupBy - группировка
//        having - использование условий для агрегатных функций
//        orderBy - сортировка

        Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            table.setVisibility(View.VISIBLE);
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
            int emailIndex = cursor.getColumnIndex(DBHelper.KEY_MAIL);

            do {
                int id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);
                String email = cursor.getString(emailIndex);

                newRow(id, name, email);

                String logFormat = String.format(Locale.US, "ID = %d, name = %s, email = %s", id, name, email);
                Log.d("myLog", logFormat);
            } while (cursor.moveToNext());
        } else Log.d("myLog", "0 rows");

        cursor.close();
    }

    private void clear(SQLiteDatabase database) {
        table.setVisibility(View.GONE);
        database.delete(DBHelper.TABLE_CONTACTS, null, null);

        int childCount = table.getChildCount();
        if (childCount > 1)
            table.removeViews(1, childCount - 1);
    }

    private void update(ContentValues contentValues, SQLiteDatabase database) {
        String idString = etId.getText().toString();
        if (!idString.trim().matches("^\\d+$")) return;
        int id = Integer.parseInt(idString);

        Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);
        if (!cursor.moveToPosition(id - 1)) return;
        int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
        int emailIndex = cursor.getColumnIndex(DBHelper.KEY_MAIL);

        String nameInTable = cursor.getString(nameIndex);
        String emailInTable = cursor.getString(emailIndex);

        String name = etName.getText().toString();
        name = name.equals("") ? nameInTable : name;

        String email = etEmail.getText().toString();
        email = email.equals("") ? emailInTable : email;

        contentValues.put(DBHelper.KEY_NAME, name);
        contentValues.put(DBHelper.KEY_MAIL, email);

        int updateCount = database.update(DBHelper.TABLE_CONTACTS, contentValues, DBHelper.KEY_ID + "= ?", new String[]{String.valueOf(id)});

        Log.d("myLog", "updates rows count = " + updateCount);
        cursor.close();

        if (table.getVisibility() == View.GONE || table.getVisibility() == View.INVISIBLE) return;
        read(database);
    }

    private void delete(SQLiteDatabase database) {
        String id = etId.getText().toString();
        String name = etName.getText().toString();
        int deleteCount = 0;
        if (!id.equals("")) {
            deleteCount = database.delete(DBHelper.TABLE_CONTACTS, DBHelper.KEY_ID + "=" + id, null);

        } else if (!name.equals("")) {
            deleteCount = database.delete(DBHelper.TABLE_CONTACTS, DBHelper.KEY_NAME + " = ?", new String[] {name});
        } else return;

        Log.d("myLog", "deleted rows count = " + deleteCount);
        if (table.getVisibility() == View.GONE || table.getVisibility() == View.INVISIBLE) return;
        read(database);
    }

    private void newRow(int id, String name, String email) {
        TableRow.LayoutParams idParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
        TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2.0f);

        TableRow tableRow = new TableRow(this);
        TextView tvId = new TextView(new ContextThemeWrapper(this, R.style.TableData));
        tvId.setLayoutParams(idParams);
        tvId.setText(String.valueOf(id));

        TextView tvName = new TextView(new ContextThemeWrapper(this, R.style.TableData));
        tvName.setLayoutParams(params);
        tvName.setText(name);

        TextView tvEmail = new TextView(new ContextThemeWrapper(this, R.style.TableData));
        tvEmail.setLayoutParams(params);
        tvEmail.setText(email);
        tvEmail.setMaxLines(1);

        tableRow.addView(tvId);
        tableRow.addView(tvName);
        tableRow.addView(tvEmail);

        table.addView(tableRow);
    }
}