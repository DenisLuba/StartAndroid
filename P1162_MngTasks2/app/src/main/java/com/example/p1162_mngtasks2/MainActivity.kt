package com.example.p1162_mngtasks2

import android.app.ActivityManager
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.p1162_mngtasks2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private fun log(message: String) = Log.d("myLogs", message)
    private lateinit var list: MutableList<ActivityManager.RunningTaskInfo>
    private lateinit var activityManager: ActivityManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        title = "${resources.getString(R.string.app_name)} : $localClassName"
        activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager

        binding.btnInfo.setOnClickListener(::onInfoClick)
        binding.btnStart.setOnClickListener(::onClick)
    }

    private fun onInfoClick(view: View) {
        @Suppress("DEPRECATION")
        list = activityManager.getRunningTasks(10)
        for (task in list) {
            if (task.baseActivity?.flattenToShortString()?.startsWith("com.example.p116") == true) {
                log("------------------------------")
                log("Count: ${task.numActivities}")
                log("Root: ${task.baseActivity?.flattenToShortString()}")
                log("Top: ${task.topActivity?.flattenToShortString()}")
            }
        }
    }

    private fun onClick(view: View) {
        startActivity(Intent("mngtasks1_activity_c")
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }
}