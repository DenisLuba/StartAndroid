package com.example.p0151_contextmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvColor, tvSize;

    private final int ID_MENU_COLOR_RED = R.id.MENU_COLOR_RED;
    private final int ID_MENU_COLOR_GREEN = R.id.MENU_COLOR_GREEN;
    private final int ID_MENU_COLOR_BLUE = R.id.MENU_COLOR_BLUE;

    private final int ID_MENU_SIZE_20 = R.id.MENU_SIZE_20;
    private final int ID_MENU_SIZE_24 = R.id.MENU_SIZE_24;
    private final int ID_MENU_SIZE_28 = R.id.MENU_SIZE_28;

    private final int IDtvSIZE = R.id.tvSize;
    private final int IDtvCOLOR = R.id.tvColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvColor = findViewById(IDtvCOLOR);
        tvSize = findViewById(IDtvSIZE);

//        registerForContextMenu(tvColor);
        tvColor.setOnCreateContextMenuListener(this);
        registerForContextMenu(tvSize);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        switch (view.getId()) {
            case IDtvCOLOR:
                menu.add(0, ID_MENU_COLOR_RED, 0, "Red");
                menu.add(0, ID_MENU_COLOR_BLUE, 0, "Blue");
                menu.add(0, ID_MENU_COLOR_GREEN, 0, "Green");
                break;
            case IDtvSIZE:
                menu.add(0, ID_MENU_SIZE_20, 0, "Size 20");
                menu.add(0, ID_MENU_SIZE_24, 0, "Size 24");
                menu.add(0, ID_MENU_SIZE_28, 0, "Size 28");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case ID_MENU_COLOR_RED:
                tvColor.setTextColor(Color.RED);
                tvColor.setText(R.string.setRedColor);
                break;
            case ID_MENU_COLOR_BLUE:
                tvColor.setTextColor(Color.BLUE);
                tvColor.setText(R.string.setBlueColor);
                break;
            case ID_MENU_COLOR_GREEN:
                tvColor.setTextColor(Color.GREEN);
                tvColor.setText(R.string.setGreenColor);
                break;

            case ID_MENU_SIZE_20:
                tvSize.setTextSize(20);
                tvSize.setText(R.string.setSize_20);
                break;
            case ID_MENU_SIZE_24:
                tvSize.setTextSize(24);
                tvSize.setText(R.string.setSize_24);
                break;
            case ID_MENU_SIZE_28:
                tvSize.setTextSize(28);
                tvSize.setText(R.string.setSize_28);
                break;
        }
        return super.onContextItemSelected(item);
    }
}