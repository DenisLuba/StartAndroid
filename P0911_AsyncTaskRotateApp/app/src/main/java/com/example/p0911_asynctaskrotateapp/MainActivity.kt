package com.example.p0911_asynctaskrotateapp

import android.app.Activity
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.p0911_asynctaskrotateapp.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

/**
 * Код не рабочий
 *
 * Помогает не крашится при повороте экрана только строка в Манифесте
 *  android:configChanges="orientation|screenSize"
 *  Пи развороте экрана не вызываетя onCreate()
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val hash = hashCode()
    private val LOG_TAG = "myLogs"

    private lateinit var myTask: MyTask

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(LOG_TAG, "create MainActivity: $hash")

        var copyTask = lastCustomNonConfigurationInstance
        if (copyTask == null) copyTask = MyTask()
        else copyTask = copyTask as MyTask

        copyTask.execute()
        myTask = copyTask

        myTask.link(this)

        Log.d(LOG_TAG, "create MyTask: ${myTask.hashCode()}")
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        myTask.unLink()
        return myTask
    }

    class MyTask: AsyncTask<String, Int, Void>() {

        var activity: MainActivity? = null

        // получаем ссылку на MainActivity
        fun link(mainActivity: MainActivity) {
            activity = mainActivity
        }

        fun unLink() {
            activity = null
        }

        override fun doInBackground(vararg params: String): Void? {
            for (i in 0..10) {
                TimeUnit.SECONDS.sleep(1)
                publishProgress(i)
                Log.d(activity?.LOG_TAG, "i = $i, MyTask: ${this.hashCode()}," +
                        "MainActivity: ${activity?.hash}")
            }
            return null
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            val text = "i = ${values[0]}"
            activity?.binding!!.textView.text = text
        }
    }
}