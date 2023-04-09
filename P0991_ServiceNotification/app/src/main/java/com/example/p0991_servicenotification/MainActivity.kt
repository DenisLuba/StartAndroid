package com.example.p0991_servicenotification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.example.p0991_servicenotification.databinding.ActivityMainBinding

/**
 *  P0991_ServiceNotification
 */


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    companion object {
        const val FILE_NAME = "fileName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        with(binding) {
            btnStart.setOnClickListener { startService(Intent(this@MainActivity, MyService::class.java)) }
            btnStop.setOnClickListener { stopService(Intent(this@MainActivity, MyService::class.java)) }
        }

        val intent = intent
        val fileName = intent.getStringExtra(FILE_NAME)
        if (!TextUtils.isEmpty(fileName)) binding.textView.text = fileName
    }

    override fun onDestroy() {
        stopService(intent)
        super.onDestroy()
    }
}