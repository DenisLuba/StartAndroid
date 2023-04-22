package com.example.p1191_pendingintent

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.Builder
import androidx.core.app.NotificationManagerCompat
import com.example.p1191_pendingintent.Receiver.Companion.log
import com.example.p1191_pendingintent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val notificationManager: NotificationManager by lazy {
        getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    private val alarmManager: AlarmManager by lazy {
        getSystemService(ALARM_SERVICE) as AlarmManager
    }

    private lateinit var intent1: Intent
    private lateinit var intent2: Intent
    private lateinit var pendingIntent1: PendingIntent
    private lateinit var pendingIntent2: PendingIntent

    companion object {
        private const val CHANNEL_ID = "myChannel"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with (binding) {
            button1.setOnClickListener(::onClick)
            button2.setOnClickListener(::onClick)
        }
    }

    private fun onClick(view: View) {
        when (view.id) {
            binding.button1.id -> {
                intent1 = createIntent("action", "extra 1")
                pendingIntent1 = PendingIntent.getBroadcast(
                    this,
                    0,
                    intent1,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                intent2 = createIntent("action", "extra 2")
                pendingIntent2 = PendingIntent.getBroadcast(
                    this,
                    0,
                    intent2,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                compare()

                sendNotification(1, pendingIntent1)
                sendNotification(2, pendingIntent2)
            }
            binding.button2.id -> {

            }
        }
    }

    private fun createIntent(_action: String, _extra: String) = Intent(this, Receiver::class.java).
    apply {
        action = _action
        putExtra("extra", _extra)
    }

    private fun compare() {
        log("intent1 == intent2: ${ intent1.filterEquals(intent2) }")
        log("pendingIntent1 == pendingIntent2: ${ pendingIntent1 == pendingIntent2 }")
    }

    private fun sendNotification(id: Int, pendingIntent: PendingIntent) {
        val builder = getNotificationBuilder(id, pendingIntent)

        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED) {
                log("NO PERMISSION")
                return
            }
            notify(id, builder.build())
        }
    }

    private fun getNotificationBuilder(id: Int, pendingIntent: PendingIntent) : Builder {
        createNotificationChannel()

        return Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Notification $id")
            .setContentText("Content $id")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }
    }
}