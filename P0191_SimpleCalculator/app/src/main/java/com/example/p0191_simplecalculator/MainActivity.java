package com.example.p0191_simplecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final int MENU_RESET_ID = 1;
    final int MENU_QUIT_ID = 2;

    EditText etNum1, etNum2;
    Button plus, minus, multiply, divide;
    TextView tvResult;

    String operator = "";

    final int PLUS_ID = R.id.plus;
    final int MINUS_ID = R.id.minus;
    final int MULTIPLY_ID = R.id.multiply;
    final int DIVIDE_ID = R.id.divide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNum1 = findViewById(R.id.etNum1);
        etNum2 = findViewById(R.id.etNum2);
        plus = findViewById(PLUS_ID);
        minus = findViewById(MINUS_ID);
        multiply = findViewById(MULTIPLY_ID);
        divide = findViewById(DIVIDE_ID);
        tvResult = findViewById(R.id.tvResult);

        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
        multiply.setOnClickListener(this);
        divide.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        double num1 = 0;
        double num2 = 0;
        double result = 0;

        if (TextUtils.isEmpty(etNum1.getText().toString())
                || TextUtils.isEmpty(etNum2.getText().toString())) return;

        num1 = Double.parseDouble(etNum1.getText().toString());
        num2 = Double.parseDouble(etNum2.getText().toString());

        switch (view.getId()) {
            case PLUS_ID:
                operator = "+";
                result = num1 + num2;
                break;
            case MINUS_ID:
                operator = "-";
                result = num1 - num2;
                break;
            case MULTIPLY_ID:
                operator = "*";
                result = num1 * num2;
                break;
            case DIVIDE_ID:
                if (num2 == 0) {
                    tvResult.setText(R.string.divide_by_zero);
                    return;
                }
                operator = "/";
                result = num1 / num2;
                break;
        }

        tvResult.setText(String.format(Locale.US, "%s %s %s = %s",
                format(num1), operator, format(num2), format(result)));
    }

    private String format(double n) {
        return n == (long) n ? String.format(Locale.US, "%d", (long) n) : String.format("%s", n);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_RESET_ID, 0, "Reset");
        menu.add(0, MENU_QUIT_ID, 0, "Quit");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case MENU_RESET_ID:
                etNum1.setText("");
                etNum2.setText("");
                tvResult.setText("");
                break;
            case MENU_QUIT_ID:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}