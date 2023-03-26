package com.example.p0761_tab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = findViewById(android.R.id.tabhost);
//        инициализация
//        В этом методе TabHost находит в себе TabWidget и FrameLayout.
        tabHost.setup();

        TabHost.TabSpec tabSpec;

//        создаем вкладку и указываем тег
        tabSpec = tabHost.newTabSpec("tag1");
//        название вкладки
        tabSpec.setIndicator("Вкладка 1");
//        указываем id компонента из FrameLayout, он и станет содержимым
        tabSpec.setContent(R.id.tvTab1);
//        добавляем в корневой элемент
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
//        указываем название и картинку
//        в нашем случае вместо картинки идет xml-файл,
//        который определяет картинку по состоянию вкладки
        tabSpec.setIndicator("Вкладка 2", ResourcesCompat.getDrawable(getResources(), R.drawable.tab_icon_selector, null));
        tabSpec.setContent(R.id.tvTab2);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag3");
//        создаем View из layout-файла
        View view = getLayoutInflater().inflate(R.layout.tab_header, null);
//        и устанавливаем его, как заголовок
        tabSpec.setIndicator(view);
        tabSpec.setContent(R.id.tvTab3);
        tabHost.addTab(tabSpec);

//        вторая вкладка будет выбрана по умолчанию
        tabHost.setCurrentTabByTag("tag2");

//        обработчик переключения вкладок
        tabHost.setOnTabChangedListener(tabId ->
                Toast.makeText(this, "tabId = " + tabId, Toast.LENGTH_SHORT).show()
        );
    }
}