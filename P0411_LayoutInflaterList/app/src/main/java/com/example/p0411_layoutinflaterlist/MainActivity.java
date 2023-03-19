package com.example.p0411_layoutinflaterlist;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "myLog";
    private final String[] names = {"Иван", "Марья", "Петр", "Антон", "Даша", "Борис",
            "Костя", "Игорь", "Вася", "Давид", "Руслан", "Андрей", "Наташа", "Аня"};
    private final String[] positions = {"Программист", "Бухгалтер", "Программист", "Программист", "Бухгалтер", "Директор",
            "Программист", "Охранник", "Программист", "Программист", "Программист", "Программист", "Бухгалтер", "Бухгалтер"};
    private final int[] salaries = { 130000, 100000, 130000, 130000, 100000, 180000, 130000, 20000,
            130000, 130000, 130000, 130000, 100000, 100000};
    private final int[] colors = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colors[0] = Color.parseColor("#559966CC");
        colors[1] = Color.parseColor("#55336699");

        LinearLayout linearLayout = findViewById(R.id.linearLayout);

        LayoutInflater inflater = getLayoutInflater();

        View item;

        for (int i = 0; i < names.length; i++) {
            Log.d(LOG_TAG, "i = " + i);

            item = inflater.inflate(R.layout.item, linearLayout, false);
            Log.d(LOG_TAG, item.getClass().getSimpleName());

            TextView tvName = item.findViewById(R.id.tvName);
            tvName.setText(names[i]);

            TextView tvPosition = item.findViewById(R.id.tvPosition);
            String position = "Должность: " + positions[i];
            tvPosition.setText(position);

            TextView tvSalary = item.findViewById(R.id.tvSalary);
            String salary = "Оклад: " + salaries[i];
            tvSalary.setText(salary);

            item.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
            item.setBackgroundColor(colors[i % 2]);
            linearLayout.addView(item);
        }
    }
}