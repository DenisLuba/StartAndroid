package com.example.p0111_resvalues;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button button;
    ImageView imageView;
    boolean is = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button);
        button.setOnClickListener(view -> {
                    is = !is;
                    if (is) {
                        imageView.setImageDrawable(ResourcesCompat
                                .getDrawable(getResources(), R.drawable.ic_launcher_foreground, null));
                    } else
                        imageView.setImageDrawable(ResourcesCompat
                                .getDrawable(getResources(), R.drawable.ic_round_camera_24, null));
                }
        );

    }
}