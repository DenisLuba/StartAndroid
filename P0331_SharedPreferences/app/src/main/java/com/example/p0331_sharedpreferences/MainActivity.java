package com.example.p0331_sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etText;
    private Button btnSave, btnLoad;
    private final int ID_ET_TEXT = R.id.etText;
    private final int ID_BTN_SAVE = R.id.btnSave;
    private final int ID_BTN_LOAD = R.id.btnLoad;
    private SharedPreferences sharedPreferences;
    private final String SAVED_TEXT = "saved text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("myLog", "onCreate");

        etText = findViewById(ID_ET_TEXT);
        btnSave = findViewById(ID_BTN_SAVE);
        btnLoad = findViewById(ID_BTN_LOAD);

        btnSave.setOnClickListener(this);
        btnLoad.setOnClickListener(this);

        loadText();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("myLog", "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("myLog", "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("myLog", "onPause");
    }

    @Override
    protected void onStop() {
        saveText(false);
        super.onStop();
        Log.d("myLog", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("myLog", "onDestroy");
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case ID_BTN_SAVE -> saveText(true);
            case ID_BTN_LOAD -> loadText();
        }
    }

    private void saveText(boolean flag) {
        sharedPreferences = flag ?
                getSharedPreferences("New_preferences", MODE_PRIVATE) :
                getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVED_TEXT, etText.getText().toString());
        editor.apply();
        Toast.makeText(this, "The text has been saved.", Toast.LENGTH_SHORT).show();
    }

    private void loadText() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        String text = sharedPreferences.getString(SAVED_TEXT, "default");
        etText.setText(text);
        Toast.makeText(this, "\"" + text + "\"" + " has been loaded", Toast.LENGTH_LONG).show();
    }
}