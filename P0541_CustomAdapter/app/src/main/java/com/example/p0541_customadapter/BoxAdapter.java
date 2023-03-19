package com.example.p0541_customadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BoxAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private final List<Product> products;

    public BoxAdapter(Context context, List<Product> products) {
        this.products = products;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

//    количество элементов
    @Override
    public int getCount() {
        return products.size();
    }

//    элемент по позиции
    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

//    id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

//    пункт списка
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
//    используем созданные, но не используемые view
        if (view == null) {
            view = inflater.inflate(R.layout.item, viewGroup, false);
        }

        Product product = getProduct(position);

//    заполняем View в пункте списка данными из товаров: наименование, цена и картинка
        ((TextView) view.findViewById(R.id.tvName)).setText(product.getName());
        ((TextView) view.findViewById(R.id.tvPrice)).setText(String.valueOf(product.getPrice()));
        ((ImageView) view.findViewById(R.id.image)).setImageResource(product.getImage());

        CheckBox checkBox = view.findViewById(R.id.checkBox);
//    присваиваем чекбоксу обработчик
        checkBox.setOnCheckedChangeListener(onCheckedChangeListener);
//    пишем позицию в тэг
        checkBox.setTag(position);
//    заполняем данными из товаров: в корзине или нет
        checkBox.setChecked(product.inBox());
        return view;
    }

//    товар по позиции
    private Product getProduct(int position) {
        return (Product) getItem(position);
    }

//    содержимое корзины
    public List<Product> getBox() {
        List<Product> box = new ArrayList<>();
        for (Product product : products) {
//            если в корзине
            if (product.inBox()) box.add(product);
        }
        return box;
    }

//    обработчик для чекбоксов
    private final OnCheckedChangeListener onCheckedChangeListener = (checkBox, isChecked) -> {
//    меняем данные товара (в корзине или нет)
//    берем у чекбокса тэг (объект, который может использоваться для пометки чекбокса в его иерархии)
//    Tag – это некое Object-хранилище у каждого View, куда вы можете поместить нужные вам данные.
//    этот тэг мы установили сами в методе getView()
        Integer buttonViewTag = (Integer) checkBox.getTag();
        Log.d("myLogs", "TAG : " + buttonViewTag);
        getProduct(buttonViewTag).setBox(isChecked);
    };
}
