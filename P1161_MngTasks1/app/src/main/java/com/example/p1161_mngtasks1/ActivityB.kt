package com.example.p1161_mngtasks1

import android.content.Intent
import android.view.View

class ActivityB : MainActivity() {

    override fun onClick(view: View) {
        startActivity(Intent(this, ActivityC::class.java))
    }
}