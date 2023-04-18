package com.example.p1111_preferencefragment

import android.os.Bundle
import android.preference.PreferenceFragment

class Fragment2 : PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preference2)
    }
}