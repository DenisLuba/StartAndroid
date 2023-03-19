package com.example.p0171_dynamiclayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout llMain;
    RadioGroup rgGravity;
    EditText etName;
    Button btnCreate, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llMain = findViewById(R.id.llMain);
        rgGravity = findViewById(R.id.rgGravity);
        etName = findViewById(R.id.etName);

        btnCreate = findViewById(R.id.btnCreate);
        btnClear = findViewById(R.id.btnClear);

        btnCreate.setOnClickListener(this);
        btnClear.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnCreate:
                LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                int btnGravity = Gravity.LEFT;

                switch(rgGravity.getCheckedRadioButtonId()) {
                    case R.id.rbLeft:
                        btnGravity = Gravity.LEFT;
                        break;
                    case R.id.rbCenter:
                        btnGravity = Gravity.CENTER_HORIZONTAL;
                        break;
                    case R.id.rbRight:
                        btnGravity = Gravity.RIGHT;
                        break;
                }
                lParams.gravity = btnGravity;

                Button btnNew = new Button(this);
                btnNew.setText(etName.getText().toString());
                llMain.addView(btnNew, lParams);

                break;

            case R.id.btnClear:
                llMain.removeAllViews();
                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}