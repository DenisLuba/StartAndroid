package com.example.p0541_customadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<Product> products = new ArrayList<>();
    private BoxAdapter boxAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        создаем адаптер
        fillData();
        boxAdapter = new BoxAdapter(this, products);

//        настраиваем список
        ListView list = findViewById(R.id.list);
        list.setAdapter(boxAdapter);
    }

//        генерируем данные для адаптера
    private void fillData() {
        for (int i = 1; i <= 20; i++) {
            products.add(new Product("Product " + i, i * 1000, R.mipmap.ic_launcher, false));
        }
    }

//        выводим информацию о корзине
    public void showResult(View view) {
        StringBuilder result = new StringBuilder("Товары в корзине");
        for (Product product : boxAdapter.getBox()) {
            if (product.inBox()) result.append('\n').append(product.getName());
        }
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }
}