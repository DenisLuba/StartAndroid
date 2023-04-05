package com.example.p0921_servicesimple

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.concurrent.TimeUnit

class MyService : Service() {

    private val logTag = "myLogs"

    override fun onCreate() {
        super.onCreate()
        Log.d(logTag, "onCreate MyService")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(logTag, "onStartCommand MyService")
        someTask()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        Log.d(logTag, "onBind MyService")
        return null
    }

    override fun onDestroy() {
        Log.d(logTag, "onDestroy MyService")
        super.onDestroy()
    }

    fun someTask() {
        Thread {
            for (i in 0..4) {
                Log.d(logTag, "i = $i")
                TimeUnit.SECONDS.sleep(1)
            }
            stopSelf()
        }.start()
    }
}