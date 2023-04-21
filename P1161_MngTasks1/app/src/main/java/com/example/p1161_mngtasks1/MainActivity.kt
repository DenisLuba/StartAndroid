package com.example.p1161_mngtasks1

import android.app.ActivityManager
import android.app.ActivityManager.RunningTaskInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.p1161_mngtasks1.databinding.ActivityMainBinding

/**
 *  Manifest: <activity android:launchMode=["standard" | "singleTop" | "singleTask" | "singleInstance" | "singleInstancePerTask"] ...
 *
 *  Manifest: <activity android:taskAffinity="newtask"...
 *
 *  Manifest: <activity android:allowTaskReparenting="true"
 */

abstract class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private fun log(message: String) = Log.d("myLogs", message)
    private lateinit var list: MutableList<RunningTaskInfo>
    private lateinit var activityManager: ActivityManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        log("onCreate ${this.localClassName}")

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

    abstract fun onClick(view: View)
}