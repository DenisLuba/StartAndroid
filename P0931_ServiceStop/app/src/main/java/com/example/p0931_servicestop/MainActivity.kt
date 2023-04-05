package com.example.p0931_servicestop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.p0931_servicestop.databinding.ActivityMainBinding

/**
 *  P0931_ServiceStop
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val logTag = "myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.start.setOnClickListener { onClickStart(binding.start) }
    }

    fun onClickStart(view: View) {
        startService(Intent(this, MyService::class.java).putExtra("time", 7))
        startService(Intent(this, MyService::class.java).putExtra("time", 2))
        startService(Intent(this, MyService::class.java).putExtra("time", 4))
    }
}