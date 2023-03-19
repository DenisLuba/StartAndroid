package com.example.p013_androidcontextmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int messageId = -1;
        switch(item.getItemId()) {
            case R.id.action_settings:
                messageId = R.string.action_settings;
                break;
            case R.id.action_item1:
                messageId = R.string.action_item1;
                break;
            case R.id.action_item2:
                messageId = R.string.action_item2;
                break;
            case R.id.action_item3:
                messageId = R.string.action_item3;
        }
        Toast.makeText(this, getString(messageId), Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }
}