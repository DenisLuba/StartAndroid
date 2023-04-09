package com.example.p0981_servicebindinglocal

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.example.p0981_servicebindinglocal.MainActivity.Companion.log
import java.util.*

class MyService : Service() {

    private lateinit var timer: Timer
    private var timerTask: TimerTask? = null
    private var interval: Long = 1000

    private val binder = MyBinder()

    override fun onCreate() {
        super.onCreate()
        log("MyService onCreate")
        timer = Timer()
        schedule()
    }

    override fun onBind(intent: Intent): IBinder {
        log("MyService onBind")
        return binder
    }

    override fun onRebind(intent: Intent?) {
        log("MyService onRebind. TimerTask is null = ${timerTask == null}")

        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        log("MyService onUnbind")
        if (timerTask != null) timerTask = timerTask.also { it!!.cancel() }
        onDestroy()
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        log("MyService onDestroy")
        super.onDestroy()
    }

    private fun schedule() {
        if (timerTask != null) timerTask = timerTask.also { it!!.cancel() }
        if (interval > 0) {
            timerTask = object : TimerTask() {
                override fun run() {
                    log("run")
                }
            }
            timer.schedule(timerTask, 1000, interval)
        }
    }

    fun upInterval(gap: Long): Long {
        interval += gap
        schedule()
        return interval
    }

    fun downInterval(gap: Long): Long {
        if (gap > interval) interval = 0 else interval -= gap
        schedule()
        return interval
    }

    inner class MyBinder : Binder() {
        fun getService() = this@MyService
    }
}