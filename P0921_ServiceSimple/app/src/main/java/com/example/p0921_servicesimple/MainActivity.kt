package com.example.p0921_servicesimple

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.p0921_servicesimple.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val logTag = "myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener { onClickStart(binding.btnStart) }
        binding.btnStop.setOnClickListener { onClickStop(binding.btnStop) }
    }

    fun onClickStart(view: View) {
        startService(Intent(this, MyService::class.java))
    }

    fun onClickStop(view: View) {
        stopService(Intent(this, MyService::class.java))
    }
}