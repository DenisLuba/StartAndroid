package com.example.p1111_preferencefragment

import android.os.Bundle
import android.preference.PreferenceFragment

class Fragment1 : PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preference1)
    }
}