package com.example.p0901_asynctaskstatus

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.p0901_asynctaskstatus.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myTask: MyTask

    private val btnStartId = R.id.btnStart
    private val btnStatusId = R.id.btnStatus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myTask = MyTask()

        binding.btnStart.setOnClickListener { startTask() }
        binding.btnStatus.setOnClickListener(this::onClick)
    }

    private fun onClick(view: View) {
        when(view.id) {
            btnStartId -> startTask()
            btnStatusId -> showStatus()
        }
    }

    private fun startTask() {
        myTask = MyTask()
        myTask.execute()
        myTask.cancel(true)
    }

    private fun showStatus() {
        if (myTask.isCancelled) {
            Toast.makeText(this, "CANCELLED", Toast.LENGTH_SHORT).show()
            TimeUnit.SECONDS.sleep(2)
        }
        Toast.makeText(this, myTask.status.toString(), Toast.LENGTH_SHORT).show()
        TimeUnit.SECONDS.sleep(2)
        myTask = MyTask()
        Toast.makeText(this, myTask.status.toString(), Toast.LENGTH_SHORT).show()
    }

    inner class MyTask: AsyncTask<Void, Void, Void>() {

        override fun onPreExecute() {
            super.onPreExecute()
            val text = "Begin"
            binding.tvInfo.text = text
        }

        override fun doInBackground(vararg p0: Void?): Void? {
            for (i in 0..3) {
                if (isCancelled) return null
                TimeUnit.SECONDS.sleep(1)
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            val text = "End"
            binding.tvInfo.text= text
        }

        override fun onCancelled() {
            super.onCancelled()
            val text = "Cancel"
            binding.tvInfo.text = text
        }
    }
}