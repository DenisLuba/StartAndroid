package com.example.p0871_asynctaskparams;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

/**
 *  P0871_AsyncTaskParams
 *
 *  Методы AsincTask:
 *
 *      execute() – этот метод мы явно вызываем, чтобы начать выполнение задачи.
 *  В него мы передаем набор данных определенного типа.
 *  Этот тип указан первым в угловых скобках при описании AsyncTask
 *  (в нашем примере это String). Имеют доступ к UI.
 *
 *      onPreExecute() и onPostExecute() – их мы сами явно не вызываем, о
 *  ни вызываются системой в начале и конце выполнения задачи.
 *
 *      doInBackground() – в нем мы указываем, что нам надо сделать в новом потоке.
 *  На вход поступают данные, которые мы передали в execute(). Явно не вызываем.
 *  Не имеет доступа к UI.
 *
 *      publishProgress() – явно вызываем в методах doInBackground(),
 *  onPreExecute() или onPostExecute().
 *  На вход передаем промежуточные данные определенного типа.
 *  Этот тип указан вторым в угловых скобках при описании AsyncTask
 *  (в нашем примере это Integer).
 *
 *      onProgressUpdate() – метод получает на вход промежуточные результаты.
 *  Сами не вызываем, вместо этого используем метод publishProgress().
 *  То, что передаем в publishProgress(), попадает в onProgressUpdate().
 */

public class MainActivity extends AppCompatActivity {

    private MyTask myTask;
    private TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInfo = findViewById(R.id.tvInfo);
        findViewById(R.id.btnStart).setOnClickListener(view -> {
            myTask = new MyTask();
//            Mы подаем на вход AsyncTask через execute() набор адресов файлов для загрузки.
//            Метод doInBackground() принимает эти данные.
            myTask.execute("filePath_1", "filePath_2", "filePath_3", "filePath_4");
        });
    }

    /**
     * первый <String> используется для входных данных,
     * второй <Integer> – для промежуточных данных,
     * третий <Void> – для выходных данных (пока не используем).
     */
    class MyTask extends AsyncTask<String, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvInfo.setText(R.string.begin);
        }

//        здесь в параметрах используется первый тип из угловых скобок - String
        @Override
        protected Void doInBackground(String... urls) {
            int count = 0;
            try {
                for (String url : urls) {
//                загружаем файл
                    downloadFile(url);
//                выводим промежуточные результаты
                    publishProgress(++count); // передаем в метод onProgressUpdate()
                }
//            разъединяемся
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

//        здесь в параметрах используется второй тип из угловых скобок - Integer
//        onProgressUpdate() выполняется в основном потоке и имеет доступ к UI.
        @Override
        protected void onProgressUpdate(Integer... count) {
            super.onProgressUpdate(count);
            String text = "Downloaded " + count[0] + " files";
            tvInfo.setText(text);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            tvInfo.setText(R.string.end);
        }

        private void downloadFile(String url) throws InterruptedException {
            TimeUnit.SECONDS.sleep(1);
        }
    }
}