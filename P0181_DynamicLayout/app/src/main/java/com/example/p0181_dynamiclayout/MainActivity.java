package com.example.p0181_dynamiclayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    Button btn1, btn2;
    SeekBar sbWeight;

    LinearLayout.LayoutParams lParams1;
    LinearLayout.LayoutParams lParams2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);

        sbWeight = findViewById(R.id.sbWeight);
        sbWeight.setOnSeekBarChangeListener(this);

        lParams1 = (LinearLayout.LayoutParams) btn1.getLayoutParams();
        lParams2 = (LinearLayout.LayoutParams) btn2.getLayoutParams();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int rightValue = seekBar.getMax() - progress; // the remainder of the progress

        lParams1.weight = progress;
        lParams2.weight = rightValue;

        btn1.requestLayout();
        btn2.requestLayout();

        btn1.setText(String.valueOf(progress));
        btn2.setText(String.valueOf(rightValue));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Toast.makeText(this, String.format(Locale.US, "button 1 = %f\nbutton 2 = %f", lParams1.weight, lParams2.weight), Toast.LENGTH_SHORT).show();
    }
}