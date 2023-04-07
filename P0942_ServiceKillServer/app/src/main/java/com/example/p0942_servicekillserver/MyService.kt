package com.example.p0942_servicekillserver

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.concurrent.TimeUnit

class MyService : Service() {

    private val myLogs = "myLogs"

    override fun onCreate() {
        super.onCreate()
        Log.d(myLogs, "MyService onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(myLogs, "MyService onStartCommand")
        readFlags(flags)
        Thread(MyRun(startId)).start()
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? = null

    override fun onDestroy() {
        Log.d(myLogs, "MyService onDestroy")
        super.onDestroy()
    }

    private fun readFlags(flags: Int) {
        if (flags.and(START_FLAG_REDELIVERY) == START_FLAG_REDELIVERY)
            Log.d(myLogs, "START_FLAG_REDELIVERY")
        if (flags.and(START_FLAG_RETRY) == START_FLAG_RETRY)
            Log.d(myLogs, "START_FLAG_RETRY")
    }

    inner class MyRun(private val startId: Int) : Runnable {

        init { Log.d(myLogs, "MyRun#$startId create") }

        override fun run() {
            Log.d(myLogs, "MyRun#$startId start")
            TimeUnit.SECONDS.sleep(15)
            stop()
        }

        private fun stop() =
            Log.d(myLogs, "MyRun#$startId end, stopSelfResult($startId) = ${ stopSelfResult(startId) }")

    }
}