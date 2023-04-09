package com.example.p0981_servicebindinglocal

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.example.p0981_servicebindinglocal.databinding.ActivityMainBinding
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity() {

    companion object {
        fun log(message: String) = Log.d("myLogs", message)
    }

    private lateinit var binding: ActivityMainBinding

    private var bound = false
    private var serviceIsAlive = false
    private lateinit var serviceConnection: ServiceConnection
    private lateinit var myService: MyService
    private lateinit var intent: Intent
    private var interval: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        intent = Intent(this, MyService::class.java)

        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(componentName: ComponentName, binder: IBinder) {
                log("MainActivity onServiceConnected")
                myService = (binder as MyService.MyBinder).getService()
                bound = true
            }

            override fun onServiceDisconnected(componentName: ComponentName) {
                log("MainActivity onServiceDisconnected")
                bound = false
            }
        }

        with(binding) {
            btnStart.setOnClickListener {
                if (!serviceIsAlive) {
                    serviceIsAlive = true
                    bound = true
                    startService(intent)
                    bindService(intent, serviceConnection, 0)
                    btnStart.text = resources.getString(R.string.stop)
                } else {
                    serviceIsAlive = false
                    bound = false
                    unbindService(serviceConnection)
                    stopService(intent)
                    btnStart.text = resources.getString(R.string.start)
                }
            }
            btnUp.setOnClickListener {
                if (bound) {
                    interval = myService.upInterval(500)
                    val text = "interval = $interval"
                    tvInterval.text = text
                }
            }
            btnDown.setOnClickListener {
                if (bound) {
                    interval = myService.downInterval(500)
                    val text = "interval = $interval"
                    tvInterval.text = text
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        bindService(intent, serviceConnection, 0)
    }

    override fun onStop() {
        super.onStop()
        if (!bound) return
        unbindService(serviceConnection)
        stopService(intent)
        bound = false
        serviceIsAlive = false
    }
}