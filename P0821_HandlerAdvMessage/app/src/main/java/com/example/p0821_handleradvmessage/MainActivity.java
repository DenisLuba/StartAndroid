package com.example.p0821_handleradvmessage;

/**
 * P0821_HandlerAdvMessage
 */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "myLogs";

    private static final int STATUS_NONE = 0;            // нет подключения
    private static final String NONE = "Not connected";
    private static final int STATUS_CONNECTING = 1;      // подключаемся
    private static final String CONNECTING = "Connecting";
    private static final int STATUS_CONNECTED = 2;       // подключено
    private static final String CONNECTED = "Connected";
    private static final int STATUS_DOWNLOAD_START = 3;  // загрузка началась
    private static final String DOWNLOAD_START = "Start download";
    private static final int STATUS_DOWNLOAD_FILE = 4;   // файл загружен
    private static final String DOWNLOAD_FILE = "Downloading.";
    private static final int STATUS_DOWNLOAD_END = 5;    // загрузка закончена
    private static final String DOWNLOAD_END = "Download complete!";
    private static final int STATUS_DOWNLOAD_NONE = 6;   // загрузка закончена
    private static final String DOWNLOAD_NONE = "No files for download.";

    private Handler handler;

    private TextView tvStatus;
    private Button btnConnect;
    private ProgressBar pbDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tvStatus);
        btnConnect = findViewById(R.id.btnConnect);
        pbDownload = findViewById(R.id.pbDownload);

        btnConnect.setOnClickListener(this::onClick);

        handler = getHandler(Looper.myLooper());
        handler.sendEmptyMessage(STATUS_NONE);
    }

    private void onClick(View view) {
        Thread thread = new Thread(() -> {

            Message message;
            byte[] file;
            Random random = new Random();

            try {
//            устанавливаем подключение
                handler.sendEmptyMessage(STATUS_CONNECTING);
                TimeUnit.SECONDS.sleep(1);

//            подключение установлено
                handler.sendEmptyMessage(STATUS_CONNECTED);

//            определяем кол-во файлов
                TimeUnit.SECONDS.sleep(1);
                int filesXCount = random.nextInt(5);

                if (filesXCount == 0) {
    //                сообщаем, что файлов для загрузки нет
                    handler.sendEmptyMessage(STATUS_DOWNLOAD_NONE);
    //                и отключаемся
                    TimeUnit.MILLISECONDS.sleep(1500);
                    handler.sendEmptyMessage(STATUS_NONE);
                    return;
                }

//            загрузка начинается
//            создаем сообщение, с информацией о количестве файлов
                message = handler.obtainMessage(STATUS_DOWNLOAD_START, filesXCount, 0);
//            отправляем
                handler.sendMessage(message);

                for (int i = 1; i <= filesXCount; i++) {
    //                загружается файл
                    file = downloadFile();
    //                создаем сообщение с информацией о порядковом номере файла,
    //                кол-вом оставшихся и самим файлом
                    message = handler.obtainMessage(STATUS_DOWNLOAD_FILE, i, filesXCount - i, file);
    //                отправляем
                    handler.sendMessage(message);
                }

//            загрузка завершена
                handler.sendEmptyMessage(STATUS_DOWNLOAD_END);
//            отключаемся
                TimeUnit.MILLISECONDS.sleep(1500);
                handler.sendEmptyMessage(STATUS_NONE);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });
        thread.start();
    }

    private Handler getHandler(Looper looper) {

        return new Handler(looper) {
            @Override
            public void handleMessage(android.os.Message message) {
                switch (message.what) {
                    case STATUS_NONE -> {
                        btnConnect.setEnabled(true);
                        tvStatus.setText(NONE);
                        pbDownload.setVisibility(View.GONE);
                    }
                    case STATUS_CONNECTING -> {
                        btnConnect.setEnabled(false);
                        tvStatus.setText(CONNECTING);
                    }
                    case STATUS_CONNECTED -> tvStatus.setText(CONNECTED);
                    case STATUS_DOWNLOAD_START -> {
                        String start = DOWNLOAD_START + " " + message.arg1 + " files";
                        tvStatus.setText(start);
                        pbDownload.setMax(message.arg1);
                        pbDownload.setProgress(0);
                        pbDownload.setVisibility(View.VISIBLE);
                    }
                    case STATUS_DOWNLOAD_FILE -> {
                        String downloading = DOWNLOAD_FILE + " Left " + message.arg2 + " files";
                        tvStatus.setText(downloading);
                        pbDownload.setProgress(message.arg1);
                        saveFile((byte[]) message.obj);
                    }
                    case STATUS_DOWNLOAD_END -> tvStatus.setText(DOWNLOAD_END);
                    case STATUS_DOWNLOAD_NONE -> tvStatus.setText(DOWNLOAD_NONE);
                }
            }
        };
    }

    private byte[] downloadFile() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        return new byte[1024];
    }

    private void saveFile(byte[] file) {

    }
}