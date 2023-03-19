package com.example.p0601_alertdialogsimple;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int DIALOG_EXIT = 1;
    private DialogInterface.OnClickListener myClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(this);

        myClickListener = (dialogInterface, which) -> {
            switch (which) {
                case Dialog.BUTTON_POSITIVE -> {
                    saveData();
                    finish();
                }
                case Dialog.BUTTON_NEGATIVE -> finish();
            }
        };
    }


    @Override
    public void onClick(View view) {
//        вызываем диалог
        showDialog(DIALOG_EXIT);
    }

    @Override
    public void onBackPressed() {
//        вызываем диалог
        showDialog(DIALOG_EXIT);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == DIALOG_EXIT) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        заголовок
            builder.setTitle(R.string.exit);
//        сообщение
            builder.setMessage(R.string.save_data);
//        иконка
            builder.setIcon(android.R.drawable.ic_dialog_alert);
//        кнопка положительного ответа
            builder.setPositiveButton(R.string.yes, myClickListener);
//        кнопка отрицательного ответа
            builder.setNegativeButton(R.string.no, myClickListener);
//        кнопка нейтрального ответа
            builder.setNeutralButton(R.string.cancel, myClickListener);
//        создаем диалог
            return builder.create();
        }
        return super.onCreateDialog(id);
    }

    private void saveData() {
        Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
    }
}