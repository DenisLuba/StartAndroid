package com.example.p1111_preferencefragment

import android.preference.PreferenceActivity

/**
 *     For Tablets
 */

class MainActivity : PreferenceActivity() {

    override fun onBuildHeaders(target: MutableList<Header>?) {
        loadHeadersFromResource(R.xml.preference_headers, target)
    }

    override fun isValidFragment(fragmentName: String?): Boolean {
        return  fragmentName == Fragment1::class.java.name ||
                fragmentName == Fragment2::class.java.name
    }
}