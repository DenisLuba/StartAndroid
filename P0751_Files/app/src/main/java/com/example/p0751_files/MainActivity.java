package com.example.p0751_files;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "myLogs";

    private final String FILENAME = "file";
    private final String FILENAME_SD = "fileSD";
    private final String DIRECTORY_SD = "MyFiles";

    private static final int BTN_WRITE_ID = R.id.btnWrite;
    private static final int BTN_READ_ID = R.id.btnRead;
    private static final int BTN_WRITE_SD_ID = R.id.btnWriteSD;
    private static final int BTN_READ_SD_ID = R.id.btnReadSD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnWrite = findViewById(BTN_WRITE_ID);
        Button btnRead = findViewById(BTN_READ_ID);
        Button btnWriteSD = findViewById(BTN_WRITE_SD_ID);
        Button btnReadSD = findViewById(BTN_READ_SD_ID);

        btnWrite.setOnClickListener(this::onClick);
        btnRead.setOnClickListener(this::onClick);
        btnWriteSD.setOnClickListener(this::onClick);
        btnReadSD.setOnClickListener(this::onClick);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case BTN_WRITE_ID -> writeFile();
            case BTN_READ_ID -> readFile();
            case BTN_WRITE_SD_ID -> writeFileSD();
            case BTN_READ_SD_ID -> readFileSD();
        }
    }

    private void writeFile() {
//        отрываем поток для записи
        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                openFileOutput(FILENAME, MODE_PRIVATE)))) {
//        пишем данные
            writer.write("Содержимое файла");
            Log.d(LOG_TAG, "Файл записан");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFile() {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
                openFileInput(FILENAME)))) {
            String line;
//            читаем содержимое
            while ((line = reader.readLine()) != null)
                Log.d(LOG_TAG, line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeFileSD() {
//        проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return;
        }
//        получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();;

//        добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIRECTORY_SD);
//        создаем каталог
        sdPath.mkdirs();
//        формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, FILENAME_SD);

//        открываем поток для записи
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(sdFile))) {
//            пишем данные
            writer.write("Содержимое файла на SD");
            Log.d(LOG_TAG, "Файл записан на SD: " + sdFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFileSD() {
        //        проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return;
        }
//        получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
//        добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIRECTORY_SD);
//        формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, FILENAME_SD);

//        открываем поток для чтения
        try(BufferedReader reader = new BufferedReader(new FileReader(sdFile))) {
            String line;
//        читаем содержимое
            while((line = reader.readLine()) != null)
                Log.d(LOG_TAG, line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}