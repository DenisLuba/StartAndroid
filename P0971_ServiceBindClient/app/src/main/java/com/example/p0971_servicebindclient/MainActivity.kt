package com.example.p0971_servicebindclient

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.ResolveInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.example.p0971_servicebindclient.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        fun log(message: String) = Log.d("myLogs", message)
    }

    private var bound = false
    private lateinit var serviceConnection: ServiceConnection
    private lateinit var implicitIntent: Intent
    private lateinit var explicitIntent: Intent

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        implicitIntent = Intent()
        implicitIntent.action = "com.example..p0972servicebindserver.MyService"
        explicitIntent = convertImplicitToExplicitIntent(implicitIntent, this) ?: Intent()

        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(componentName: ComponentName?, binder: IBinder?) {
                log("MainActivity onServiceConnected")
                bound = true
            }

            override fun onServiceDisconnected(componentName: ComponentName?) {
                log("MainActivity onServiceDisconnected")
                bound = false
            }
        }

        with(binding) {
            btnStart.setOnClickListener { onClickStart() }
            btnStop.setOnClickListener { stopService(explicitIntent) }
            btnBind.setOnClickListener { bindService(explicitIntent, serviceConnection, BIND_AUTO_CREATE) }
//            btnBind.setOnClickListener { bindService(explicitIntent, serviceConnection, 0) }
            btnUnBind.setOnClickListener { onClickUnBind() }
        }
    }

    private fun onClickUnBind() {
        if (bound) {
            unbindService(serviceConnection)
            bound = false
        }
    }

    private fun onClickStart() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(explicitIntent)
        } else startService(explicitIntent)
    }

    override fun onDestroy() {
        onClickUnBind()
        super.onDestroy()
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