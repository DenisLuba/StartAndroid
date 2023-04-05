package com.example.p0931_servicestop

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MyService : Service() {

    private val logTag = "myLogs"
    private var executorService: ExecutorService? = null
    private var someRes: Any? = null

    override fun onCreate() {
        super.onCreate()
        Log.d(logTag, "MyService onCreate")
        executorService = Executors.newFixedThreadPool(3)
        someRes = Any()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(logTag, "MyService onStartCommand")
        val time = intent?.getIntExtra("time", 1) ?: 1
        val myRun = MyRun(time, startId)
        executorService?.execute(myRun)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        Log.d(logTag, "MyService onDestroy")
        someRes = null
        super.onDestroy()
    }

    inner class MyRun(private val time: Int, private val startId: Int) : Runnable {

        init {
            Log.d(logTag, "MyRun#$startId create")
        }

        override fun run() {
            Log.d(logTag, "MyRun#$startId start, time = $time")
            TimeUnit.SECONDS.sleep(time.toLong())
            try {
                Log.d(logTag, "MyRun#$startId someRes = ${someRes!!::class.java}")
            } catch (e: java.lang.NullPointerException) {
                Log.d(logTag, "MyRun#$startId error, null pointer")
            }
            stop()
        }

//        private fun stop() {
//            Log.d(logTag, "MyRun#$startId end, stopSelf($startId)")
//            stopSelf(startId)
//        }

        private fun stop() {
            Log.d(logTag, "MyRun#$startId end, stopSelfResult($startId) = ${stopSelfResult(startId)}")
        }


    }
}