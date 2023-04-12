package com.example.p1041_fragmentlifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    private fun log(message: String) = Log.d("myLogs", message)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        log("MainActivity onCreate")
    }

    override fun onStart() {
        super.onStart()
        log("MainActivity onStart")
    }

    override fun onResume() {
        super.onResume()
        log("MainActivity onResume")
    }

    override fun onPause() {
        super.onPause()
        log("MainActivity onPause")
    }

    override fun onStop() {
        super.onStop()
        log("MainActivity onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("MainActivity onDestroy")
    }
}