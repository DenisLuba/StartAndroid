package com.example.p0961_servicebackbroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.p0961_servicebackbroadcast.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        fun log(message: String) = Log.d("myLogs", message)

        private lateinit var BROADCAST_ACTION: String

        private lateinit var task1text: String
        private lateinit var task2text: String
        private lateinit var task3text: String

        private lateinit var task1start: String
        private lateinit var task2start: String
        private lateinit var task3start: String

        private lateinit var task1finish: String
        private lateinit var task2finish: String
        private lateinit var task3finish: String

        const val TASK1_CODE = 1
        const val TASK2_CODE = 2
        const val TASK3_CODE = 3

        const val STATUS_START = 100
        const val STATUS_FINISH = 200

        const val PARAMETER_TIME = "time"
        const val PARAMETER_TASK = "task"
        const val PARAMETER_RESULT = "result"
        const val PARAMETER_STATUS = "status"


    }

    private lateinit var broadcastReceiver: BroadcastReceiver
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        BROADCAST_ACTION = resources.getString(R.string.broadcast_action)

        task1text = resources.getString(R.string.task1text)
        task2text = resources.getString(R.string.task2text)
        task3text = resources.getString(R.string.task3text)

        task1start = resources.getString(R.string.task1start)
        task2start = resources.getString(R.string.task2start)
        task3start = resources.getString(R.string.task3start)

        task1finish = resources.getString(R.string.task1finish)
        task2finish = resources.getString(R.string.task2finish)
        task3finish = resources.getString(R.string.task3finish)

        with(binding) {
            task1.text = task1text
            task2.text = task2text
            task3.text = task3text

            buttonStart.setOnClickListener { onClick() }
        }

//        создаем BroadcastReceiver
        broadcastReceiver = object : BroadcastReceiver() {
//        действия при получении сообщений
            override fun onReceive(context: Context?, intent: Intent?) {
                val task = intent?.getIntExtra(PARAMETER_TASK, 0)
                val status = intent?.getIntExtra(PARAMETER_STATUS, 0)
                log("onReceive: task = $task, status = $status")

                with(binding) {
                    when(status) {
//        Ловим сообщения о старте задач
                        STATUS_START -> when(task) {
                            TASK1_CODE -> task1.text = task1start
                            TASK2_CODE -> task2.text = task2start
                            TASK3_CODE -> task3.text = task3start
                        }
//        Ловим сообщения об окончании задач
                        STATUS_FINISH -> {
                            val result = intent.getIntExtra(PARAMETER_RESULT, 0)
                            when(task) {
                                TASK1_CODE -> task1.text = task1finish.format(result)
                                TASK2_CODE -> task2.text = task2finish.format(result)
                                TASK3_CODE -> task3.text = task3finish.format(result)
                            }
                        }
                    }
                }
            }
        }

//        создаем фильтр для BroadcastReceiver
        val intentFilter = IntentFilter(BROADCAST_ACTION)
//        регистрируем (включаем) BroadcastReceiver
        registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onDestroy() {
//        дерегистрируем (выключаем) BroadcastReceiver
        unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }

    private fun onClick() {
//        Создаем Intent для вызова сервиса, кладем туда параметр времени и код задачи
        var intent = Intent(this, MyService::class.java)
            .putExtra(PARAMETER_TIME, 7)
            .putExtra(PARAMETER_TASK, TASK1_CODE)
//        стартуем сервис
        startService(intent)

        intent = Intent(this, MyService::class.java)
            .putExtra(PARAMETER_TIME, 4)
            .putExtra(PARAMETER_TASK, TASK2_CODE)
        startService(intent)

        intent = Intent(this, MyService::class.java)
            .putExtra(PARAMETER_TIME, 6)
            .putExtra(PARAMETER_TASK, TASK3_CODE)
        startService(intent)
    }
}