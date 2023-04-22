package com.example.p1191_pendingintent

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class Receiver : BroadcastReceiver() {

    companion object {
        @JvmStatic fun log(message: String) = Log.d("myLogs", message)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        log("onReceive")
        if (intent == null) return
        log("action = ${intent.action}")
        log("extra = ${intent.getStringExtra("extra")}")
    }
}