package com.example.p0741_preferencescode;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.widget.CheckBox;

import androidx.annotation.Nullable;

public class PreferencesActivity extends PreferenceActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        создаем экран
        PreferenceScreen rootScreen = getPreferenceManager().createPreferenceScreen(this);
//        говорим Activity, что rootScreen - корневой
        setPreferenceScreen(rootScreen);

//        даллее создаем элементы, присваиваем атрибуты и формируем иерархию

//        *******************************************************

        CheckBoxPreference checkBox1 = new CheckBoxPreference(this);

        checkBox1.setKey("checkBox1");
        checkBox1.setSummaryOn("Description of checkbox 1 on");
        checkBox1.setSummaryOff("Description of checkbox 1 off");
        checkBox1.setTitle("CheckBox 1");

        rootScreen.addPreference(checkBox1);

//        *******************************************************

        ListPreference list = new ListPreference(this);

        list.setKey("list");
        list.setSummary("Description of list");
        list.setTitle("List");
        list.setEntries(R.array.entries);
        list.setEntryValues(R.array.entry_values);

        rootScreen.addPreference(list);

//        *******************************************************

        CheckBoxPreference checkBox2 = new CheckBoxPreference(this);

        checkBox2.setKey("checkBox2");
        checkBox2.setSummary("Description of checkbox 2");
        checkBox2.setTitle("CheckBox 2");

        rootScreen.addPreference(checkBox2);

//        *******************************************************

        PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(this);
        screen.setKey("screen");
        screen.setSummary("Description of screen");
        screen.setTitle("Screen");

        rootScreen.addPreference(screen);

//        *******************************************************

        CheckBoxPreference checkBox3 = new CheckBoxPreference(this);

        checkBox3.setKey("checkBox3");
        checkBox3.setSummary("Description of checkbox 3");
        checkBox3.setTitle("CheckBox 3");

        screen.addPreference(checkBox3);

//        *******************************************************

        PreferenceCategory category1 = new PreferenceCategory(this);

        category1.setKey("category1");
        category1.setSummary("Description of category 1");
        category1.setTitle("Category 1");

        screen.addPreference(category1);

//        *******************************************************

        CheckBoxPreference checkBox4 = new CheckBoxPreference(this);

        checkBox4.setKey("checkBox4");
        checkBox4.setSummary("Description of checkbox 4");
        checkBox4.setTitle("CheckBox 4");

        category1.addPreference(checkBox4);

//        *******************************************************

        PreferenceCategory category2 = new PreferenceCategory(this);

        category2.setKey("category2");
        category2.setSummary("Description of category 2");
        category2.setTitle("Category 2");

        screen.addPreference(category2);

//        *******************************************************

        CheckBoxPreference checkBox5 = new CheckBoxPreference(this);

        checkBox5.setKey("checkBox5");
        checkBox5.setSummary("Description of checkbox 5");
        checkBox5.setTitle("CheckBox 5");

        category2.addPreference(checkBox5);

//        *******************************************************

        CheckBoxPreference checkBox6 = new CheckBoxPreference(this);

        checkBox6.setKey("checkBox6");
        checkBox6.setSummary("Description of checkbox 6");
        checkBox6.setTitle("CheckBox 6");

        category2.addPreference(checkBox6);

//        *******************************************************

//        добавляем зависимости
        list.setDependency("checkBox1");
        screen.setDependency("checkBox2");

//        код для связи активности category2 и значения checkBox3
        category2.setEnabled(checkBox3.isChecked());
        checkBox3.setOnPreferenceClickListener(preference -> {
            category2.setEnabled(checkBox3.isChecked());
            return false;
        });
    }
}
