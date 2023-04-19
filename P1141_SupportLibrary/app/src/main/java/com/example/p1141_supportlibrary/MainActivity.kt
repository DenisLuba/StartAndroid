package com.example.p1141_supportlibrary

import android.os.Bundle
import android.view.Menu
import androidx.fragment.app.FragmentActivity

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myFragment = MyFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, myFragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
}