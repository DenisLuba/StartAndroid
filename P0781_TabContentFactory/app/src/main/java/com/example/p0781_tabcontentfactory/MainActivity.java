package com.example.p0781_tabcontentfactory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements TabHost.TabContentFactory {

    private final String TABS_TAG_1 = "Tag 1";
    private final String TABS_TAG_2 = "Tag 2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tabSpec;

        tabSpec = tabHost.newTabSpec(TABS_TAG_1)
                .setIndicator("Вкладка 1")
                .setContent(this);

        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec(TABS_TAG_2)
                .setIndicator("Вкладка 2")
                .setContent(this);

        tabHost.addTab(tabSpec);
    }

    @Override
    public View createTabContent(String tag) {
        return switch (tag) {
            case TABS_TAG_1 -> getLayoutInflater().inflate(R.layout.tab_activity, null);
            case TABS_TAG_2 -> {
                TextView textView = new TextView(this);
                textView.setText("Это создано вручную");

//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT
//                );
//                params.gravity = Gravity.CENTER;
//                textView.setLayoutParams(params);

                yield textView;
            }
            default -> null;
        };
    }
}