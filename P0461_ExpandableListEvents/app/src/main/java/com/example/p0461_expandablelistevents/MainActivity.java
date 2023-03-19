package com.example.p0461_expandablelistevents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "myLogs";

    private ExpandableListView expListView;
    private AdapterHelper adapterHelper;
    private SimpleExpandableListAdapter adapter;
    private TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInfo = findViewById(R.id.tvInfo);

//        создаем адаптер
        adapterHelper = new AdapterHelper(this);
        adapter = adapterHelper.getAdapter();

        expListView = findViewById(R.id.expListView);
        expListView.setAdapter(adapter);

//        нажатие на элемент
        expListView.setOnChildClickListener((expandableListView, view, groupPosition, childPosition, id) -> {
            Log.d(LOG_TAG, "onChildClick groupPosition = " + groupPosition +
                    ", childPosition = " + childPosition +
                    ", id = " + id);
            tvInfo.setText(adapterHelper.getGroupChildText(groupPosition, childPosition));
            return false;
        });

//        нажатие на группу
        expListView.setOnGroupClickListener((expandableListView, view, groupPosition, id) -> {
            Log.d(LOG_TAG, "onGroupClick groupPosition = " + groupPosition + ", id = " + id);
//        блокируем дальнейшую обработку события для группы с позицией 1
            return groupPosition == 1;
        });

//        сворачивание группы
        expListView.setOnGroupCollapseListener(groupPosition -> {
            Log.d(LOG_TAG, "onGroupCollapse groupPosition = " + groupPosition);
            String text = "Свернули " + adapterHelper.getGroupText(groupPosition);
            tvInfo.setText(text);
        });

//        разворачивание группы
        expListView.setOnGroupExpandListener(groupPosition -> {
            Log.d(LOG_TAG, "onGroupExpand groupPosition " + groupPosition);
            String text = "Развернули " + adapterHelper.getGroupText(groupPosition);
            tvInfo.setText(text);
        });

//        разворачиваем группу с позицией 2
        expListView.expandGroup(2);
    }
}