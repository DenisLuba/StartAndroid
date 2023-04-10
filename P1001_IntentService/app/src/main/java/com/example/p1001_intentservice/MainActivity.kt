package com.example.p1001_intentservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.p1001_intentservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        val intent = Intent(this, MyIntentService::class.java)

        startService(intent.putExtra("time", 3).putExtra("label", "Call 1"))
        startService(intent.putExtra("time", 1).putExtra("label", "Call 2"))
        startService(intent.putExtra("time", 4).putExtra("label", "Call 3"))
    }
}