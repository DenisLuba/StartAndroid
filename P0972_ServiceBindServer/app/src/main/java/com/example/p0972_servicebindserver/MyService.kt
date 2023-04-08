package com.example.p0972_servicebindserver

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MyService : Service() {

    private companion object {
        fun log(message: String) = Log.d("myLogs", message)
    }

    override fun onCreate() {
        super.onCreate()
        log("MyService onCreate")
    }

    override fun onBind(intent: Intent): IBinder {
        log("MyService onBind")
        return Binder()
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        log("MyService onRebind")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        log("MyService onUnbind")
//        return super.onUnbind(intent)
        return true
    }

    override fun onDestroy() {
        log("MyService onDestroy")
        super.onDestroy()
    }
}