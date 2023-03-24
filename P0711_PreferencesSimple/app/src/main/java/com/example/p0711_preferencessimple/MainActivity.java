package com.example.p0711_preferencessimple;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvInfo;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInfo = findViewById(R.id.tvInfo);

//        получаем SharedPreferences, которое работает с файлом настроек
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        полная очистка настроек
//        preferences.edit().clear().apply();
//        preferences.edit().clear().commit(); // то же самое
    }

    @Override
    protected void onResume() {
        boolean notification = preferences.getBoolean("notification", false);
        String address = preferences.getString("address", "");
        String text = "Notifications are " +
                (notification ?
                "enabled, address = " + address :
                "disabled");

        tvInfo.setText(text);

        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.add(0, 1, 0, "Preferences");
        menuItem.setIntent(new Intent(this, PrefActivity.class));
        return super.onCreateOptionsMenu(menu);
    }
}