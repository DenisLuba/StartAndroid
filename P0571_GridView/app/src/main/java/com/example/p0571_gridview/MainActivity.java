package com.example.p0571_gridview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    private String[] data;
    private ArrayAdapter<String> adapter;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = getResources().getStringArray(R.array.data);

        adapter = new ArrayAdapter<>(this, R.layout.item, R.id.textView, data);
        gridView = findViewById(R.id.gridView);
        gridView.setAdapter(adapter);

        adjustGridView();
    }

    private void adjustGridView() {
//        gridView.setNumColumns(3);
        gridView.setNumColumns(GridView.AUTO_FIT);
        gridView.setColumnWidth(300);
        gridView.setVerticalSpacing(20);
        gridView.setHorizontalSpacing(20);
//        gridView.setStretchMode(GridView.NO_STRETCH);
//        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
//        gridView.setStretchMode(GridView.STRETCH_SPACING);
        gridView.setStretchMode(GridView.STRETCH_SPACING_UNIFORM);
    }
}