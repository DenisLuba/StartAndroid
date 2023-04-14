package com.example.p1081_actionbarnavigation

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.p1081_actionbarnavigation.databinding.ActivityMainBinding

class MainActivity2 : AppCompatActivity(), ActionBar.OnNavigationListener {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val data = arrayOf("one", "two", "tree")
    private fun log(message: String) = Log.d("myLogs", message)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val bar: ActionBar? = supportActionBar
        if (bar != null) {
            bar.navigationMode = ActionBar.NAVIGATION_MODE_LIST

            val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, data)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            bar.setListNavigationCallbacks(adapter, this)
        }
    }

    override fun onNavigationItemSelected(itemPosition: Int, itemId: Long): Boolean {
        log("selected: position = $itemPosition, id = $itemId, ${data[itemPosition]}")
        return false
    }
}