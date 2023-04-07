package com.example.p0951_servicebackpendingintent

import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.p0951_servicebackpendingintent.databinding.ActivityMainBinding

/**
 *  P0951_ServiceBackPendingIntent
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var task1 : String
    lateinit var task2 : String
    lateinit var task3 : String

    lateinit var task1start : String
    lateinit var task2start : String
    lateinit var task3start : String

    lateinit var task1finish : String
    lateinit var task2finish : String
    lateinit var task3finish : String

    companion object {

        const val TASK1_CODE = 1
        const val TASK2_CODE = 2
        const val TASK3_CODE = 3

        const val STATUS_START = 100
        const val STATUS_FINISH = 200

        const val PARAMETER_TIME = "time"
        const val PARAMETER_PENDING_INTENT = "pendingIntent"
        const val PARAMETER_RESULT = "result"

        fun log(message: String) {
            Log.d("myLogs", message)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        task1 = resources.getString(R.string.task1).toString()
        task2 = resources.getString(R.string.task2)

        task3 = resources.getString(R.string.task3)
        task1start = resources.getString(R.string.task1start)

        task2start = resources.getString(R.string.task2start)
        task2finish = resources.getString(R.string.task2finish)
        task3start = resources.getString(R.string.task3start)
        task3finish = resources.getString(R.string.task3finish)
        task1finish = resources.getString(R.string.task1finish)

        with (binding) {
            tvTask1.text = task1
            tvTask2.text = task2
            tvTask3.text = task3

            btnStart.setOnClickListener { onClick() }
        }
    }

    private fun onClick() {
        var pendingIntent: PendingIntent
        var intent: Intent

//        Создаем PendingIntent для Task1
        pendingIntent = createPendingResult(TASK1_CODE, Intent(), 0)
//        Создаем Intent для вызова сервиса, кладем туда параметр времени и созданный PendingIntent
        intent = Intent(this, MyService::class.java)
            .putExtra(PARAMETER_TIME, 7)
            .putExtra(PARAMETER_PENDING_INTENT, pendingIntent)
//        стартуем сервис
        startService(intent)

        pendingIntent = createPendingResult(TASK2_CODE, Intent(), 0)
        intent = Intent(this, MyService::class.java)
            .putExtra(PARAMETER_TIME, 4)
            .putExtra(PARAMETER_PENDING_INTENT, pendingIntent)
        startService(intent)

        pendingIntent = createPendingResult(TASK3_CODE, Intent(), 0)
        intent = Intent(this, MyService::class.java)
            .putExtra(PARAMETER_TIME, 6)
            .putExtra(PARAMETER_PENDING_INTENT, pendingIntent)
        startService(intent)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        log("requestCode = $requestCode, resultCode = $resultCode")

        with (binding) {
//        Ловим сообщения о старте задач
            when (resultCode) {
                STATUS_START -> when (requestCode) {
                    TASK1_CODE -> tvTask1.text = task1start
                    TASK2_CODE -> tvTask2.text = task2start
                    TASK3_CODE -> tvTask3.text = task3start
                }
//        Ловим сообщения об окончании задач
                STATUS_FINISH -> {
                    val result = intent?.getIntExtra(PARAMETER_RESULT, 0)
                    when (requestCode) {
                        TASK1_CODE -> tvTask1.text = task1finish.format(result)
                        TASK2_CODE -> tvTask2.text = task2finish.format(result)
                        TASK3_CODE -> tvTask3.text = task3finish.format(result)
                    }
                }
            }
        }
    }
}