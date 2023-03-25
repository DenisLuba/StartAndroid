package com.example.p0731_preferencesenable;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;

import androidx.annotation.Nullable;

public class PreferencesActivity extends PreferenceActivity {
    private CheckBoxPreference checkBox3;
    private PreferenceCategory category2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_preferences);

        category2 = (PreferenceCategory) findPreference("category2");
        checkBox3 = (CheckBoxPreference) findPreference("checkbox3");

//        активность категории равна значению чекбокса (вкл/выкл).
//        Это, чтобы при старте экрана все было верно.
        category2.setEnabled(checkBox3.isChecked());
//        Далее для чекбокса прописываем обработчик и в нем по нажатию устанавливаем связь -
//        активность категории равна значению чекбокса.
        checkBox3.setOnPreferenceClickListener(preference -> {
            category2.setEnabled(checkBox3.isChecked());
            return false;
        });
    }
}
