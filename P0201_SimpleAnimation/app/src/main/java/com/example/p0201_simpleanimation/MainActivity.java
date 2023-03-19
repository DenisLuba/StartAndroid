package com.example.p0201_simpleanimation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // константы для ID пунктов меню
    final int MENU_ALPHA_ID = 1;
    final int MENU_SCALE_ID = 2;
    final int MENU_TRANSLATE_ID = 3;
    final int MENU_ROTATE_ID = 4;
    final int MENU_COMBO_ID = 5;

    TextView tv;
    final int TV_ID = R.id.tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);
        // регистрируем контекстное меню для компонента tv
        registerForContextMenu(tv);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        switch (view.getId()) {
            case TV_ID:
                menu.add(0, MENU_ALPHA_ID, 0, "Alpha");
                menu.add(0, MENU_SCALE_ID, 0, "Scale");
                menu.add(0, MENU_TRANSLATE_ID, 0, "Translate");
                menu.add(0, MENU_ROTATE_ID, 0, "Rotate");
                menu.add(0, MENU_COMBO_ID, 0, "Combo");
                break;
        }
        super.onCreateContextMenu(menu, view, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Animation animation = null;
        switch(item.getItemId()) {
            case MENU_ALPHA_ID:
                // создаем объект анимации из файла anim/myalpha
                animation = AnimationUtils.loadAnimation(this, R.anim.myalpha);
                break;
            case MENU_SCALE_ID:
                animation = AnimationUtils.loadAnimation(this, R.anim.myscale);
                break;
            case MENU_TRANSLATE_ID:
                animation = AnimationUtils.loadAnimation(this, R.anim.mytrans);
                break;
            case MENU_ROTATE_ID:;
                animation = AnimationUtils.loadAnimation(this, R.anim.myrotate);
                break;
            case MENU_COMBO_ID:
                animation = AnimationUtils.loadAnimation(this, R.anim.mycombo);
                break;
        }
        tv.startAnimation(animation);
        return super.onContextItemSelected(item);
    }
}