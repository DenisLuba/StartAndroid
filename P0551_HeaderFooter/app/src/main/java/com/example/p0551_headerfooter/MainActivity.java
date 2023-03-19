package com.example.p0551_headerfooter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "myLogs";

    private ListView listView;
    private ArrayAdapter<String> adapter;

    private View header1;
    private View header2;
    private View footer1;
    private View footer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        String[] data = getResources().getStringArray(R.array.data);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);

// создаем Header и Footer
        header1 = createHeader("Header 1");
        header2 = createHeader("Header 2");
        footer1 = createFooter("Footer 1");
        footer2 = createFooter("Footer 2");

        fillList();
    }

// формирование списка
    private void fillList() {
        try {
            listView.addHeaderView(header1);
            listView.addHeaderView(header2, "some text for header 2", false);
            listView.addFooterView(footer1);
            listView.addFooterView(footer2, "some text for footer 2", false);

            listView.setAdapter(adapter);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }
    }

// нажатие кнопки
    public void onClick(View view) {
//        listView.removeHeaderView(header2);
//        listView.removeFooterView(footer1);

        HeaderViewListAdapter headerAdapter = (HeaderViewListAdapter) listView.getAdapter();
        Object object;
        object = headerAdapter.getItem(1);
        Log.d(LOG_TAG, "headerAdapter.getItem(1) = " + object.toString());
        object = headerAdapter.getItem(4);
        Log.d(LOG_TAG, "headerAdapter.getItem(4) = " + object.toString());

        ArrayAdapter<String> arrayAdapter = (ArrayAdapter<String>) headerAdapter.getWrappedAdapter();
        object = arrayAdapter.getItem(1);
        Log.d(LOG_TAG, "arrayAdapter.getItem(1) = " + object.toString());
        object = arrayAdapter.getItem(4);
        Log.d(LOG_TAG, "arrayAdapter.getItem(4) = " + object.toString());
    }

// создание Header
    private View createHeader(String text) {
        View view = getLayoutInflater().inflate(R.layout.header, null);
        ((TextView) view.findViewById(R.id.tvTextHeader)).setText(text);
        return view;
    }

    // создание Footer
    private View createFooter(String text) {
        View view = getLayoutInflater().inflate(R.layout.footer, null);
        ((TextView) view.findViewById(R.id.tvTextFooter)).setText(text);
        return view;
    }
}