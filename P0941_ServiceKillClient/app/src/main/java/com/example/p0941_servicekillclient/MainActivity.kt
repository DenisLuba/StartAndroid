package com.example.p0941_servicekillclient

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.p0941_servicekillclient.databinding.ActivityMainBinding

/**
 *  P0941_ServiceKillClient
 *
 *  Мы вызываем startService, срабатывает onStartCommand и возвращает одну из следующих констант:
 *   START_NOT_STICKY – сервис не будет перезапущен после того, как был убит системой;
 *   START_STICKY – сервис будет перезапущен после того, как был убит системой;
 *   START_REDELIVER_INTENT – сервис будет перезапущен после того, как был убит системой.
 *         Кроме этого, сервис снова получит все вызовы startService, которые не были завершены методом stopSelf(startId).
 *
 *  Второй параметр flags метода onStartCommand дает нам понять, что это повторная попытка вызова onStartCommand.
 *  flags может принимать значения 0(?), START_FLAG_RETRY или START_FLAG_REDELIVERY.
 */

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener { onClick() }
    }

    private fun onClick() {
        val service = Intent()
        service.action = "com.example.p0942_servicekillserver.MyService"
        service.putExtra("name", "value")
        val explicitIntent = convertImplicitToExplicitIntent(service, this)
        if (explicitIntent != null) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(explicitIntent)
            } else startService(explicitIntent)
        }
        else Toast.makeText(this, "intent is null", Toast.LENGTH_LONG).show()
    }

    private fun convertImplicitToExplicitIntent(implicitIntent: Intent, context: Context) : Intent? {
        val packageManager = context.packageManager
        val infoList: List<ResolveInfo> = packageManager.queryIntentServices(implicitIntent, 0)

        if (infoList.isEmpty()) return null

        val serviceInfo: ResolveInfo = infoList[0]
        val component = ComponentName(serviceInfo.serviceInfo.packageName, serviceInfo.serviceInfo.name)
        return Intent(implicitIntent).setComponent(component)
    }
}