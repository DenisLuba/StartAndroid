package com.example.p1001_intentservice

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.util.Log
import java.util.concurrent.TimeUnit


/**
 * Подкласс [IntentService] для управления запросами асинхронных задач в сервисе в отдельном потоке обработчика.

 * TODO: Настройте класс - обновите действия intent-а, дополнительные параметры и статические методы обработчика.
 */
class MyIntentService : IntentService("MyIntentService") {

    fun log(message: String) = Log.d("myLogs", message)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onHandleIntent(intent: Intent?) {
        val time = intent?.getIntExtra("time", 0) ?: 0
        val label = intent?.getStringExtra("label") ?: ""

        log("onHandleIntent start $label")

        TimeUnit.SECONDS.sleep(time.toLong())

        log("onHandleIntent end $label")
    }

    override fun onDestroy() {
        log("onDestroy")
        super.onDestroy()
    }
}