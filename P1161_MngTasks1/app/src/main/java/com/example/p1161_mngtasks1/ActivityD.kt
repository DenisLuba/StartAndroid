package com.example.p1161_mngtasks1

import android.content.Intent
import android.util.Log
import android.view.View

class ActivityD : MainActivity() {

    override fun onClick(view: View) {

//        Manifest: <activity android:launchMode=["standard" |
//        "singleTop" | "singleTask" | "singleInstance" | "singleInstancePerTask"] ...
        startActivity(Intent(this, ActivityD::class.java))

    }

//    Вначале вызывается onCreate, а в последующие вызовы интента, если стоит launchMode = singleTop,
//    будет вызван onNewIntent. Новый интент не будет создан. А после вызова finish() в onNewIntent
//    активити будет закрыта.
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d("myLogs", "onNewIntent ActivityD")
        finish()
    }
}