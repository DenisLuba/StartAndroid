package com.example.p0961_servicebackbroadcast

import android.app.Service
import android.content.Intent
import com.example.p0961_servicebackbroadcast.MainActivity.Companion.PARAMETER_RESULT
import com.example.p0961_servicebackbroadcast.MainActivity.Companion.PARAMETER_STATUS
import com.example.p0961_servicebackbroadcast.MainActivity.Companion.PARAMETER_TASK
import com.example.p0961_servicebackbroadcast.MainActivity.Companion.PARAMETER_TIME
import com.example.p0961_servicebackbroadcast.MainActivity.Companion.STATUS_FINISH
import com.example.p0961_servicebackbroadcast.MainActivity.Companion.STATUS_START
import com.example.p0961_servicebackbroadcast.MainActivity.Companion.log
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MyService : Service() {

    private lateinit var executorService: ExecutorService
    companion object {
        private lateinit var BROADCAST_ACTION: String
    }

    override fun onCreate() {
        super.onCreate()
        log("MyService onCreate")

        BROADCAST_ACTION = resources.getString(R.string.broadcast_action)
        executorService = Executors.newFixedThreadPool(2)
    }

    override fun onBind(intent: Intent) = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val task = intent!!.getIntExtra(PARAMETER_TASK, 0)
        val time = intent.getIntExtra(PARAMETER_TIME, 1)
        log("MyService onStartCommand from TASK$task")

        val myRun = MyRun(startId, time, task)
        executorService.execute(myRun)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        log("MyService onDestroy")
        super.onDestroy()
    }

    inner class MyRun(private val startId: Int, private val time: Int, private val task: Int) : Runnable {
        init {
            log("MyRun#$startId create")
        }

        override fun run() {
//            сообщаем об старте задачи
            val intent = Intent(BROADCAST_ACTION)
                .putExtra(PARAMETER_TASK, task)
                .putExtra(PARAMETER_STATUS, STATUS_START)
            sendBroadcast(intent)

            log("MyRun#$startId start, time = $time")

//             начинаем выполнение задачи
            TimeUnit.SECONDS.sleep(time.toLong())

//             сообщаем об окончании задачи
            intent
                .putExtra(PARAMETER_STATUS, STATUS_FINISH)
                .putExtra(PARAMETER_RESULT, time * 100)
            sendBroadcast(intent)

            stop()
        }

        private fun stop() {
            log("MyRun#$startId end, stopSelfResult($startId) = ${stopSelfResult(startId)}")
        }
    }
}