package com.example.p0951_servicebackpendingintent

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.p0951_servicebackpendingintent.MainActivity.Companion.PARAMETER_PENDING_INTENT
import com.example.p0951_servicebackpendingintent.MainActivity.Companion.PARAMETER_RESULT
import com.example.p0951_servicebackpendingintent.MainActivity.Companion.PARAMETER_TIME
import com.example.p0951_servicebackpendingintent.MainActivity.Companion.STATUS_FINISH
import com.example.p0951_servicebackpendingintent.MainActivity.Companion.STATUS_START

import com.example.p0951_servicebackpendingintent.MainActivity.Companion.log
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MyService : Service() {

    private lateinit var executor: ExecutorService

    override fun onCreate() {
        super.onCreate()
        log("MyService onCreate")
        executor = Executors.newFixedThreadPool(2)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("MyService onStartCommand")

        val time: Int = intent!!.getIntExtra(PARAMETER_TIME, 1)
        val pendingIntent: PendingIntent = intent.getParcelableExtra(PARAMETER_PENDING_INTENT)!!

        val myRun = MyRun(time, startId, pendingIntent)
        executor.execute(myRun)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        log("MyService onDestroy")
        super.onDestroy()
    }

    inner class MyRun(val time: Int, val startId: Int, val pendingIntent: PendingIntent) : Runnable {

        init {
            log("MyRun#$startId create")
        }

        override fun run() {
            log("MyRun#$startId start, time = $time")

//            сообщаем о старте задачи
            pendingIntent.send(STATUS_START)

//            начинаем выполнение задачи
            TimeUnit.SECONDS.sleep(time.toLong())

//            сообщаем об окончании задачи
            val intent = Intent().putExtra(PARAMETER_RESULT, time * 100)
            pendingIntent.send(this@MyService, STATUS_FINISH, intent)

            stop()
        }

        private fun stop() {
            log("MyRun#$startId end, stopSelfResult($startId) = ${stopSelfResult(startId)}")
        }
    }
}