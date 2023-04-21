package com.example.p1161_mngtasks1

import android.content.Intent
import android.view.View

class ActivityC : MainActivity() {

    override fun onClick(view: View) {
        startActivity(Intent(this, ActivityD::class.java))
    }
}