package com.example.p0451_expandablelist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

//    названия компаний (групп)
    private final String[] companies = { "HTC", "Samsung", "LG" };

//    названия телефонов (элементов)
    private final String[] phonesHTC = { "Sensation", "Desire", "Wildfire", "Hero" };
    private final String[] phonesSamsung = { "Galaxy S II", "Galaxy Nexus", "Wave" };
    private final String[] phonesLG = { "Optimus", "Optimus Link", "Optimus Black", "Optimus One" };

//    коллекция для групп
    private ArrayList<Map<String, String>> companiesData;

//    коллекция для элементов одной группы
    private ArrayList<Map<String , String>> phonesData;

//    общая коллекция для коллекций элементов
    private ArrayList<ArrayList<Map<String, String>>> allPhonesData;
//    в итоге получится allPhonesData = ArrayList<phonesData>

//    список аттрибутов группы или элемента
    private Map<String, String> m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        заполняем коллекцию групп из массива с названиями групп
        companiesData = new ArrayList<>();
        for (String company : companies) {
//            заполняем список аттрибутов для каждой группы
            m = new HashMap<>();
            m.put("companyName", company); // имя компании
            companiesData.add(m);
        }

//        создаем коллекцию для коллекций элементов
        allPhonesData = new ArrayList<>();

//        создаем коллекцию элементов для первой группы
        phonesData = new ArrayList<>();
//        заполняем список аттрибутов для каждого элемента
        for (String phone : phonesHTC) {
            m = new HashMap<>();
            m.put("phoneName", phone);
            phonesData.add(m);
        }
//        добавляем в коллекцию коллекций
        allPhonesData.add(phonesData);

//        создаем коллекцию элементов для второй группы
        phonesData = new ArrayList<>();
//        заполняем список аттрибутов для каждого элемента
        for (String phone : phonesSamsung) {
            m = new HashMap<>();
            m.put("phoneName", phone);
            phonesData.add(m);
        }
//        добавляем в коллекцию коллекций
        allPhonesData.add(phonesData);

//        создаем коллекцию элементов для третьей группы
        phonesData = new ArrayList<>();
//        заполняем список аттрибутов для каждого элемента
        for (String phone : phonesLG) {
            m = new HashMap<>();
            m.put("phoneName", phone);
            phonesData.add(m);
        }
//        добавляем в коллекцию коллекций
        allPhonesData.add(phonesData);

//        список аттрибутов групп для чтения
        String[] companyFrom = { "companyName" };
//        список ID view-элементов, в которые будут помещены аттрибуты групп
        int[] companyTo = { android.R.id.text1 };

//        список аттрибутов элементов для чтения
        String[] phoneFrom = {"phoneName"};
//        список ID view-элементов, в которые будут помещены аттрибуты элементов
        int[] phoneTo = { android.R.id.text1 };

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                this,
                companiesData,
                android.R.layout.simple_expandable_list_item_1,
                companyFrom,
                companyTo,
                allPhonesData,
                android.R.layout.simple_list_item_1,
                phoneFrom,
                phoneTo);

        ExpandableListView expListView = findViewById(R.id.expListView);
        expListView.setAdapter(adapter);
    }
}