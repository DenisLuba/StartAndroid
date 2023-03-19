package com.example.p014_androidmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    CheckBox checkBox, checkBox2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBox = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox2);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem action_mail = menu.findItem(R.id.action_mail);

        action_mail.setVisible(checkBox2.isChecked());

        menu.setGroupVisible(R.id.group1, checkBox.isChecked());
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.add(2, 4, 4, "item4").setCheckable(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_item1:
                Toast.makeText(MainActivity.this, "item 1", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_item2:
                Toast.makeText(MainActivity.this, "item 2", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_item3:
                Toast.makeText(MainActivity.this, "item 3", Toast.LENGTH_LONG).show();
            case 4:
                item.setChecked(!item.isChecked());
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}