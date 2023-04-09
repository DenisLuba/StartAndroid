package com.example.p0991_servicenotification

import android.Manifest
import android.app.*
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.p0991_servicenotification.MainActivity.Companion.FILE_NAME
import java.util.concurrent.TimeUnit

class MyService : Service() {

    private lateinit var notificationManager: NotificationManager
    private val CHANNEL_ID = "myChannel"
    private val notificationId = 0

    override fun onCreate() {
        Log.d("myLogs", "onCreate")
        super.onCreate()
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onBind(intent: Intent) = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("myLogs", "onStartCommand")
        for (i in 1..5) {
            TimeUnit.SECONDS.sleep(3);
            sendNotification("File № $i")
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("myLogs", "onDestroy")
    }

    private fun sendNotification(fileName: String) {
        Log.d("myLogs", "sendNotification")

        val builder = getNotificationBuilder(fileName)

        with(NotificationManagerCompat.from(this)) {
//        проверка на наличие разрешения POST_NOTIFICATIONS
            if (ActivityCompat.checkSelfPermission(
                    this@MyService,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Log.d("myLogs", "NO PERMISSION")
                return
            }
//            notificationId — это уникальный int для каждого уведомления, которое вы должны определить.
            notify(notificationId, builder.build())
        }
    }

    private fun getNotificationBuilder(fileName: String): NotificationCompat.Builder {
        Log.d("myLogs", "createNotificationBuilder")
        createNotificationChannel()

        val intent = Intent(this, MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(FILE_NAME, fileName)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Text in status bar")
            .setContentText("Much longer text that cannot fit one line...")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            notificationManager.createNotificationChannel(channel)
        }
    }

}