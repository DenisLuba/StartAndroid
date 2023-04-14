package com.example.p1081_actionbarnavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBar.Tab
import androidx.appcompat.app.ActionBar.TabListener
import androidx.fragment.app.FragmentTransaction
import com.example.p1081_actionbarnavigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), TabListener {

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private fun log(message: String) = Log.d("myLogs", message)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar

        if (actionBar != null) {
            actionBar.navigationMode = ActionBar.NAVIGATION_MODE_TABS

            var tab: Tab = actionBar.newTab()
            tab.text = "tab1"
            tab.setTabListener(this)
            actionBar.addTab(tab)

            tab = actionBar.newTab()
            tab.text = "tab2"
            tab.setTabListener(this)
            actionBar.addTab(tab)
        }
    }

    override fun onTabReselected(tab: Tab?, ft: FragmentTransaction?) {
        log("reselected ${tab?.text}")
    }

    override fun onTabSelected(tab: Tab?, ft: FragmentTransaction?) {
        log("selected ${tab?.text}")
    }

    override fun onTabUnselected(tab: Tab?, ft: FragmentTransaction?) {
        log("unselected ${tab?.text}")
    }
}